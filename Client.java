package client;

import shared.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class Client {
	
	public static void main(String[] args) {
		try {
			Scanner kbd = new Scanner(System.in);
			System.out.print("Enter your UserName: ");
			String userName = kbd.nextLine();
			System.out.print("Enter your Password: ");
			String password = kbd.nextLine();
			System.out.print("Enter the IP address of your server: ");
			String address = kbd.nextLine();
			System.out.print("Enter the Port number of your server: ");
			int port = Integer.parseInt(kbd.nextLine());
			Registry registry = LocateRegistry.getRegistry(address, port);
			Module module = (Module) registry.lookup("server");
			int mode = module.login(userName, password);
			switch(mode) {
				case 1: 
					showAdminOptions();
					break;
				default: 
					System.out.println();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	public static void showAdminOptions() {
		System.out.println("1. start the server.");
		System.out.println("2. stop the server.");
		System.out.println("3. activate a user.");
		System.out.println("4. de-activate a user.");
		System.out.println("5. create a new Project.");
		System.out.println("6. show The project List.");
	}

	public void userAdminFunctions(int choice) {
	}
}