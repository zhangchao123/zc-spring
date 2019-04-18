package com.zc.spring.formework.aop.aspect;

import java.lang.reflect.Method;

/**
 * @author zhangchao
 * @Title: ZCJoinPoint
 * @ProjectName zcSpring
 * @Description: TODO
 * @date 2019/4/18/01811:26
 */
public interface ZCJoinPoint {
    Object getThis();

    Object[] getArguments();

    Method getMethod();

    void setUserAttribute(String key, Object value);

    Object getUserAttribute(String key);
}
