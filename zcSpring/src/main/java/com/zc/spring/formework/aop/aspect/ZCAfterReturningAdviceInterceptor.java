package com.zc.spring.formework.aop.aspect;

import com.zc.spring.formework.aop.intercept.ZCMethodInterceptor;
import com.zc.spring.formework.aop.intercept.ZCMethodInvocation;

import java.lang.reflect.Method;

/**
 * @author zhangchao
 * @Title: ZCAfterReturningAdviceInterceptor
 * @ProjectName zcSpring
 * @Description: TODO
 * @date 2019/4/17/01716:41
 */
public class ZCAfterReturningAdviceInterceptor extends ZCAbstractAspectAdvice implements ZCAdvice, ZCMethodInterceptor {

    private ZCJoinPoint joinPoint;
    public ZCAfterReturningAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(ZCMethodInvocation mi) throws Throwable {
        Object retVal = mi.proceed();
        this.joinPoint = mi;
        this.afterReturning(retVal,mi.getMethod(),mi.getArguments(),mi.getThis());
        return retVal;
    }
    private void afterReturning(Object retVal, Method method, Object[] arguments, Object aThis) throws Throwable {
        super.invokeAdviceMethod(this.joinPoint,retVal,null);
    }
}
