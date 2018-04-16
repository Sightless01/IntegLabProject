package shared;

/**
 * @(#)MessageCallback.java
 *
 *
 * @author 
 * @version 1.00 2018/4/15
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessageCallback extends Remote {
	
	//method to get the current user of the callback/session
	public User getUser() throws RemoteException;
	
	//method called by the server when other clients log in
	//User data is sent as parameter
	public void loginCall(User u) throws RemoteException;
	
	//method called by server when a message is send by clients
	public void broadcastCall(User u, String msg) throws RemoteException;
	
	//method called by server when a user logs out from the system
	//user logging out sent as parameter
	public void logoutCall(User u) throws RemoteException;
}