package com.github.KoviRobi;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.FormParam;
import javax.ws.rs.DefaultValue;

import java.security.NoSuchAlgorithmException;
import java.net.UnknownHostException;
import com.mongodb.MongoTimeoutException;

@Path("/User")
public class UserAPI {

    // Entry point for login method
    @POST
    @Path("/Login")
    @Consumes("application/json")
    @Produces("application/json")
    public Response Login (@DefaultValue("") @FormParam("username") String name, 
                           @DefaultValue("") @FormParam("password") String pass)
    {
        try
        {   // TODO: Avoid repeated code, viz. same catch blocks
            if (UserInterface.getInstance().authenticateUser(name, pass))
            {
                // TODO: HMAC
                long userToken = UserInterface.setUser(name);

                NewCookie authCookie = new NewCookie(
       /* name:    */   "token"
       /* value:   */ , Long.toString(userToken)
       /* path:    */ , "/UROP_1"
       /* domain:  */ , null
       /* comment: */ , "Authentication token"
       /* maxAge:  */ , NewCookie.DEFAULT_MAX_AGE
       /* secure:  */ , false
                 );

                System.err.println("Logged in: " + name + ":" + pass);

                 return Response.status(200)
                     .cookie(authCookie)
                     .build();
            }
            else return Response.status(401).build();
        }
        catch (NoSuchAlgorithmException e)
        {   // TODO: Log
            String error = "Not able to use SHA-512!";
            System.err.println(error);
            return Response.status(501).entity(new ErrorResponse(error)).build();
        }
        catch (UnknownHostException e)
        {   // TODO: Log
            String error = "Not able to connect to MongoDB!" + e.getMessage();
            System.err.println(error);
            return Response.status(503).entity(new ErrorResponse(error)).build();
        }
        catch (MongoTimeoutException e)
        {   // TODO: Log
            String error = "Connection to database timed out!";
            System.err.println(error);
            return Response.status(503).entity(new ErrorResponse(error)).build();
        }
    }

    @POST
    @Path("/Add")
    @Produces("application/json")
    public Response Add (@DefaultValue("") @FormParam("username") String name,
                         @DefaultValue("") @FormParam("password") String pass)
    {
        try
        {
            UserInterface.getInstance().addUser(name, pass);
            return Response.status(200).build();
        }
        catch (NoSuchAlgorithmException e)
        {   // TODO: Log
            String error = "Not able to use SHA-512!";
            System.err.println(error);
            return Response.status(501).entity(new ErrorResponse(error)).build();
        }
        catch (UnknownHostException e)
        {   // TODO: Log
            String error = "Not able to connect to MongoDB!\n" + e.getMessage();
            System.err.println(error);
            return Response.status(503).entity(new ErrorResponse(error)).build();
        }
        catch (MongoTimeoutException e)
        {   // TODO: Log
            String error = "Connection to database timed out!";
            System.err.println(error);
            return Response.status(503).entity(new ErrorResponse(error)).build();
        }
        catch (UserExistanceException e)
        {   // TODO: Log
            String error = "User already exists";
            System.err.println(error);
            return Response.status(409).entity(new ErrorResponse(error)).build();
        }
    }

    @POST
    @Path("/Remove")
    @Produces("application/json")
    public Response Remove (@DefaultValue("") @FormParam("username") String name)
    {
        try
        {
            UserInterface.getInstance().delUser(name);
            return Response.status(200).build();
        }
        catch (NoSuchAlgorithmException e)
        {   // TODO: Log
            String error = "Not able to use SHA-512!";
            System.err.println(error);
            return Response.status(501).entity(new ErrorResponse(error)).build();
        }
        catch (UnknownHostException e)
        {   // TODO: Log
            String error = "Not able to connect to MongoDB!" + e.getMessage();
            System.err.println(error);
            return Response.status(503).entity(new ErrorResponse(error)).build();
        }
        catch (MongoTimeoutException e)
        {   // TODO: Log
            String error = "Connection to database timed out!";
            System.err.println(error);
            return Response.status(503).entity(new ErrorResponse(error)).build();
        }
        catch (UserExistanceException e)
        {   // TODO: Log
            String error = "User does not exists";
            System.err.println(error);
            return Response.status(409).entity(new ErrorResponse(error)).build();
        }
    }
}
