package com.github.KoviRobi;

import javax.ws.rs.core.MediaType;
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response Login ( UserRequest data)
    {
        try
        {
            System.err.println("Logging in: \"" + data.username + "\":\"" + data.password + "\"");

            if (UserInterface.getInstance().authenticateUser(data.username, data.password))
            {
                // TODO: HMAC
                long userToken = UserInterface.setUser(data.username);

                NewCookie authCookie = new NewCookie(
       /* name:    */   "token"
       /* value:   */ , Long.toString(userToken)
       /* path:    */ , "/UROP_1"
       /* domain:  */ , null
       /* comment: */ , "Authentication token"
       /* maxAge:  */ , NewCookie.DEFAULT_MAX_AGE
       /* secure:  */ , false
                 );


                 return Response.status(200)
                     .cookie(authCookie)
                     .build();
            }
            else return Response
                          .status(401)
                          .entity(new ErrorResponse("Failed to log in!"))
                          .build();
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
    }

    @POST
    @Path("/Add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response Add (UserRequest data)
    {
        try
        {
            UserInterface.getInstance().addUser(data.username, data.password);
            return Response
                     .status(200)
                     .entity(new MessageResponse("User successfully created."))
                     .build();
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response Remove (UserRequest data)
    {
        try
        {
            // Authenticate to remove user
            if (!UserInterface.getInstance().authenticateUser(data.username, data.password))
                return Response
                         .status(401)
                         .entity(new ErrorResponse("Failed to authenticate! Perhaps user already removed?"))
                         .build(); // Unauthorised

            UserInterface.getInstance().delUser(data.username);
            return Response
                     .status(200)
                     .entity(new MessageResponse("User successfully removed."))
                     .build();
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
