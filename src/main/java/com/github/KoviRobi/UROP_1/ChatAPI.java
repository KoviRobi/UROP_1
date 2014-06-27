package com.github.KoviRobi.UROP_1;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
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
    public Response GetMessages (@CookieParam("token") long token,
              @DefaultValue("0") @CookieParam("lastMessage") long lastMessage)
    {
        try
        {
            if (UserInterface.getUser(token) == null)
                return Response.status(401).build(); // Unauthorised

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

            return Response.status(200) // Success
                .cookie(lastMessageCookie)
                .entity(messages)
                .build();
        }
        catch(UnknownHostException e)
        {
            return Response.status(503).build(); // Service (database) unavailable
        }
    }

    // Sends a message of parameter
    @POST
    @Path("/SendMessage")
    @Produces("application/json")
    public Response SendMessage (@CookieParam("token") long token,
                                 @FormParam("message") String message)
                                 //@HeaderParam("message") String message)
    {
        System.out.println(message);
        if (message == null)
            return Response.status(400).build();

        try
        {
            if (UserInterface.getUser(token) == null)
                return Response.status(401).build(); // Unauthorised

            ChatInterface.getInstance().sendMessage(UserInterface.getUser(UserInterface.setUser("foo")), message);
            return Response.status(200).build(); // Success
        }
        catch(UnknownHostException e)
        {
            return Response.status(503).build(); // Service (database) unavailable
        }
    }

    @GET
    @Path("/GetUsers")
    @Produces("application/json")
    public Response GetUsers ()
    {
        return Response.status(200) // Success
                .entity(UserInterface.getUsers())
                .build();
    }

}
