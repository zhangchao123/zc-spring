package com.zc.spring.formework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhangchao
 * @Title: ZCMapping
 * @ProjectName zcSpring
 * @Description: TODO
 * @date 2019/4/15/01517:47
 */
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ZCMapping {
}
