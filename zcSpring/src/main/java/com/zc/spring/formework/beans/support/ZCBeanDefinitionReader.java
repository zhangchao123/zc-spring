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
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(locations[0].replace("classpath",""));
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
        URL url = this.getClass().getClassLoader().getResource("/"+scannerPackage.replaceAll("\\.","/"));
        File classPath = new File(url.getFile());
        for (File file: classPath.listFiles()) {
            //如果是文件
            if(file.isDirectory()){
                doScanner(scannerPackage+"."+file.getName());
            }else{
                if(!file.getName().equals(".class")){continue;}
                String className = (scannerPackage+"."+file.getName().replace(".class",""));
                registyBeanClasses.add(className);
            }
        }
    }

    public List<ZCBeanDefinition> loadBeanDefinitions(){

        List<ZCBeanDefinition> res = new ArrayList<ZCBeanDefinition>();
        for(String className:registyBeanClasses){
            ZCBeanDefinition beanDefinition = doCreateBenanDefinition(className);
            if(beanDefinition!=null){
                res.add(beanDefinition);
            }
        }
        return null;
    }

    private ZCBeanDefinition doCreateBenanDefinition(String className){
        try {
            Class<?> beanClass = Class.forName(className);
            if(beanClass.isInterface()){
                return  null;
            }
            ZCBeanDefinition beanDefinition = new ZCBeanDefinition();
            beanDefinition.setBeanClassName(className);
            beanDefinition.setFactoryBeanName(toLowerFirstCase(beanClass.getSimpleName()));
            return beanDefinition;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
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
