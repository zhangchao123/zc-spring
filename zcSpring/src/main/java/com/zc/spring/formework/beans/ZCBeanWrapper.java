package com.zc.spring.formework.beans;

public class ZCBeanWrapper {
    private Object wrappedInstance;

    private Class<?> wrappedclass;

    public ZCBeanWrapper(Object wrappedInstance){
        this.wrappedInstance = wrappedInstance;
    }
    /**
     * Return the bean instance wrapped by this object.
     */
   public Object getWrappedInstance(){
        return this.wrappedInstance;
    }

    /**
     * Return the type of the wrapped bean instance.
     */
    public Class<?> getWrappedClass(){
        return this.wrappedInstance.getClass();
    }
}
