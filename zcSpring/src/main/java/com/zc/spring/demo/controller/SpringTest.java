package com.zc.spring.demo.controller;

import com.zc.spring.formework.context.ZCApplicationContext;

/**
 * @author zhangchao
 * @Title: SpringTest
 * @ProjectName zcSpring
 * @Description: TODO
 * @date 2019/4/15/01513:53
 */
public class SpringTest {

    public static void main(String[] args) throws Exception {
        ZCApplicationContext context = new ZCApplicationContext
                ("application.properties");
        IndexController obj = (IndexController)context.getBean("indexController");
        obj.queryTest("123123");

    }
}
