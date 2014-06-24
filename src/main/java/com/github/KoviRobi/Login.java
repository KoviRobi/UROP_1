package com.github.KoviRobi;

import javax.ws.rs.core.Response;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.FormParam;

@Path("/Login")
public class Login {

    static String auth_pairs[][] = { { "test", "test"}
                                   , { "admin", "pwd"} };

    // Entry point for login method
    @POST
    public Response main (@FormParam("username") String name, 
                          @FormParam("password") String pass)
    {
        return Response.status(200).entity("un: " + name + "pw: " + pass).build();
    }
}
