import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.PrintWriter;

/**
 * @(#)Server.java
 *
 *
 * @author Brendon Bruce Viloria
 * @version 1.00 2018/3/27
 */

public class Server {
    public static void main(String[] args) {
        // TODO code application logic here
        try{
        	ServerSocket server = new ServerSocket(60000);
        	System.out.println("Server is running");
        	while(true){
        		MyRunnable work = new MyRunnable(server.accept());
        		Thread worker = new Thread(work);
        		worker.start();
        	}
        }catch(Exception e){
        	e.printStackTrace();
        }
    }


}

class MyRunnable implements Runnable{
    	private PrintWriter writer;
    	private Scanner reader;
    	private Socket socket;

    	MyRunnable(Socket client){
    		try{
    			socket = client;
    			writer = new PrintWriter(client.getOutputStream(),true);
    			reader = new Scanner(client.getInputStream());
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	}

    	public void run(){
    	}
}
