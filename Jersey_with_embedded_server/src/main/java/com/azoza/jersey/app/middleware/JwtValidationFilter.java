package com.azoza.jersey.app.middleware;

import com.azoza.jersey.app.model.UserDetails;
import com.azoza.jersey.app.service.UserService;
import com.azoza.jersey.app.util.JwtTokenUtil;
import io.fusionauth.jwt.JWTExpiredException;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
@Priority(1)
//@PreMatching
public class JwtValidationFilter implements ContainerRequestFilter {

    @Inject
    private JwtTokenUtil tokenUtil;
    @Inject
    private UserService userService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        System.out.println("JwtValidationFilter");

        String path = requestContext.getUriInfo().getPath();
        if(path.equals("auth") || path.equals("refresh-token")){
            System.out.println("JwtValidationFilter1");
            return;
        }
        if(requestContext.getHeaders().getFirst("Authorization")==null){
            System.out.println("JwtValidationFilter2");
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }else{
            System.out.println("JwtValidationFilter3");
            String token = requestContext.getHeaders().getFirst("Authorization").split(" ")[1];
            try {
                UserDetails userDetails = userService.getUserByEmail(tokenUtil.getUsernameFromToken(token));
                if(!tokenUtil.validateToken(token,userDetails)){
                    System.out.println("JwtValidationFilter4");
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                }
            }catch(JWTExpiredException | NullPointerException ex){
                System.out.println("JwtValidationFilter5");
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Token Expired...").build());
            }catch (Exception ex){
                System.out.println("JwtValidationFilter6");
                //ex.printStackTrace();
               requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());

            }
        }
    }
}
