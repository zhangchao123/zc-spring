package com.zc.spring.formework.aop.intercept;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author zhangchao
 * @Title: ZCMethodInvocation
 * @ProjectName zcSpring
 * @Description: TODO
 * @date 2019/4/17/01715:42
 */
public class ZCMethodInvocation {

    public ZCMethodInvocation(
            Object proxy, Object target, Method method, Object[] arguments,
            Class<?> targetClass, List<Object> interceptorsAndDynamicMethodMatchers) {

//        this.proxy = proxy;
//        this.target = target;
//        this.targetClass = targetClass;
//        this.method = method;
//        this.arguments = arguments;
//        this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
    }

    public Object proceed() throws Throwable {
//        //如果Interceptor执行完了，则执行joinPoint
//        if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
//            return this.method.invoke(this.target,this.arguments);
//        }
//
//        Object interceptorOrInterceptionAdvice =
//                this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);
//        //如果要动态匹配joinPoint
//        if (interceptorOrInterceptionAdvice instanceof GPMethodInterceptor) {
//            GPMethodInterceptor mi =
//                    (GPMethodInterceptor) interceptorOrInterceptionAdvice;
//            return mi.invoke(this);
//        } else {
//            //动态匹配失败时,略过当前Intercetpor,调用下一个Interceptor
//            return proceed();
//        }
        return null;
    }
}
