package com.zc.spring.formework.aop.support;

import com.zc.spring.formework.aop.aspect.ZCAfterReturningAdviceInterceptor;
import com.zc.spring.formework.aop.aspect.ZCAfterThrowingAdviceInterceptor;
import com.zc.spring.formework.aop.aspect.ZCMethodBeforeAdviceInterceptor;
import com.zc.spring.formework.aop.config.ZCAopConfig;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangchao
 * @Title: ZCAdvisedSupport
 * @ProjectName zcSpring
 * @Description: TODO
 * @date 2019/4/17/01715:32
 * 通知 支撑
 */
public class ZCAdvisedSupport {
    private Class<?> targetClass;
    private transient Map<Method, List<Object>> methodCache;
    private ZCAopConfig config;
    private Object target;
    private Pattern pointCutClassPattern;
    public ZCAdvisedSupport(ZCAopConfig config) {
        this.config = config;
    }

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) throws Exception{
        List<Object> cached = methodCache.get(method);
        if(cached == null){
            Method m = targetClass.getMethod(method.getName(),method.getParameterTypes());
            cached = methodCache.get(m);
            //底层逻辑，对代理方法进行一个兼容处理
            this.methodCache.put(m,cached);
        }

        return cached;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
        parse();
    }
    private void parse() {
        String pointCut = config.getPointCut()
                .replaceAll("\\.","\\\\.")
                .replaceAll("\\\\.\\*",".*")
                .replaceAll("\\(","\\\\(")
                .replaceAll("\\)","\\\\)");
        //pointCut=public .* com.gupaoedu.vip.spring.demo.service..*Service..*(.*)
        //玩正则
        String pointCutForClassRegex = pointCut.substring(0,pointCut.lastIndexOf("\\(") - 4);
        pointCutClassPattern = Pattern.compile("class " + pointCutForClassRegex.substring(
                pointCutForClassRegex.lastIndexOf(" ") + 1));
        try {
            methodCache = new HashMap<Method, List<Object>>();
            Pattern pattern = Pattern.compile(pointCut);

            Class aspectClass = Class.forName(this.config.getAspectClass());
            Map<String,Method> aspectMethods = new HashMap<String,Method>();
            for (Method m : aspectClass.getMethods()) {
                aspectMethods.put(m.getName(),m);
            }

            for (Method m : this.targetClass.getMethods()) {
                String methodString = m.toString();
                if (methodString.contains("throws")) {
                    methodString = methodString.substring(0, methodString.lastIndexOf("throws")).trim();
                }

                Matcher matcher = pattern.matcher(methodString);
                if(matcher.matches()){
                    //执行器链
                    List<Object> advices = new LinkedList<Object>();
                    //把每一个方法包装成 MethodIterceptor
                    //before
                    if(!(null == config.getAspectBefore() || "".equals(config.getAspectBefore()))) {
                        //创建一个Advivce
                        advices.add(new ZCMethodBeforeAdviceInterceptor(aspectMethods.get(config.getAspectBefore()),aspectClass.newInstance()));
                    }
                    //after
                    if(!(null == config.getAspectAfter() || "".equals(config.getAspectAfter()))) {
                        //创建一个Advivce
                        advices.add(new ZCAfterReturningAdviceInterceptor(aspectMethods.get(config.getAspectAfter()),aspectClass.newInstance()));
                    }
                    //afterThrowing
                    if(!(null == config.getAspectAfterThrow() || "".equals(config.getAspectAfterThrow()))) {
                        //创建一个Advivce
                        ZCAfterThrowingAdviceInterceptor throwingAdvice =
                                new ZCAfterThrowingAdviceInterceptor(
                                        aspectMethods.get(config.getAspectAfterThrow()),
                                        aspectClass.newInstance());
                        throwingAdvice.setThrowName(config.getAspectAfterThrowingName());
                        advices.add(throwingAdvice);
                    }
                    methodCache.put(m,advices);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Object getTarget(){
        return null;
    }
    public void setTarget(Object target) {
        this.target = target;
    }
    public Class<?> getTargetClass(){
        return this.targetClass;
    }

    public boolean pointCutMatch() {
        return pointCutClassPattern.matcher(this.targetClass.toString()).matches();
    }
}
