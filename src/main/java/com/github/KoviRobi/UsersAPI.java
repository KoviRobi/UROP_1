package com.github.KoviRobi;

import java.util.Hashtable;


// import javax.ws.rs.core.Response;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.FormParam;
import javax.ws.rs.DefaultValue;

import java.security.NoSuchAlgorithmException;
import java.net.UnknownHostException;
import com.mongodb.MongoTimeoutException;

@Path("/Users")
public class UsersAPI {

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
    public Response login (@DefaultValue("") @FormParam("username") String name, 
                           @DefaultValue("") @FormParam("password") String pass)
    {

        try
        {
            if (User.getInstance().authenticateUser(name, pass))
                 return new LoginResponse("cookie");
            else return new LoginResponse("noauth");
        }
        catch (NoSuchAlgorithmException e)
        {
            // TODO: Log
            String error = "Not able to use SHA-512!";
            System.err.println(error);
            return new ErrorResponse (error);
        }
        catch (UnknownHostException e)
        {
            // TODO: Log
            String error = "Not able to connect to MongoDB!" + e.getMessage();
            System.err.println(error);
            return new ErrorResponse (error);
        }
        catch (MongoTimeoutException e)
        {
            // TODO: Log
            String error = "Connection to database timed out!";
            System.err.println(error);
            return new ErrorResponse (error);
        }
        /*if ( auth_pairs.containsKey(name)
          && auth_pairs.get(name).equals(pass))
             response = new LoginResponse ("cookie");
        else response = new LoginResponse ("noauth");
        */

    }
}
