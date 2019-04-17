package com.zc.spring.demo.service.impl;

import com.zc.spring.demo.service.IQueryService;
import com.zc.spring.formework.stereotype.ZCService;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhangchao
 * @Title: QueryService
 * @ProjectName zcSpring
 * @Description: TODO
 * @date 2019/4/16/01615:27
 */
@ZCService
@Slf4j
public class QueryService implements IQueryService {
    /**
     * 查询
     */
    public String query(String name) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        String json = "{name:\"" + name + "\",time:\"" + time + "\"}";
        log.info("这是在业务方法中打印的：" + json);
        return json;
    }

    @Override
    public String add(String name) throws Exception{
        throw new Exception("加个异常");

    }
}
