package com.esp.chatroom;

import java.io.IOException;
import java.util.Properties;
import java.util.Vector;
import java.rmi.*;
import java.util.ArrayList;
import java.net.MalformedURLException;
import java.net.URL;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.json.JSONException;
import org.json.JSONObject;
 
@Path("/main")
public class ServerMain {
	
    private ChatRoomUser roomUser = null;
    private XmlRpcClient serverMessage=null;
    

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
    	this.connexionClientMessage();
    }
    
    public void connexionClientMessage() {
    	try {
    		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
    	    config.setServerURL(new URL(this.props.getProperty("service-messages_uri")));
			this.serverMessage = new XmlRpcClient();
			serverMessage.setConfig(config);
			System.out.println("connected to server message success");
		} catch (Exception exception) { 
			System.err.println("Client: " + exception); 
		}
    }

	@POST
	@Path("subscribe")
	@Produces("application/json")
	public Response subscribeUser(String data) throws RemoteException {
		int status = 200;
		JSONObject jsonObject = new JSONObject();
		JSONObject payload = new JSONObject(data);
		if (payload.isNull("user") || payload.getString("user").length() == 0) {
			status = 400;
			jsonObject.put("error", "form invalid");
		} else {
			ArrayList<String> online_users = this.roomUser.subscribe(payload.get("user").toString());
			jsonObject.put("online_users", online_users);
		}
		String response = "" + jsonObject;
		return Response.status(status).entity(response).build();
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
	public Response sendMessage(String data) throws JSONException, XmlRpcException, RemoteException {
		int status = 200;
		JSONObject payload = new JSONObject(data);
		// check if user is connected
		JSONObject jsonObject = new JSONObject();
		Boolean isConnected = this.roomUser.isUserConnected(payload.getString("user"));
		if (isConnected == true) {
			Vector<String> message_post = new Vector<String>();
			message_post.add(payload.get("user").toString());
			message_post.add(payload.get("message").toString());
			String message_display = (String) this.serverMessage.execute(this.props.getProperty("message_handler") + ".postMessage", message_post);
			jsonObject.put("message", message_display);
		} else {
			jsonObject.put("error", "user not found");
			status = 404;
		}
		String result = "" + jsonObject;
		return Response.status(status).entity(result).build();
	}
	
}
