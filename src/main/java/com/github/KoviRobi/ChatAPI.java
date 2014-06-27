package com.github.KoviRobi;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.CookieParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.DefaultValue;

import java.util.List;
import com.mongodb.DBObject;

import java.net.UnknownHostException;

@Path("/Chat")
public class ChatAPI {

    // Gets messages since lastMessage
    @GET
    @Path("/GetMessages")
    @Produces("application/json")
    public Response GetMessages (@DefaultValue("0") @CookieParam("lastMessage") long lastMessage)
    {
        try
        {
            List<DBObject> messages = ChatInterface.getInstance().getMessages(lastMessage);

            NewCookie lastMessageCookie;
            if (messages.size()>0)
            {
                lastMessageCookie = new NewCookie(
       /* name:    */   "lastMessage"
       /* value:   */ , messages.get(messages.size()-1).get("time").toString()
       /* path:    */ , "/UROP_1"
       /* domain:  */ , null
       /* comment: */ , "Last seen message"
       /* maxAge:  */ , NewCookie.DEFAULT_MAX_AGE
       /* secure:  */ , false
                        );
            }
            else
               lastMessageCookie = null;

            return Response.status(200)
                .cookie(lastMessageCookie)
                .entity(messages)
                .build();
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
    public Response SendMessage (@CookieParam("token") long token,
                                 //@FormParam("message") String message)
                                 @HeaderParam("message") String message)
    {
        System.out.println(message);
        if (message == null)
            return Response.status(400).build();

        try
        {
            ChatInterface.getInstance().sendMessage(UserInterface.getUser(UserInterface.setUser("foo")), message);
            return Response.status(200).build();
        }
        catch(UnknownHostException e)
        {
            return Response.status(503).build();
        }
    }

    @GET
    @Path("/GetUsers")
    @Produces("application/json")
    public Response GetUsers ()
    {
        // TODO:
        return Response.status(200).entity(UserInterface.getUsers()).build();
    }

}
