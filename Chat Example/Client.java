package client;

/**
 * @(#)Client.java
 *
 *
 * @author 
 * @version 1.00 2018/4/16
 */
import shared.*;
import java.rmi.registry.*;
import java.util.Scanner;
import java.rmi.server.*;

public class Client {
    public static void main(String[] args) {
        try{
        	Registry reg = LocateRegistry.getRegistry("localhost",1099);
        	MessageServer msgs = (MessageServer) reg.lookup("msgserver");
        	Scanner input = new Scanner(System.in);
        	String line = "";
        	MessageCallbackImplementation mci = null;
        	
        	while(true){
        		try{
        			do{
        				System.out.print("Enter your username: ");
        				line = input.nextLine().trim();
        			}while(line.equals(""));
        			User u = new User();
        			u.setName(line);
        			mci = new MessageCallbackImplementation(u);
        			UnicastRemoteObject.exportObject(mci);
        			msgs.login(mci);
        			break;
        		}catch(AlreadyLoggedInException e){
        			System.out.println("already logged in.. you can't login twice. until you logout..");
        		}
        		System.out.println("Chat now!\n");
        		
        		while(true){
        			line = input.nextLine();
        			if(!line.equals("bye")){
        				msgs.broadcast(mci, line);
        			}else{
        				msgs.logout(mci);
        				break;
        			}
        		}
        	}
        	
        	System.exit(0);
        }catch(Exception e){
        	e.printStackTrace();
        }
    }
}
