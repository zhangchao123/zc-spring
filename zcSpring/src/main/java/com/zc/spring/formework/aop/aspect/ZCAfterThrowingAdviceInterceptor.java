package com.zc.spring.formework.aop.aspect;

import com.zc.spring.formework.aop.intercept.ZCMethodInterceptor;

import java.lang.reflect.Method;

/**
 * @author zhangchao
 * @Title: ZCAfterThrowingAdviceInterceptor
 * @ProjectName zcSpring
 * @Description: TODO
 * @date 2019/4/17/01716:42
  */
public class ZCAfterThrowingAdviceInterceptor extends ZCAbstractAspectAdvice implements ZCAdvice, ZCMethodInterceptor {
    private String throwingName;

    public ZCAfterThrowingAdviceInterceptor(Method method, Object newInstance) {
    }

    public void setThrowName(String throwName){
        this.throwingName = throwName;
    }

    @Override
    public Object invoke(ZCMethodInterceptor invocation) throws Throwable {
        return null;
    }
}
