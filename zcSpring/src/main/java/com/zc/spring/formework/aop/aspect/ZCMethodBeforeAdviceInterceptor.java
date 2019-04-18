package com.zc.spring.formework.aop.aspect;

import com.zc.spring.formework.aop.intercept.ZCMethodInterceptor;
import com.zc.spring.formework.aop.intercept.ZCMethodInvocation;

import java.lang.reflect.Method;

/**
 * @author zhangchao
 * @Title: ZCMethodBeforeAdviceInterceptor
 * @ProjectName zcSpring
 * @Description: TODO
 * @date 2019/4/17/01716:36
 */
public class ZCMethodBeforeAdviceInterceptor extends ZCAbstractAspectAdvice implements ZCAdvice, ZCMethodInterceptor {
    private ZCJoinPoint joinPoint;
    public ZCMethodBeforeAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    private void before(Method method,Object[] args,Object target) throws Throwable{
        //传送了给织入参数
        //method.invoke(target);
        super.invokeAdviceMethod(this.joinPoint,null,null);

    }
    @Override
    public Object invoke(ZCMethodInvocation invocation) throws Throwable {
        //从被织入的代码中才能拿到，JoinPoint
        this.joinPoint = invocation;
        before(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return invocation.proceed();
    }
}
