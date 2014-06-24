package com.github.KoviRobi;

import java.util.Hashtable;

// import javax.ws.rs.core.Response;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.FormParam;
import javax.ws.rs.DefaultValue;

@Path("/")
public class Login {

    static Hashtable<String, String> auth_pairs = new Hashtable<String, String>();
    static
    {
        auth_pairs.put("admin", "password");
        auth_pairs.put("test1", "passphrase");
    }

    // Entry point for login method
    @POST
    @Path("/Login")
    @Produces("application/json")
    public LoginResponse main (@DefaultValue("") @FormParam("username") String name, 
                               @FormParam("password") String pass)
    {

    	LoginResponse response;
    	
        if ( auth_pairs.containsKey(name)
          && auth_pairs.get(name).equals(pass))
             response = new LoginResponse (true, "cookie");
        else response = new LoginResponse (false);

        return response;

        /*StringBuilder rtn = new StringBuilder("{");

        if ( auth_pairs.containsKey(name)
          && auth_pairs.get(name).equals(pass))
             rtn.append("text: \"success\";");
        else rtn.append("text: \"failure\";");

        rtn.append("}");

        return Response.status(200).entity(rtn.toString()).build();*/
    }
}
