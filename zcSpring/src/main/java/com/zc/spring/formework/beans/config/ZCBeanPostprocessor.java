package com.zc.spring.formework.beans.config;

/**
 * @author zhangchao
 * @Title: ZCBeanPostprocessor
 * @ProjectName zcSpring
 * @Description: TODO
 * @date 2019/4/15/01515:44
 */
public class ZCBeanPostprocessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }
}
