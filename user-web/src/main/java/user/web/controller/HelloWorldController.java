package user.web.controller;

import io.kaitoshy.web.mvc.controller.PageController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hello")
public class HelloWorldController implements PageController {

    @GET
    public String hello() {
        return "index.jsp";
    }
}
