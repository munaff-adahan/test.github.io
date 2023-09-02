package com.azoza.jersey.app.controller;

import com.azoza.jersey.app.dto.AuthResponseDTO;
import com.azoza.jersey.app.model.UserDetails;
import com.azoza.jersey.app.service.UserService;
import com.azoza.jersey.app.util.JwtTokenUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.FormParam;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Date;

@Path("/")
public class AuthController {

@Inject
private JwtTokenUtil tokenUtil;
@Inject
private UserService userService;
    @Path("/auth")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response auth(@FormParam("email") String email , @FormParam("password") String password){

        if(email.equals("abc@gmail.com") && password.equals("1234")){

            UserDetails ud =  userService.getUserByEmail(email);
            String accessToken =  tokenUtil.generateAccessToken(ud);
            String refreshToken = tokenUtil.generateRefreshToken(ud);
            Date expiredDate = tokenUtil.getExpiredDateFromToken(accessToken);

            AuthResponseDTO dto = new AuthResponseDTO();
            dto.setAccessToken(accessToken);
            dto.setRefreshToken(refreshToken);
            dto.setExpireIn(expiredDate.toString());

            return Response.ok().entity(dto).build();
        }else{
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid Email or Password").build();
        }

    }
    @Path("/refresh-token")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response refreshToken(@FormParam("refreshToken") String refreshToken){
        UserDetails userDetails = userService.getUserByEmail(tokenUtil.getUsernameFromToken(refreshToken));
        if(!tokenUtil.validateToken(refreshToken,userDetails)){
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid refresh Token").build();

        }else {
            String accessToken = tokenUtil.generateAccessToken(userDetails);
            Date expiredDate = tokenUtil.getExpiredDateFromToken(accessToken);

            AuthResponseDTO dto = new AuthResponseDTO();
            dto.setAccessToken(accessToken);
            dto.setRefreshToken(refreshToken);
            dto.setExpireIn(expiredDate.toString());

            return Response.ok().entity(dto).build();

        }
    }

}
