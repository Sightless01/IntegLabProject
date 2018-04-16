package client;

/**
 * @(#)MessageCallbackImplementation.java
 *
 *
 * @author 
 * @version 1.00 2018/4/15
 */

import shared.*;
import java.rmi.*;
import java.io.*;
import java.rmi.server.*;

public class MessageCallbackImplementation extends UnicastRemoteObject implements MessageCallback{
	
	//private member variable holding the user/client details
	private User user;
	
	//constructor that assigns the current user
	public MessageCallbackImplementation(User u) throws RemoteException{
		this.user = u;
	}
	
	//return current user of callback
	public User getUser() throws RemoteException{
		return this.user;
	}
	
	//all methods below are printing messages to the console
	//if you are going to create a gui, these methods should
	//be able to repaint/redraw
	public void loginCall(User u) throws RemoteException{
		System.out.println(u.getName()+" logged in ...");
	}
	
	public void broadcastCall(User u,String msg) throws RemoteException{
		System.out.println("["+u.getName()+"]: "+msg);
	}
	public void logoutCall(User u) throws RemoteException{
		System.out.println(u.getName()+"logged out ...");
	}
}