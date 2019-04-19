package com.zc.spring.formework.aop.aspect;

import com.zc.spring.formework.aop.intercept.ZCMethodInterceptor;
import com.zc.spring.formework.aop.intercept.ZCMethodInvocation;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @author zhangchao
 * @Title: ZCAroundAdviceInterceptor
 * @ProjectName zcSpring
 * @Description: TODO
 * @date 2019/4/18/01817:56
 */
@Slf4j
public class ZCAroundAdviceInterceptor extends ZCAbstractAspectAdvice implements ZCAdvice, ZCMethodInterceptor {

    private ZCJoinPoint joinPoint;

    public ZCAroundAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(ZCMethodInvocation invocation) throws Throwable {
        log.info("环绕之前开启事务");
        this.joinPoint = invocation;
        //
        Object obj = invocation.proceed();
        //执行切面方法
        super.invokeAdviceMethod(this.joinPoint,obj,null);

//        invocation.getMethod().invoke(invocation.getThis());

        log.info("环绕之后关闭事务");
        return obj;
    }
}
