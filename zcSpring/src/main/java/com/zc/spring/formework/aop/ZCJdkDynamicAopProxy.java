package com.zc.spring.formework.aop;

import com.zc.spring.formework.aop.intercept.ZCMethodInvocation;
import com.zc.spring.formework.aop.support.ZCAdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author zhangchao
 * @Title: ZCJdkDynamicAopProxy
 * @ProjectName zcSpring
 * @Description: TODO
 * @date 2019/4/17/01715:28
 */
public class ZCJdkDynamicAopProxy implements ZCAopProxy, InvocationHandler {

    private ZCAdvisedSupport advised;

    public ZCJdkDynamicAopProxy(ZCAdvisedSupport advised){
        this.advised = advised;
    }

    @Override
    public Object getProxy() {
        return getProxy(this.advised.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader,this.advised.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Object> interceptorsAndDynamicMethodMatchers =
                this.advised.getInterceptorsAndDynamicInterceptionAdvice
                        (method,this.advised.getTargetClass());
        ZCMethodInvocation invocation = new ZCMethodInvocation(
                proxy,
                this.advised.getTarget(),
                method,
                args,
                this.advised.getTargetClass(),
                interceptorsAndDynamicMethodMatchers);
        return invocation.proceed();
    }
}
