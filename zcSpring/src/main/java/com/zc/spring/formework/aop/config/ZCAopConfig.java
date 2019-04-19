package com.zc.spring.formework.aop.config;

import lombok.Data;

/**
 * @author zhangchao
 * @Title: ZCAopConfig
 * @ProjectName zcSpring
 * @Description: TODO
 * @date 2019/4/17/01716:29
 */
@Data
public class ZCAopConfig {
    private String pointCut;
    private String aspectBefore;
    private String aspectAfter;
    private String aspectClass;
    private String aspectAfterThrow;
    private String aspectAfterThrowingName;
    private String aspectAround;
}
