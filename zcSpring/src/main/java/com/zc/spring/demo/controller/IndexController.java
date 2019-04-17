package com.zc.spring.demo.controller;

import com.zc.spring.demo.service.IQueryService;
import com.zc.spring.formework.annotation.ZCRequestMapping;
import com.zc.spring.formework.stereotype.ZCAutowired;
import com.zc.spring.formework.stereotype.ZCController;
import com.zc.spring.formework.stereotype.ZCRequestParam;
import com.zc.spring.formework.webmvc.servlet.ZCModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangchao
 * @Title: IndexController
 * @ProjectName zcSpring
 * @Description: TODO
 * @date 2019/4/15/01513:50
 */
@ZCController
@ZCRequestMapping("/web")
public class IndexController {

    @ZCAutowired("indexService")
    private IndexService indexService;

    @ZCAutowired("queryService")
    private IQueryService iQueryService;


    @ZCRequestMapping("/query.json")
    public ZCModelAndView query(HttpServletRequest request, HttpServletResponse response,
                                @ZCRequestParam("name") String name){

        String result = iQueryService.query(name);
        return out(response,result);
    }

    @ZCRequestMapping("/add.json")
    public ZCModelAndView add(HttpServletRequest request, HttpServletResponse response,
                                @ZCRequestParam("name") String name){
        String result = null;
        try {
            result = iQueryService.add(name);
        } catch (Exception e) {
            Map<String,Object> model = new HashMap<String,Object>();
            model.put("detail",e.getCause().getMessage());
            model.put("stackTrace",e.getCause().getStackTrace());
            return  new ZCModelAndView("500");

        }
        return out(response,result);
    }

    public void queryTest(String name){
        iQueryService.query(name);
    }
    private ZCModelAndView out(HttpServletResponse resp, String str){
        try {
            resp.getWriter().write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
