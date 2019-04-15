package com.zc.spring.demo.controller;

import com.zc.spring.formework.stereotype.ZCAutowired;
import com.zc.spring.formework.stereotype.ZCController;

/**
 * @author zhangchao
 * @Title: IndexController
 * @ProjectName zcSpring
 * @Description: TODO
 * @date 2019/4/15/01513:50
 */
@ZCController
public class IndexController {

    @ZCAutowired("indexService")
    private IndexService indexService;

}
