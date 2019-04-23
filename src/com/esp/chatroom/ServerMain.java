package com.esp.chatroom;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;
 
@Path("/main")
public class ServerMain {

	@POST
	@Path("subscribe")
	@Produces("application/json")
	public Response subscribeUser() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ok", "success");
 
		String result = "" + jsonObject;
		return Response.status(200).entity(result).build();
	}
	
	@DELETE
	@Path("unsubscribe")
	@Produces("application/json")
	public Response unSubscribeUser() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ok", "success");
 
		String result = "" + jsonObject;
		return Response.status(200).entity(result).build();
	}
	
	@POST
	@Path("sendmessage")
	@Produces("application/json")
	public Response sendMessage() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ok", "success");
 
		String result = "" + jsonObject;
		return Response.status(200).entity(result).build();
	}
	
}
