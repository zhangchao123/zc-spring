package com.zc.spring.formework.webmvc.servlet;

import com.zc.spring.formework.stereotype.ZCRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ZCHandlerAdapter {
    public boolean supports(Object heander){return (heander instanceof  ZCHandlerMapping);}
    public ZCModelAndView handler(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception{
        ZCHandlerMapping handlerMapping = (ZCHandlerMapping)handler;


        //存储方法参数所在的顺序
        Map<String,Integer> paramIndexMapping = new HashMap<String, Integer>();

        //获取方法上的参数注解，一个参数可以多个注解，所有是二位数组
        Annotation[] [] pa = handlerMapping.getMethod().getParameterAnnotations();
        for (int i = 0; i < pa.length ; i ++) {
            for(Annotation a : pa[i]){
                //判断是否是参数注解
                if(a instanceof ZCRequestParam){
                    String paramName = ((ZCRequestParam) a).value();
                    if(!"".equals(paramName.trim())){
                        paramIndexMapping.put(paramName, i);
                    }
                }
            }
        }
        //把HttpServletRequest和HttpServletResponse（如果有）
        Class<?> [] paramsTypes = handlerMapping.getMethod().getParameterTypes();
        for (int i = 0; i < paramsTypes.length ; i ++) {
            Class<?> type = paramsTypes[i];
            if(type == HttpServletRequest.class ||
                    type == HttpServletResponse.class){
                paramIndexMapping.put(type.getName(),i);
            }
        }

        //获得方法的形参列表
        Map<String,String[]> params = request.getParameterMap();

        //根据获取的参数数声明实参集合
        Object [] paramValues = new Object[paramsTypes.length];

        //把通过上面获取的参数名顺序和参数值，对应到实参集合中
        for (Map.Entry<String, String[]> parm : params.entrySet()) {
            String value = Arrays.toString(parm.getValue()).replaceAll("\\[|\\]","")
                    .replaceAll("\\s",",");

            if(!paramIndexMapping.containsKey(parm.getKey())){continue;}

            int index = paramIndexMapping.get(parm.getKey());
            paramValues[index] = caseStringValue(value,paramsTypes[index]);
        }
        //插入HttpServletRequest
        if(paramIndexMapping.containsKey(HttpServletRequest.class.getName())) {
            int reqIndex = paramIndexMapping.get(HttpServletRequest.class.getName());
            paramValues[reqIndex] = request;
        }
        //插入HttpServletResponse
        if(paramIndexMapping.containsKey(HttpServletResponse.class.getName())) {
            int respIndex = paramIndexMapping.get(HttpServletResponse.class.getName());
            paramValues[respIndex] = response;
        }
        //执行url对应的方法，传入参数
        Object result = handlerMapping.getMethod().invoke(handlerMapping.getController(),paramValues);
        if(result == null || result instanceof Void){ return null; }

        //如果返回值是ZCModelAndView，则返回，进行页面模板处理
        boolean isModelAndView = handlerMapping.getMethod().getReturnType() == ZCModelAndView.class;
        if(isModelAndView){
            return (ZCModelAndView) result;
        }

        return null;
    }

    private Object caseStringValue(String value, Class<?> paramsType) {
        if(String.class == paramsType){
            return value;
        }
        //如果是int
        if(Integer.class == paramsType){
            return Integer.valueOf(value);
        }
        else if(Double.class == paramsType){
            return Double.valueOf(value);
        }else {
            if(value != null){
                return value;
            }
            return null;
        }
    }
}
