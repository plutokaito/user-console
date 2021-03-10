package io.kaitoshy.projects.user.web.controller;

import io.kaitoshy.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/register")
public class SignController implements PageController {

    @Override
    @GET
    @POST
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        boolean isRegisterSuccess =  true;
        if (!isRegisterSuccess) {
            return "register.jsp";
        } else {
            return "success.jsp";
        }
    }
}
