package io.kaitoshy.web.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  页面控制器，负责服务端页面渲染
 */
public interface PageController extends Controller{

    /**
     *
     * @param request Http 请求
     * @param response Http
     * @return
     * @throws Throwable
     */
    String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable;
}
