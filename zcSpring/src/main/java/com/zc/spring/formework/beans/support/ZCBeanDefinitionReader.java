package com.zc.spring.formework.beans.support;

import com.zc.spring.formework.beans.config.ZCBeanDefinition;

import java.beans.beancontext.BeanContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class ZCBeanDefinitionReader {
    public Properties getConfig() {
        return config;
    }
    private Properties config = new Properties();
    private List<String> registyBeanClasses = new ArrayList<String>();
    private final String SCNAN_PACKAGE="scanPackage";

    public ZCBeanDefinitionReader(String ... locations) {
        //读取配置文件
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(locations[0].replace("classpath:",""));
        try {
            config.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(null!=inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        doScanner(config.getProperty(SCNAN_PACKAGE));
    }

    //扫描类
    private void doScanner(String scannerPackage) {
        URL url = this.getClass().getResource("/"+scannerPackage.replaceAll("\\.","/"));
        File classPath = new File(url.getFile());
        for (File file: classPath.listFiles()) {
            //如果是文件
            if(file.isDirectory()){
                doScanner(scannerPackage+"."+file.getName());
            }else{
                if(!file.getName().endsWith(".class")){continue;}
                String className = (scannerPackage+"."+file.getName().replace(".class",""));
                registyBeanClasses.add(className);
            }
        }
    }

    public List<ZCBeanDefinition> loadBeanDefinitions(){

        List<ZCBeanDefinition> res = new ArrayList<ZCBeanDefinition>();
        for(String className:registyBeanClasses){
            try {
                Class<?> beanClass = Class.forName(className);

                if(beanClass.isInterface()) { continue; }

                res.add(doCreateBenanDefinition(toLowerFirstCase(beanClass.getSimpleName()),beanClass.getName()));

                //获取这个类的所有接口，然后找到这个类的接口，把这个类作为接口的实现类进行后面的注入
                Class<?> [] interfaces = beanClass.getInterfaces();
                for (Class<?> i : interfaces) {
                    //如果有多个，那么最后一个生效
                    //后面在注入的时候，应该判断注解上是否注明了实现类的名称，如果没有，则使用默认的
                    res.add(doCreateBenanDefinition(i.getName(),beanClass.getName()));
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    private ZCBeanDefinition doCreateBenanDefinition(String factoryBeanName,String beanClassName){
        ZCBeanDefinition beanDefinition = new ZCBeanDefinition();
        beanDefinition.setBeanClassName(beanClassName);
        beanDefinition.setFactoryBeanName(factoryBeanName);
        return beanDefinition;
    }

    private String toLowerFirstCase(String simpleName){
        char [] chars = simpleName.toCharArray();

        /**
         * 首字母小写
         * 默认我们约定类名都是大写的，大写字母的ascii小于小写字母，其中相差32位
         */
        chars[0]+=32;
        return String.valueOf(chars);
    }
}
