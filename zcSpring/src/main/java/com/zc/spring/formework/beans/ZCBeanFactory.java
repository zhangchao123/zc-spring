package com.zc.spring.formework.beans;

public interface ZCBeanFactory {

    /**
     * 获取一个实例bean
     * @param beanName
     * @return
     */
    Object getBean(String beanName);
}
