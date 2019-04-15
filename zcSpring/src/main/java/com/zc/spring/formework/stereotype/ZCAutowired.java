package com.zc.spring.formework.stereotype;

import java.lang.annotation.*;

/**
 * @author zhangchao
 * @Title: ZCAutowired
 * @ProjectName zcSpring
 * @Description: TODO
 * @date 2019/4/15/01511:29
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZCAutowired {
    String value() default "";
    boolean required() default true;
}
