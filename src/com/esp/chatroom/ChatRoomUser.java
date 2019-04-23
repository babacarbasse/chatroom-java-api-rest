package com.esp.chatroom;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ChatRoomUser extends Remote{
	public  ArrayList<String> subscribe(String pseudo) throws RemoteException;
	public void unsubscribe(String pseudo) throws RemoteException;
}