package io.kaitoshy.projects.user.web.controller;

import io.kaitoshy.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/hello")
public class HelloWorldController implements PageController {

    @Override
    @GET
    @POST
    @Path("/world")
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return "index.jsp";
    }
}
