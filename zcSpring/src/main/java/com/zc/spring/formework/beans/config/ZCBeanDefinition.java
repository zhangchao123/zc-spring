package com.zc.spring.formework.beans.config;

import lombok.Data;

@Data
public class ZCBeanDefinition {
   private String beanClassName;
   private boolean lazyInit = false;
   private String factoryBeanName;
   private boolean isSingleton = true;

}
