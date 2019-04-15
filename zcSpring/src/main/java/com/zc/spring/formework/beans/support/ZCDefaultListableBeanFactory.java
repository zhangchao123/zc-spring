package com.zc.spring.formework.beans.support;

import com.zc.spring.formework.beans.config.ZCBeanDefinition;
import com.zc.spring.formework.context.support.ZCAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ZCDefaultListableBeanFactory extends ZCAbstractApplicationContext {
    //存储注册信息的beanDefinitionMap
    protected final Map<String, ZCBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, ZCBeanDefinition>();
}
