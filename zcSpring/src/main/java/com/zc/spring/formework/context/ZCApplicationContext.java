package com.zc.spring.formework.context;

import com.zc.spring.formework.beans.ZCBeanFactory;
import com.zc.spring.formework.beans.ZCBeanWrapper;
import com.zc.spring.formework.beans.config.ZCBeanDefinition;
import com.zc.spring.formework.beans.support.ZCBeanDefinitionReader;
import com.zc.spring.formework.beans.support.ZCDefaultListableBeanFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ZCApplicationContext  extends ZCDefaultListableBeanFactory implements ZCBeanFactory {
    private String [] configLoactions;

    //单例的ioc容器
    private Map<String,Object> singletonObjects = new ConcurrentHashMap<String,Object>();

    public ZCApplicationContext(String ...configLoactions){
        this.configLoactions  = configLoactions;
        refresh();
    }
    @Override
    public void refresh() {

        //1.定位配置文件
        ZCBeanDefinitionReader reader =  new ZCBeanDefinitionReader(this.configLoactions);
        //2.加载配置文件，扫描类，封装成ZCBeanDefiniton
        List<ZCBeanDefinition> beanDefinitionList =  reader.loadBeanDefinitions();
        //3.注册，把配置信息放到伪ioc容器中
        doRegisterBeanDefinition(beanDefinitionList);
        //4.初始化所有不延时的类
        doAutowrited();

    }

    private void doAutowrited() {
        for (Map.Entry<String,ZCBeanDefinition> definition: super.beanDefinitionMap.entrySet()) {
            if(definition.getValue().isLazyInit()){
                String beanName = definition.getKey();
                getBean(beanName);
            }
        }
    }

    private void doRegisterBeanDefinition(List<ZCBeanDefinition> beanDefinitionList) {
        for (ZCBeanDefinition beanDefinition: beanDefinitionList ) {
            beanDefinitionMap.put(beanDefinition.getFactoryBeanName(),beanDefinition);
        }
    }

    @Override
    public Object getBean(String beanName) {
        //1.初始化
        ZCBeanWrapper wrapper = instantiateBean(beanName,new ZCBeanDefinition());
        //2.注入
        populateBean(beanName,new ZCBeanDefinition(),wrapper);
        return null;
    }

    private void populateBean(String beanName, ZCBeanDefinition zcBeanDefinition, ZCBeanWrapper zcBeanWrapper) {
    }


    private ZCBeanWrapper instantiateBean(String beanName, ZCBeanDefinition zcBeanDefinition) {
        //1.获取类名

        //2.反射，得到对象

        //3.把对象封装成wrapper

        //4.


        return null;

    }
}
