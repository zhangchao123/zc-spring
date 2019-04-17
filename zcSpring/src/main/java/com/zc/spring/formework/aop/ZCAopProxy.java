package com.zc.spring.formework.aop;

/**
 * @author zhangchao
 * @Title: ZCAopProxy
 * @ProjectName zcSpring
 * @Description: TODO
 * @date 2019/4/17/01715:27
 */
public interface ZCAopProxy {
    Object getProxy();
    Object getProxy(ClassLoader classLoader);
}
