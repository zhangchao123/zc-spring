package com.zc.spring.formework.stereotype;

import java.lang.annotation.*;

/**
 * 请求参数映射
 * @author Tom
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZCRequestParam {
	
	String value() default "";
	
	boolean required() default true;

}
