package com.zc.spring.formework.aop.aspect;

import com.zc.spring.formework.aop.intercept.ZCMethodInterceptor;
import com.zc.spring.formework.aop.intercept.ZCMethodInvocation;

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

    public ZCAfterThrowingAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(ZCMethodInvocation invocation) throws Throwable {
        try {
            return invocation.proceed();
        }catch (Throwable e){
            invokeAdviceMethod(invocation,null,e.getCause());
            throw e;
        }
    }


    public void setThrowName(String throwName){
        this.throwingName = throwName;
    }
}
