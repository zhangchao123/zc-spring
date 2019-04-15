package com.zc.spring.formework.context;

/**
 * 通过监听器，扫描所有的类在，把实现了该结构的类，自动注入ZCApplicationContext
 */
public interface ZCApplicationContextAware {
    void setApplicationContext(ZCApplicationContext zcApplicationContext);
}
