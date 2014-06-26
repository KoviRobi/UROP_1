package com.github.KoviRobi;

import javax.ws.rs.core.Response;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.DefaultValue;

import java.net.UnknownHostException;

@Path("/Chat")
public class ChatAPI {

    // Gets messages since lastMessage
    // @POST
    @javax.ws.rs.GET
    @Path("/GetMessages")
    @Produces("application/json")
    public Response GetMessages (@DefaultValue("0") @FormParam("lastMessage") int lastMessage)
    {
        try
        {
            return Response.status(200). entity
                (ChatInterface.getInstance().getMessages(lastMessage)).build();
        }
        catch(UnknownHostException e)
        {
            return Response.status(503).build();
        }
    }

    // Sends a message of parameter
    @POST
    @Path("/SendMessage")
    @Produces("application/json")
    public Response SendMessage (@CookieParam("token") String token,
                                 @FormParam("message") String message)
    {
        if (message == null)
            return Response.status(400).build();

        try
        {
            ChatInterface.getInstance().sendMessage(UserInterface.getUser(token), message);
            return Response.status(200).build();
        }
        catch(UnknownHostException e)
        {
            return Response.status(503).build();
        }
    }

    @POST
    @Path("/GetUsers")
    @Produces("application/json")
    public Response GetUsers ()
    {
        // TODO:
        return Response.status(200).entity("").build();
    }

}
