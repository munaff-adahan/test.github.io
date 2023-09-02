package com.azoza.jersey.app.controller;

import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.glassfish.jersey.server.mvc.Viewable;


@Path("/")
//@Singleton
public class HomeController {

    @GET
    public String index(){

        return "ok ";
    }
}
