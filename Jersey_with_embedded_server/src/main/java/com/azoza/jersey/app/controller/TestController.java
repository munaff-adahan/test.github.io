package com.azoza.jersey.app.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/test")
public class TestController {
    @GET
    public String get(){
        return "This is Test";
    }
}
