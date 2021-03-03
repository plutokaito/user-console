package io.kaitoshy.web.mvc;

import io.kaitoshy.web.mvc.controller.Controller;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;


import static java.util.Arrays.asList;

public class FrontControllerServlet extends HttpServlet {

    /**
     * 请求路径和 {@link Controller} 的映射关系
     */
    private Map<String, Controller> routerMapping = new HashMap<>();

    /**
     * 请求路径和 {@link RequestMethodHandler｝ 的映射关系
     */
    private Map<String, RequestMethodHandler> handlerMethodMapper = new HashMap<>();

    /**
     * 初始化 Servlet
     * @param servletConfig ServletConfig
     */
    @Override
    public void init(ServletConfig servletConfig) {
        initHandlerMethods();
    }

    /**
     * 读取所有的 Controller 的注解元信息 @Path
     * 利用 ServiceLoader 技术 （Java SPI 技术）
     */
    private void initHandlerMethods() {
       for (Controller controller : ServiceLoader.load(Controller.class)) {
          Class<?> controllerClass = controller.getClass();
           Path pathFromClass = controllerClass.getAnnotation(Path.class);
           String requestPath = pathFromClass.value();

           Method[] publicMethods = controllerClass.getMethods();

           for (Method method : publicMethods) {
               Set<String> supportedHttpMethods = findSupportedHttpMethods(method);
               Path pathFromMethod =  method.getAnnotation(Path.class);
               if (pathFromMethod != null) {
                   requestPath += pathFromMethod.value();
               }

               handlerMethodMapper.put(requestPath,
                       new RequestMethodHandler(requestPath, method, supportedHttpMethods));
           }

           routerMapping.put(requestPath, controller);
       }


    }

    /**
     * 获取处理方法标注的 Http 方法几何
     * @param method 处理方法
     * @return Set
     */
    private Set<String> findSupportedHttpMethods(Method method) {
        Set<String> supportedHttpMethods = new LinkedHashSet<>();
        for (Annotation annotationFromMethod : method.getAnnotations()) {
            HttpMethod httpMethod = annotationFromMethod.annotationType().getAnnotation(HttpMethod.class);
            if (httpMethod != null) {
                supportedHttpMethods.add(httpMethod.value());
            }
        }

        if (supportedHttpMethods.isEmpty()) {
            supportedHttpMethods.addAll(asList(HttpMethod.GET, HttpMethod.POST,
                    HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.HEAD, HttpMethod.OPTIONS));
        }

        return supportedHttpMethods;
    }


}
