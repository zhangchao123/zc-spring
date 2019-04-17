package com.zc.spring.formework.webmvc.servlet;

import java.util.Map;

public class ZCModelAndView {

    private String viewName;
    private Map<String,?> model;

    public ZCModelAndView(String viewName) { this.viewName = viewName; }

    public ZCModelAndView(String viewName, Map<String, ?> model) {
        this.viewName = viewName;
        this.model = model;
    }
    public String getViewName() {
        return viewName;
    }


    public Map<String, ?> getModel() {
        return model;
    }
}
