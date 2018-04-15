package shared;

/**
 * @(#)MessageServer.java
 *
 *
 * @author 
 * @version 1.00 2018/4/15
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessageServer extends Remote{
    
    //method to login to chat system (mc is used as a callback and session manager)
    public void login(MessageCallback mc) throws RemoteException, AlreadyLoggedInException;
    
    //method to broadcast a message to all logged in clients using mc as session manager
    public void broadcast(MessageCallback mc, String msg) throws RemoteException, NotLoggedInException;
    
    //method to logout from the chat server using mc as the session manager
    public void logout(MessageCallback mc) throws RemoteException, NotLoggedInException;
}