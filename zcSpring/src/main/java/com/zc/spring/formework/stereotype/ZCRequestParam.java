package com.zc.spring.formework.stereotype;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZCRequestParam {
	
	String value() default "";
	
	boolean required() default true;

}
