package com.zc.spring.formework.aop;

import com.zc.spring.formework.aop.support.ZCAdvisedSupport;

/**
 * @author zhangchao
 * @Title: ZCCglibAopProxy
 * @ProjectName zcSpring
 * @Description: TODO
 * @date 2019/4/17/01715:28
 */
public class ZCCglibAopProxy implements ZCAopProxy {
    public ZCCglibAopProxy(ZCAdvisedSupport config) {
    }
    @Override
    public Object getProxy() {
        return null;
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return null;
    }
}
