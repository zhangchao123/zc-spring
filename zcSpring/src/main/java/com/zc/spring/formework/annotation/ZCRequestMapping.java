package com.zc.spring.formework.annotation;

import java.lang.annotation.*;

/**
 * @author zhangchao
 * @Title: ZCRequestMapping
 * @ProjectName zcSpring
 * @Description: TODO
 * @date 2019/4/15/01517:47
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ZCMapping
public @interface ZCRequestMapping {
    String value() default "";

    String[] params() default {};

    String[] headers() default {};

    String[] consumes() default {};

    String[] produces() default {};
}
