package com.zc.spring.formework.webmvc.servlet;

import java.io.File;
import java.util.Locale;

/**
 * @author zhangchao
 * @Title: ZCViewResolver
 * @ProjectName zcSpring
 * @Description: TODO
 * @date 2019/4/16/01610:31
 */
public class ZCViewResolver {
    public ZCViewResolver(String templateRoot) {
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        templateRootDir = new File(templateRootPath);
    }
    private final String DEFAULT_TEMPLATE_SUFFX = ".html";
    private File templateRootDir;

    public ZCView resolveViewName(String viewName, Locale locale) throws Exception{
        if(null == viewName || "".equals(viewName.trim())){return null;}
        //判断是否呆html后缀，没有的话就帮忙加上
        viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFX) ? viewName : (viewName + DEFAULT_TEMPLATE_SUFFX);
        //获取实际地址
        File templateFile = new File((templateRootDir.getPath() + "/" + viewName).replaceAll("/+","/"));
        return new ZCView(templateFile);
    }
}
