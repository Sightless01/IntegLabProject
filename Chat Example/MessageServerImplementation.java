package shared;

/**
 * @(#)MessageServerImplementation.java
 *
 *
 * @author 
 * @version 1.00 2018/4/15
 */
 
import shared.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.*;
import java.io.*;

public class MessageServerImplementation extends UnicastRemoteObject implements MessageServer {
	//a hashtable (key,value) to hold all online users
	private Map<String, MessageCallback> cb = new Hashtable<>();
	//required constructor that throws RemoteException
	
	
    public MessageServerImplementation() throws RemoteException {
    }
    
    //login  method implementation
    public void login(MessageCallback mc) throws RemoteException, UserExistException{
    	//get current user of callback mc
    	User u  = mc.getUser();
    	if(cb.containsValue(mc)){
    		//already logged in, as in same callback instance of
    		throw new AlreadyLoggedInException();
    	}else if(cb.containsKey(u.getName())){
    		//different callback instance but same name
    		throw new UserExistException();
    	}else {
    		//new user, session; add mc to the current table of online clients 
    		cb.put(u.getName(),mc);
    		System.out.println("login: "+u.getName());
    		System.out.print("Online: [");
    		//loop to send login notification to clients
    		for(String name: cb.keySet()){
    			System.out.print(name+" ");
    			//actual method call to callback
    			cb.get(name).loginCall(u);
    		}
    		System.out.print("]");
    	}
    }
    
    //broadcast method implementation
    public void broadcast(MessageCallback mc, String msg) throws RemoteException, NotLoggedInException{
    	//check if mc/session is not in the table of callbacks/clients
    	//if callback is not found, the method is being called without
    	//first logging in
    	if(!cb.containsValue(mc)){
    		throw new NotLoggedInException();
    	}
    	//get user of mc/callback
    	User u = mc.getUser();
    	//loop to sernd broadcast to all clients/callback
    	for(String name : cb.keySet()){
    		//actual method to send broadcast(sender,message)
    		cb.get(name).broadcastCall(u, msg);
    	}
    }
    
    public void logout(MessageCallback mc) throws RemoteException, NotLoggedInException{
    	//check if mc/session is not in the table of callbacks/clients
    	//if callback is not found, the method is being called without
    	//first logging in
    	if(!cb.containsValue(mc)){
    		throw new NotLoggedInException();
    	}
    	
    	//get current user of mc/callbak
    	User u = mc.getUser();
    	
    	//remover session/callback from the table
    	cb.remove(u.getName());
    	System.out.println("- logout:  "+u.getName());
    	System.out.print("online: [");
    	
    	//loop to send logout message to all clients
    	for(String name :cb.keySet()){
    		System.out.print(name+" ");
    		//actual method call
    		cb.get(name).logoutCall(u);
    	}
    	System.out.print("]");
    }
    
}