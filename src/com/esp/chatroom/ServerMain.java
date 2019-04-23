package com.esp.chatroom;

import java.io.IOException;
import java.util.Properties;
import java.rmi.*;
import java.util.ArrayList;
import java.net.MalformedURLException;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;
 
@Path("/main")
public class ServerMain {
	
    private ChatRoomUser roomUser = null;
    Properties props;
    
    public ServerMain() throws IOException {
    	try {
			ConfigPropertyValue properties = new ConfigPropertyValue();
			this.props = properties.getPropValues();
    		Remote r = Naming.lookup(props.getProperty("service-users_uri"));
			this.roomUser = (ChatRoomUser)r;
    	} catch (MalformedURLException e) {
			System.out.println("Error");
			System.exit(0);
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
    }

	@POST
	@Path("subscribe")
	@Produces("application/json")
	public Response subscribeUser(String data) throws RemoteException {
		JSONObject jsonObject = new JSONObject();
		JSONObject payload = new JSONObject(data);
		ArrayList<String> online_users = this.roomUser.subscribe(payload.get("user").toString());
		jsonObject.put("online_users", online_users);
		String response = "" + jsonObject;
		return Response.status(200).entity(response).build();
	}
	
	@DELETE
	@Path("subscribe")
	@Produces("application/json")
	public Response unSubscribeUser(String data) throws RemoteException {
		JSONObject jsonObject = new JSONObject();
		JSONObject payload = new JSONObject(data);
		this.roomUser.unsubscribe(payload.get("user").toString());
		jsonObject.put("status", "success");
		String response = "" + jsonObject;
		return Response.status(200).entity(response).build();
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
