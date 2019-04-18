package com.zc.spring.formework.aop.intercept;

/**
 * @author zhangchao
 * @Title: ZCMethodInterceptor
 * @ProjectName zcSpring
 * @Description: TODO
 * @date 2019/4/17/01715:42
 */
public interface ZCMethodInterceptor {
    Object invoke(ZCMethodInvocation invocation) throws Throwable;
}
