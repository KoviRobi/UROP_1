package com.github.KoviRobi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/Test")
public class Test {
	
	@GET
	@Path("/Page1")
	public Response page1 ()
	{
		String response = "Welcome to page 1!";
		return Response.status(200).entity(response).build();
	}

}
