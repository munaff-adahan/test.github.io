package com.azoza.jersey.app.config;

import org.glassfish.jersey.server.ResourceConfig;

public class AppConfig extends ResourceConfig {
    public AppConfig() {
        packages("com.azoza.jersey.app.controller");
        packages("com.azoza.jersey.app.middleware");

        register(DependencyBinder.class);
    }
}
