package com.zc.spring.formework.context;

import com.zc.spring.formework.beans.ZCBeanFactory;
import com.zc.spring.formework.beans.ZCBeanWrapper;
import com.zc.spring.formework.beans.config.ZCBeanDefinition;
import com.zc.spring.formework.beans.config.ZCBeanPostprocessor;
import com.zc.spring.formework.beans.support.ZCBeanDefinitionReader;
import com.zc.spring.formework.beans.support.ZCDefaultListableBeanFactory;
import com.zc.spring.formework.stereotype.ZCAutowired;
import com.zc.spring.formework.stereotype.ZCController;
import com.zc.spring.formework.stereotype.ZCService;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * spring 核心上下文
 */
public class ZCApplicationContext  extends ZCDefaultListableBeanFactory implements ZCBeanFactory {
    //配置文件目录信息，可以配置多个，所以用数组
    private String [] configLoactions;

    //单例的ioc容器，存储被实例化的bean，注入的时候从这里取
    private Map<String,Object> singletonObjects = new ConcurrentHashMap<String,Object>();
    //在getBean的时候，存储Bean的包装类
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
        //根据工厂+策略创建事件（这里要研究下spring的处理方式）
        ZCBeanPostprocessor postprocessor = new ZCBeanPostprocessor();
        //1.初始化
        Object instace = instantiateBean(beanName,beanDefinitionMap.get(beanName));

        ZCBeanWrapper wrapper = new ZCBeanWrapper(instace);

        // 2.把beanWarpper 存入ioc容器中
        this.factoryBeanInstanceCache.put(beanName,wrapper);
        //注入开始事件
        postprocessor.postProcessBeforeInitialization(instace,beanName);
        //3.注入
        populateBean(beanName,new ZCBeanDefinition(),wrapper);
        //注入结束事件
        postprocessor.postProcessAfterInitialization(instace,beanName);
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


    private Object instantiateBean(String beanName, ZCBeanDefinition zcBeanDefinition) {
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
        return instace;
    }

    public String[] getBeanDefinitionNames(){
        return this.beanDefinitionMap.keySet().toArray(new String[this.beanDefinitionMap.size()]);
    }

    public int getBeanDefinitonCount(){
        return this.beanDefinitionMap.size();
    }
}