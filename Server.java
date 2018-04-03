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
	public static ServerSocket server;

	public static void startServer(){
		try{
			server = new ServerSocket(60000);
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

	public static void stopServer(){
		try{
			if(server != null){
				System.out.println("Server stop running");
				server.close();
			}

		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

    public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		while(true){
			System.out.print("Do you want to start the server [y]es : ");
			char runServer = input.next().charAt(0);

			if(runServer == 'y'){
				startServer();
				String exit = input.nextLine();

			}else{
				System.out.println("Server is not running -__- ");
			}
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
