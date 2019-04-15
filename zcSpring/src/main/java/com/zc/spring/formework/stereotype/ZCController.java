package com.zc.spring.formework.stereotype;

import java.lang.annotation.*;

/**
 * @author zhangchao
 * @Title: ZCController
 * @ProjectName zcSpring
 * @Description: TODO
 * @date 2019/4/15/01511:16
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ZCComponent
public @interface ZCController {
    String value() default "";
}
