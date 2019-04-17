package com.zc.spring.demo.service;

/**
 * @author zhangchao
 * @Title: IQueryService
 * @ProjectName zcSpring
 * @Description: TODO
 * @date 2019/4/16/01615:27
 */
public interface IQueryService {
    /**
     * 查询
     */
    public String query(String name);

    public String add(String name) throws Exception;
}
