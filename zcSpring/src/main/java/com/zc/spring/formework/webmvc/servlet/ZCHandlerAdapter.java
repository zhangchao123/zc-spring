package com.zc.spring.formework.webmvc.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ZCHandlerAdapter {
    public boolean supports(Object heander){return (heander instanceof  ZCHandlerMapping);}
    public ZCHanderModleAndView handler(HttpServletRequest request,HttpServletResponse response,Object Handler)throws Exception{
        return null;
    }
}
