package com.zc.spring.formework.webmvc.servlet;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * @author zhangchao
 * @Title: ZCHandlerMapping
 * @ProjectName zcSpring
 * @Description: TODO
 * @date 2019/4/15/01517:56
 */
public class ZCHandlerMapping {

    private Object controller; //保存方法对应的实例
    private Method method; //保存映射方法
    private Pattern pattern; //url的匹配

    public ZCHandlerMapping(Object controller, Method method, Pattern pattern) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }
}
