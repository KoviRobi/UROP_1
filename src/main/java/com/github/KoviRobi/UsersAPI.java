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

    // Entry point for login method
    @POST
    @Path("/Login")
    @Produces("application/json")
    public Response Login (@DefaultValue("") @FormParam("username") String name, 
                           @DefaultValue("") @FormParam("password") String pass)
    {
        try
        { // TODO: Avoid repeated code, viz. same catch blocks
            if (UserInterface.getInstance().authenticateUser(name, pass))
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
                // TODO: Proper response
            return new LoginResponse("User successfully added");
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
        catch (UserExistanceException e)
        {
            // TODO: Log
            String error = "User already exists";
            System.err.println(error);
            return new ErrorResponse (error);
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
                // TODO: Proper response
            return new LoginResponse("User successfully deleted");
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
        catch (UserExistanceException e)
        {
            // TODO: Log
            String error = "User does not exists";
            System.err.println(error);
            return new ErrorResponse (error);
        }
    }
}
