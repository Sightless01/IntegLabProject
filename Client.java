package client;

import shared.*;
import java.io.File;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class Client {
	private static Module module = null;
	private static String userInUse = "";
	public static void main(String[] args) {
		try {

			Scanner kbd = new Scanner(System.in);
			
			int mode = 0;
			do {
				try {
					System.out.println("To quit type \"quit\" in the user name");
					System.out.print("Enter your UserName: ");
					String userName = kbd.nextLine();
					if(userName.equalsIgnoreCase("quit")) {
						System.exit(0);
					}
					userInUse = userName;
					System.out.print("Enter your Password: ");
					String password = kbd.nextLine();
					System.out.print("Enter the IP address of your server: ");
					String address = kbd.nextLine();
					System.out.print("Enter the Port number of your server: ");
					int port = Integer.parseInt(kbd.nextLine());
					Registry registry = LocateRegistry.getRegistry(address, port);
					module = (Module) registry.lookup("server");
					mode = module.login(userName, password);
					System.out.println(mode);
				} catch(UnRegisteredUserException uue) {
					System.out.println("Username not registered.");
				} catch(UserAlreadyLoggedInException ualie) {
					System.out.println("this user is already logged-in.");
				} catch(WrongPasswordException wpe) {
					System.out.println("the password is incorrect.");
				} catch(NotActiveUserException nue) {
					System.out.println("The user is currently inactive. to activate contact your administrator.");
				} catch(Exception e) {
					e.printStackTrace();
				}
			} while(mode == 0);
			
			while(true) {
				switch(mode) {
					case 1: 
						showAdminOptions();
						System.out.print("Enter your choice(1-9): ");
						int choice = Integer.parseInt(kbd.nextLine());
						userAdminFunctions(choice);
						break;
					case 2:
						showLeaderOptions();
						System.out.print("Enter your choice(1-7): ");
						choice = Integer.parseInt(kbd.nextLine());
						userLeaderFunctions(choice);
						break;
					case 3:
						showMemberOptions();
						System.out.print("Enter your choice(1-4): ");
						choice = Integer.parseInt(kbd.nextLine());
						userMemberFunctions(choice);
						break;
					default: 
						System.out.println();
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	public static void showAdminOptions() {
		System.out.println("1. start the server.");
		System.out.println("2. stop the server.");
		System.out.println("3. register a user.");
		System.out.println("4. activate a user.");
		System.out.println("5. de-activate a user.");
		System.out.println("6. create a new Project.");
		System.out.println("7. set a leader for a Project.");
		System.out.println("8. show The project List.");
		System.out.println("9. logout.");
	}

	public static void showLeaderOptions() {
		System.out.println("1. show The project List.");
		System.out.println("2. assign a member to a project.");
		System.out.println("3. remove a member from the project.");
		System.out.println("4. mark project as complete.");
		System.out.println("5. upload file.");
		System.out.println("6. enter chat.");
		System.out.println("7. logout.");
	}

	public static void showMemberOptions() {
		System.out.println("1. show The project List.");
		System.out.println("2. upload file.");
		System.out.println("3. enter chat.");
		System.out.println("4. logout.");
	}

	public static void userAdminFunctions(int choice) throws Exception {
		Scanner kbd = new Scanner(System.in);
		switch(choice) {
			case 1:
				System.out.println(module.startServer());
				break;
			case 2:
				System.out.println(module.stopServer());
				break;
			case 3:
				try {
					System.out.print("Enter the name: ");
					String name = kbd.nextLine();
					System.out.print("Enter the username: ");
					String userName = kbd.nextLine();
					System.out.print("Enter the password: ");
					String password = kbd.nextLine();
					System.out.println(module.registerPerson(name, userName, password));
				} catch(UserAlreadyExistException uaee) {
					System.out.println("This username is already taken.");
				}
				break;
			case 4:
				try {
					System.out.print("Enter the username: ");
					String userName = kbd.nextLine();
					System.out.println(module.activateUser(userName));
				} catch(UnRegisteredUserException urue) {
					System.out.println("The user is not registered.");
				}
				break;
			case 5:
				try {
					System.out.print("Enter the username: ");
					String userName = kbd.nextLine();
					System.out.println(module.deactivateUser(userName));
				} catch(UnRegisteredUserException urue) {
					System.out.println("The user is not registered.");
				}
				break;
			case 6:
				try {
					System.out.print("Enter the project name: ");
					String projectName = kbd.nextLine();
					System.out.println(module.createProject(projectName));
				} catch(ProjectAlreadyExistException paee) {
					System.out.println("The project name is already in use.");
				}
				break;
			case 7:
				try {
					System.out.print("Enter the username: ");
					String userName = kbd.nextLine();
					System.out.print("Enter the project name: ");
					String projectName = kbd.nextLine();
					System.out.println(module.setLeader(userName, projectName));
				} catch(UnRegisteredUserException urue) {
					System.out.println("The user is not registered.");
				} catch(ProjectDoesNotExistException pdnee) {
					System.out.println("The project does not Exist");
				}
				break;
			case 8:
				try {
					System.out.println("Project List: ");
					System.out.println(module.showProjects(userInUse));
				} catch(UnRegisteredUserException urue) {
					System.out.println("The user is not registered.");
				}
				break;
			case 9:
				System.out.println(module.logout(userInUse));
				System.exit(0);
				break;
			default:
				System.out.println("invalid choice");
		}
	}

	public static void userLeaderFunctions(int choice) throws Exception {
		Scanner kbd = new Scanner(System.in);
		switch(choice) {
			case 1:
				try {
					System.out.println("Project List: ");
					System.out.println(module.showProjects(userInUse));
				} catch(UnRegisteredUserException urue) {
					System.out.println("The user is not registered.");
				} catch(ServerIsClosedException sice) {
					System.out.println("The server is closed.");
				}
				break;
			case 2:
				try {
					System.out.println(module.showMembers());
					System.out.print("Enter project name: ");
					String projectName = kbd.nextLine();
					System.out.print("Enter the user name to be added: ");
					String toBeAdded = kbd.nextLine();
					System.out.println(module.assignMember(projectName, userInUse, toBeAdded));
				} catch(UnRegisteredUserException urue) {
					System.out.println("The user is not registered.");
				} catch(NotALeaderException nae) {
					System.out.println("You are not the leader for this project.");
				} catch(ProjectDoesNotExistException pdnee) {
					System.out.println("The project does not Exist");
				} catch(ServerIsClosedException sice) {
					System.out.println("The server is closed.");
				}
				break;
			case 3:
				try {
					System.out.println("Enter project name: ");
					String projectName = kbd.nextLine();
					System.out.println(module.showMembersOfProject(projectName));
					System.out.println("Enter the user name to be removed: ");
					String toBeRemoved = kbd.nextLine();
					System.out.println(module.removeUser(projectName, userInUse, toBeRemoved));
				} catch(UnRegisteredUserException urue) {
					System.out.println("The user is not registered.");
				} catch(NotALeaderException nae) {
					System.out.println("You are not the leader for this project.");
				} catch(ProjectDoesNotExistException pdnee) {
					System.out.println("The project does not Exist");
				} catch(ServerIsClosedException sice) {
					System.out.println("The server is closed.");
				}
				break;
			case 4:
				try {
					System.out.println("Enter project name: ");
					String projectName = kbd.nextLine();
					System.out.println(module.completeProject(projectName, userInUse));
				} catch(NotALeaderException nae) {
					System.out.println("You are not the leader for this project.");
				} catch(ProjectDoesNotExistException pdnee) {
					System.out.println("The project does not Exist");
				} catch(ServerIsClosedException sice) {
					System.out.println("The server is closed.");
				}
				break;
			case 5:
				try {
					JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
					int returnValue = jfc.showOpenDialog(null);
					System.out.println("Enter project name: ");
					String projectName = kbd.nextLine();
					System.out.println("Enter the description of the project: ");
					String description = kbd.nextLine();
					if (returnValue == JFileChooser.APPROVE_OPTION) {
						File selectedFile = jfc.getSelectedFile();
						System.out.println(module.addFile(selectedFile, projectName, userInUse, description));
					}
				} catch(ProjectDoesNotExistException pdnee) {
					System.out.println("The project does not Exist");
				} catch(ServerIsClosedException sice) {
					System.out.println("The server is closed.");
				}
				break;
			case 6:
				break;
			case 7:
				System.out.println(module.logout(userInUse));
				System.exit(0);
				break;
			default:
				System.out.println("invalid choice");
		}
	}

	public static void userMemberFunctions(int choice) throws Exception {
		Scanner kbd = new Scanner(System.in);
		switch(choice) {
			case 1:
				try {
					System.out.println("Project List: ");
					System.out.println(module.showProjects(userInUse));
				} catch(UnRegisteredUserException urue) {
					System.out.println("The user is not registered.");
				} catch(ServerIsClosedException sice) {
					System.out.println("The server is closed.");
				}
				break;
			case 2:
				try {
					JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
					int returnValue = jfc.showOpenDialog(null);
					System.out.println("Enter project name: ");
					String projectName = kbd.nextLine();
					System.out.println("Enter the description of the project: ");
					String description = kbd.nextLine();
					if (returnValue == JFileChooser.APPROVE_OPTION) {
						File selectedFile = jfc.getSelectedFile();
						System.out.println(module.addFile(selectedFile, projectName, userInUse, description));
					}
				} catch(ProjectDoesNotExistException pdnee) {
					System.out.println("The project does not Exist");
				} catch(ServerIsClosedException sice) {
					System.out.println("The server is closed.");
				}
				break;
			case 3:
				break;
			case 4:
				System.out.println(module.logout(userInUse));
				System.exit(0);
				break;
			default:
				System.out.println("invalid choice");
		}
	}
}