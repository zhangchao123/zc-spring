package com.zc.spring.formework.context;

import com.zc.spring.formework.beans.ZCBeanFactory;
import com.zc.spring.formework.beans.ZCBeanWrapper;
import com.zc.spring.formework.beans.config.ZCBeanDefinition;
import com.zc.spring.formework.beans.support.ZCBeanDefinitionReader;
import com.zc.spring.formework.beans.support.ZCDefaultListableBeanFactory;
import com.zc.spring.formework.stereotype.ZCAutowired;
import com.zc.spring.formework.stereotype.ZCController;
import com.zc.spring.formework.stereotype.ZCService;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ZCApplicationContext  extends ZCDefaultListableBeanFactory implements ZCBeanFactory {
    private String [] configLoactions;

    //单例的ioc容器
    private Map<String,Object> singletonObjects = new ConcurrentHashMap<String,Object>();

    private  Map<String,ZCBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<String,ZCBeanWrapper>();

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
            if(!definition.getValue().isLazyInit()){
                String beanName = definition.getKey();
                try {
                    getBean(beanName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void doRegisterBeanDefinition(List<ZCBeanDefinition> beanDefinitionList) {
        for (ZCBeanDefinition beanDefinition: beanDefinitionList ) {
            beanDefinitionMap.put(beanDefinition.getFactoryBeanName(),beanDefinition);
        }
    }

    @Override
    public Object getBean(String beanName) throws Exception {
        //1.初始化
        ZCBeanWrapper wrapper = instantiateBean(beanName,beanDefinitionMap.get(beanName));
        // 2.把beanWarpper 存入ioc容器中
        this.factoryBeanInstanceCache.put(beanName,wrapper);
        //3.注入
        populateBean(beanName,new ZCBeanDefinition(),wrapper);

        return this.factoryBeanInstanceCache.get(beanName).getWrappedClass();
    }

    private void populateBean(String beanName, ZCBeanDefinition zcBeanDefinition, ZCBeanWrapper zcBeanWrapper) {
        Object instance = zcBeanWrapper.getWrappedInstance();

        //判断加了注解的类，进行依赖注入
        Class<?>  clazz = zcBeanWrapper.getWrappedClass();

        if(clazz.isAnnotationPresent(ZCService.class)||clazz.isAnnotationPresent(ZCController.class)){
            //取出所有字段
            Field[] fields = clazz.getDeclaredFields();
            for(Field field: fields){
                if(field.isAnnotationPresent(ZCAutowired.class)){
                    ZCAutowired autowired = field.getAnnotation(ZCAutowired.class);
                    String autowiredBeanName = autowired.value().trim();
                    if("".equals(autowiredBeanName)){
                        autowiredBeanName = field.getType().getName();
                    }
                    //可访问私有
                    field.setAccessible(true);
                    try {
                        field.set(instance,this.factoryBeanInstanceCache.get(autowiredBeanName).getWrappedInstance());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }


    private ZCBeanWrapper instantiateBean(String beanName, ZCBeanDefinition zcBeanDefinition) {
        //1.获取类名
        String className = zcBeanDefinition.getBeanClassName();
        //2.反射，得到对象
        Object instace = null;
        try{
            //如果单例容器中有（默认先实现单例）
            if(this.singletonObjects.containsKey(className)){
                instace = this.singletonObjects.get(className);
            }else{
                Class<?> clazz = Class.forName(className);
                instace = clazz.newInstance();
                this.singletonObjects.put(className,instace);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        //3.把对象封装成wrapper
        ZCBeanWrapper beanWrapper = new ZCBeanWrapper(instace);
        //4.
        return beanWrapper;

    }
}
