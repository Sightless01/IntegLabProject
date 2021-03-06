package server;

import shared.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ModuleImplementation extends UnicastRemoteObject implements Module {
	private ArrayList<Person> memberList;
	private ArrayList<Project> projectList;
	private ArrayList<Person> loggedInUsers = new ArrayList<>();
	private boolean serverStatus = false;
	public ModuleImplementation(ArrayList<Person> memberList, ArrayList<Project> projectList) throws RemoteException {
		this.memberList = memberList;
		this.projectList = projectList;
		System.out.print(memberList.size());
	}

	@Override
	public int login(String userName, String password) throws RemoteException, UserAlreadyLoggedInException, WrongPasswordException, UnRegisteredUserException, NotActiveUserException {
		System.out.println(memberList.contains(new Person(userName)));
		if(memberList.contains(new Person(userName))) {
			if(!loggedInUsers.contains(new Person(userName))) {
				System.out.println("test");
				if(getPersonByUserName(userName).adminStat) {
					System.out.println("test");
					if(getPersonByUserName(userName).password.equals(password)) {
						System.out.println("test");
						loggedInUsers.add(getPersonByUserName(userName));
						return 1;
					} else {
						throw new WrongPasswordException();
					}
				} else if(getPersonByUserName(userName).leaderStat) {
					if(getPersonByUserName(userName).password.equals(password)) {
						loggedInUsers.add(getPersonByUserName(userName));
						return 2;
					} else {
						throw new WrongPasswordException();
					}
				} else {
					if(getPersonByUserName(userName).password.equals(password)) {
						if(getPersonByUserName(userName).status) {
							loggedInUsers.add(getPersonByUserName(userName));
							return 3;
						} else {
							throw new NotActiveUserException();
						}
					} else {
						throw new WrongPasswordException();
					}
				}
			} else {
				throw new UserAlreadyLoggedInException();
			}
		} else {
			throw new UnRegisteredUserException();
		}
	}

	@Override
	public String logout(String userName) throws RemoteException {
		loggedInUsers.remove(new Person(userName));
		try {
			FileOutputStream memberOut = new FileOutputStream("members.obj");
            ObjectOutputStream memOut = new ObjectOutputStream(memberOut);
         	memOut.writeObject(memberList);
            FileOutputStream projectOut = new FileOutputStream("projects.obj");
            ObjectOutputStream projOut = new ObjectOutputStream(projectOut);
         	projOut.writeObject(projectList);
            memOut.close();
            memberOut.close();
			projOut.close();
			projectOut.close();
		} catch(IOException i) {
			i.printStackTrace();
		}
		return userName + " has successfully logged out!";
	}

	@Override
	public String startServer() throws RemoteException {
		serverStatus = true;
		return "The server is already running!";
	}

	@Override
	public String stopServer() throws RemoteException {
		serverStatus = false;
		return "The server is now closed!";
	}

	@Override
	public String registerPerson(String name, String userName, String password) throws RemoteException, UserAlreadyExistException {
		if(memberList.contains(new Person(userName))) {
			throw new UserAlreadyExistException();
		} else {
			memberList.add(new Person(name, userName, password));
			return name + " is successfully registered!";
		}
	}

	@Override
	public String activateUser(String userName) throws RemoteException, UnRegisteredUserException {
		if(memberList.contains(new Person(userName))) {
			getPersonByUserName(userName).status = true;
			return "The Person with the UserName of " + userName + " is now active!";
		} else {
			throw new UnRegisteredUserException();
		}
	}
	@Override
	public String deactivateUser(String userName) throws RemoteException, UnRegisteredUserException{
		if(memberList.contains(new Person(userName))) {
			getPersonByUserName(userName).status = false;
			return "The Person with the UserName of " + userName + " is now inactive!";
		} else {
			throw new UnRegisteredUserException();
		}
	}

	@Override
	public String createProject(String projectName) throws RemoteException, ProjectAlreadyExistException {
		if(projectList.contains(new Project(projectName))) {
			throw new ProjectAlreadyExistException();
		} else {
			projectList.add(new Project(projectName));
			return "a project named " + projectName + " is successfully created!";
		}
	}

	@Override
	public String setLeader(String userName, String projectName) throws RemoteException, ProjectDoesNotExistException, UnRegisteredUserException {
		if(projectList.contains(new Project(projectName))) {
			if(memberList.contains(new Person(userName))){
				getPersonByUserName(userName).setLeader();
				getPersonByUserName(userName).projectList.add(getProjectByName(projectName));
				getProjectByName(projectName).projectLeader = getPersonByUserName(userName);
				getProjectByName(projectName).members.add(getPersonByUserName(userName));
				return "The Person with the user name of " + userName + " is now the leader of project " + projectName + "!";
			} else {
				throw new UnRegisteredUserException();
			}
		} else {
			throw new ProjectDoesNotExistException();
		}
	}

	@Override
	public String showProjects(String userName) throws RemoteException, UnRegisteredUserException, ServerIsClosedException {
		String list = "";
		if(memberList.contains(new Person(userName))) {
			if(getPersonByUserName(userName).adminStat) {
				for(Project project: projectList) {
					list += "Project Name: " + project.projectName + "\nProject Status: " + (project.status ? "On-going\n" : "Complete\n");
				}
			} else if(getPersonByUserName(userName).leaderStat) {
				if(serverStatus) {
					for(Project project: projectList) {
						list += "Project Name: " + project.projectName + "\nProject Leader: " + (project.projectLeader.userName.equals(userName) ? "Yes\n" : "No\n");
					}
				} else {
					throw new ServerIsClosedException();
				}
			} else {
				if(serverStatus) {
					list = getPersonByUserName(userName).printProjectList();
				} else {
					throw new ServerIsClosedException();
				}
			}
		} else {
			throw new UnRegisteredUserException();
		}
		return list;
	}

	@Override
	public String assignMember(String projectName, String userName, String userToBeAssigned) throws RemoteException, ProjectDoesNotExistException, ServerIsClosedException, NotALeaderException, UnRegisteredUserException {
		if(serverStatus) {
			if(projectList.contains(new Project(projectName))) {
				if(getProjectByName(projectName).projectLeader.userName.equals(userName)) {
					if(memberList.contains(new Person(userToBeAssigned))) {	
						getProjectByName(projectName).members.add(getPersonByUserName(userToBeAssigned));
						getPersonByUserName(userToBeAssigned).projectList.add(getProjectByName(projectName));
						return "The person with the user name of " + userToBeAssigned + " is now part of the project " + projectName + "!";
					} else {
						throw new UnRegisteredUserException();
					}
				} else {
					throw new NotALeaderException();
				}
			} else {
				throw new ProjectDoesNotExistException();
			}
		} else {
			throw new ServerIsClosedException();
		}
	}

	@Override
	public String removeUser(String projectName, String userName, String userToBeRemove) throws RemoteException, ProjectDoesNotExistException, ServerIsClosedException, NotALeaderException, UnRegisteredUserException {
		if(serverStatus) {
			if(projectList.contains(new Project(projectName))) {
				if(getProjectByName(projectName).projectLeader.userName.equals(userName)) {
					if(memberList.contains(new Person(userToBeRemove))) {	
						getProjectByName(projectName).members.remove(getPersonByUserName(userToBeRemove));
						getPersonByUserName(userToBeRemove).projectList.remove(getProjectByName(projectName));
						return "The person with the user name of " + userToBeRemove + " was removed from the project " + projectName + "!";
					} else {
						throw new UnRegisteredUserException();
					}
				} else {
					throw new NotALeaderException();
				}
			} else {
				throw new ProjectDoesNotExistException();
			}
		} else {
			throw new ServerIsClosedException();
		}
	}

	@Override
	public String completeProject(String projectName, String userName) throws RemoteException, ProjectDoesNotExistException, ServerIsClosedException, NotALeaderException {
		if(serverStatus) {
			if(projectList.contains(new Project(projectName))) {
				if(getProjectByName(projectName).projectLeader.userName.equals(userName)) {
					getProjectByName(projectName).status = false;
					return "The project " + projectName + " is now complete!";
				} else {
					throw new NotALeaderException();
				}
			} else {
				throw new ProjectDoesNotExistException();
			}
		} else {
			throw new ServerIsClosedException();
		}
	}


	@Override
	public String addFile(File file, String projectName, String userName, String description) throws RemoteException, ProjectDoesNotExistException, ServerIsClosedException {
		if(serverStatus) {
			if(projectList.contains(new Project(projectName))) {
				getProjectByName(projectName).nodes.add(new Node(file, getPersonByUserName(userName).name, description));
				return "The file named " + file.getName() + " was successfully added to the project " + projectName + "!";
			} else {
				throw new ProjectDoesNotExistException();
			}
		} else {
			throw new ServerIsClosedException();
		}
	}

	@Override
	public String showMembers() throws RemoteException {
		String list = "Member Usernames: ";
		for(Person person : memberList) {
			list += ("\n" + person.toString());
		}
		return list;
	}

	@Override
	public String showMembersOfProject(String projectName) throws RemoteException, ProjectDoesNotExistException {
		String list = "Member Usernames: ";
		if(projectList.contains(new Project(projectName))) {
			for(Person person : memberList) {
				list += ("\n" + person.toString());
			}
		} else {
			throw new ProjectDoesNotExistException();
		}
		return list;
	}

	private Person getPersonByUserName(String userName) {
		return memberList.get(memberList.indexOf(new Person(userName)));
	}

	private Project getProjectByName(String projectName) {
		return projectList.get(projectList.indexOf(new Project(projectName)));
	}

	private ArrayList<Person> getMemberList() {
		return memberList;
	}

	private ArrayList<Project> getProjectList() {
		return projectList;
	}
}