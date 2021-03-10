package io.kaitoshy.web.mvc;

import io.kaitoshy.web.mvc.controller.Controller;
import io.kaitoshy.web.mvc.controller.PageController;
import io.kaitoshy.web.mvc.controller.RestController;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Logger;


import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.substringAfter;

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


    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 建立映射关系
        String requestURI = request.getRequestURI();
        // ContextPath = /a or / or ""
        String prefixPath = request.getContextPath();

        String requestMappingPath = substringAfter(requestURI,
                StringUtils.replace(prefixPath, "//", "/"));

        Controller controller = routerMapping.get(requestMappingPath);

        if (controller != null) {
            RequestMethodHandler handlerInfo = handlerMethodMapper.get(requestMappingPath);

            try {
                if (handlerInfo != null) {
                    String httpMethod = request.getMethod();

                    if (!handlerInfo.getSupportedHttpMethod().contains(httpMethod)) {
                        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                        return;
                    }

                    if (controller instanceof PageController) {
                        PageController pageController = PageController.class.cast(controller);
                        String viewPath = pageController.execute(request, response);
                        // 页面请求 forward

                        ServletContext servletContext = request.getServletContext();
                        if (!viewPath.startsWith("/")) {
                            viewPath = "/" + viewPath;
                        }
                        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(viewPath);
                        requestDispatcher.forward(request, response);
                    } else if (controller instanceof RestController) {
                        // TODO
                    }

                }
            } catch (Throwable throwable) {
                if (throwable.getCause() instanceof IOException) {
                    throw  (IOException) throwable.getCause();
                } else {
                    throw new ServletException(throwable.getCause());
                }
            }
        }


    }


}
