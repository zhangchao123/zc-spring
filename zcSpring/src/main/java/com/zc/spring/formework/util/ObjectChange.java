package com.zc.spring.formework.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangchao
 * @Title: ObjectChange
 * @ProjectName zcSpring
 * @Description: TODO
 * @date 2019/4/16/01614:37
 */
public class ObjectChange {
    private static Map<Class<?>,String> objectlist = new HashMap<Class<?>,String>();
    static{

        objectlist.put(String.class,"stringChange");
        objectlist.put(Integer.class,"integerChange");
        objectlist.put(Double.class,"doubleChange");
        objectlist.put(BigDecimal.class,"bigDecimalChange");
    }

    public Object objectChange(String value, Class<?> paramsType){
        for(Class<?> key:objectlist.keySet()){
            if(key==paramsType){
                String methodName = objectlist.get(key);
                try {
                    Method method = this.getClass().getMethod(methodName,String.class);
                    return method.invoke(value);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public String stringChange(String value){
        return value;
    }

    public Integer integerChange(String value){
        return Integer.valueOf(value);
    }


    public static void main(String[] args) {
        String name = "123";
        ObjectChange objectChange = new ObjectChange();
        System.out.println(objectChange.objectChange("123",Integer.class));
    }
}
