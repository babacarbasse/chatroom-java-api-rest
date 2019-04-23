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
    Properties prop;
    
    public ServerMain() throws IOException {
    	try {
			ConfigPropertyValue properties = new ConfigPropertyValue();
			this.prop = properties.getPropValues();
    		Remote r = Naming.lookup(prop.getProperty("namingUserRmi"));
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
		String result = "" + jsonObject;
		return Response.status(200).entity(result).build();
	}
	
	@DELETE
	@Path("unsubscribe")
	@Produces("application/json")
	public Response unSubscribeUser() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ok", "");
 
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
