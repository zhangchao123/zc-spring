package com.zc.spring.formework.stereotype;

import java.lang.annotation.*;

/**
 * @author zhangchao
 * @Title: ZCService
 * @ProjectName zcSpring
 * @Description: TODO
 * @date 2019/4/15/01511:21
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ZCComponent
public @interface ZCService {
    String value() default "";
}