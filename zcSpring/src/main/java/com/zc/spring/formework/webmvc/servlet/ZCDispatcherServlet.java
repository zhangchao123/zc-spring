package com.zc.spring.formework.webmvc.servlet;

import com.zc.spring.formework.annotation.ZCRequestMapping;
import com.zc.spring.formework.context.ZCApplicationContext;
import com.zc.spring.formework.stereotype.ZCController;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author zhangchao
 * @Title: ZCDispatcherServlet
 * @ProjectName zcSpring
 * @Description: TODO
 * @date 2019/4/15/01516:27
 */
@Slf4j
public class ZCDispatcherServlet extends HttpServlet {
    private final String CONTEXT_CONFIG_LOACTION ="contextConfigLocation";
    private ZCApplicationContext context;

    private List<ZCHandlerMapping> handlerMappings = new ArrayList<ZCHandlerMapping>();
    private Map<ZCHandlerMapping,ZCHandlerAdapter> handlerMappingZCHandlerAdapterMap = new HashMap<ZCHandlerMapping,ZCHandlerAdapter>();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doDispatcher(req,resp);
    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) {
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //1.初始化applicationContext
        context = new ZCApplicationContext(config.getInitParameter(CONTEXT_CONFIG_LOACTION));
        //2.初始化spirng mvc 9大组件
        initStrategies(null);
    }
    protected void initStrategies(ZCApplicationContext context) {

        //多文件上传组件
        this.initMultipartResolver(context);
        //初始化本地语言环境
        this.initLocaleResolver(context);
        //初始化模板处理器
        this.initThemeResolver(context);
        //初始化handlerMapping
        this.initHandlerMappings(context);
        //初始化参数适配器
        this.initHandlerAdapters(context);
        //初始化异常拦截器
        this.initHandlerExceptionResolvers(context);
        //初始化视图预处理器
        this.initRequestToViewNameTranslator(context);
        //初始化视图转换器
        this.initViewResolvers(context);
        //参数缓存器
        this.initFlashMapManager(context);
    }

    private void initFlashMapManager(ZCApplicationContext context) {
    }

    private void initViewResolvers(ZCApplicationContext context) {
    }

    private void initRequestToViewNameTranslator(ZCApplicationContext context) {
    }

    private void initHandlerExceptionResolvers(ZCApplicationContext context) {
    }

    private void initHandlerAdapters(ZCApplicationContext context) {
        //把一个request请求变成一个heandler ，参数都是字符串，自动配到handler中
        //那么，一个handlerMapping 对应一个heandler
        for(ZCHandlerMapping handlerMapping:this.handlerMappings){
            this.handlerMappingZCHandlerAdapterMap.put(handlerMapping,new ZCHandlerAdapter());
        }
    }

    private void initHandlerMappings(ZCApplicationContext context) {
        String [] beanNames = context.getBeanDefinitionNames();
        for (String beanName:beanNames){
            try {
                Object object = context.getBean(beanName);
                Class<?> clazz = object.getClass();
                if(!clazz.isAnnotationPresent(ZCController.class)){
                    continue;
                }

                String baseUrl = "";
                if(clazz.isAnnotationPresent(ZCRequestMapping.class)){
                    ZCRequestMapping requestMapping = clazz.getAnnotation(ZCRequestMapping.class);
                    baseUrl = requestMapping.value();
                }
                Method [] methods = clazz.getMethods();
                for(Method method : methods){
                    if(method.getClass().isAnnotationPresent(ZCRequestMapping.class)){
                        continue;
                    }

                    ZCRequestMapping requestMapping = method.getAnnotation(ZCRequestMapping.class);
                    //替换所有有//的为/
                    String regex = ("/"+baseUrl+"/"+requestMapping.value().replaceAll("\\*",".*")).replaceAll("/+","/");
                    Pattern pattern = Pattern.compile(regex);
                    handlerMappings.add(new ZCHandlerMapping(object,method,pattern));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initThemeResolver(ZCApplicationContext context) {
    }

    private void initLocaleResolver(ZCApplicationContext context) {
    }

    private void initMultipartResolver(ZCApplicationContext context) {
    }
}
