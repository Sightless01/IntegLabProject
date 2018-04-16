package shared;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Module extends Remote {
	public int login(String userName, String password) throws RemoteException, UserAlreadyLoggedInException, WrongPasswordException, UnRegisteredUserException, NotActiveUserException;
	public String logout(String userName) throws RemoteException;
	public String registerPerson(String name, String userName, String password) throws RemoteException, UserAlreadyExistException;
	public String activateUser(String userName) throws RemoteException;
	public String deactivateUser(String userName) throws RemoteException;
	public String createProject(String projectName) throws RemoteException, ProjectAlreadyExistException;
	public String setLeader(String userName, String projectName) throws RemoteException, ProjectDoesNotExistException, UnRegisteredUserException;
	public String showProjects(String userName) throws RemoteException, UnRegisteredUserException, ServerIsClosedException;
	public String AssignMember(String projectName, String userName, String userToBeAssigned) throws RemoteException, ProjectDoesNotExistException, ServerIsClosedException, NotALeaderException, UnRegisteredUserException;
	public String removeUser(String projectName, String userName, String userToBeAssigned) throws RemoteException, ProjectDoesNotExistException, ServerIsClosedException, NotALeaderException, UnRegisteredUserException;
	public String completeProject(String projectName, String userName) throws RemoteException, ProjectDoesNotExistException, ServerIsClosedException, NotALeaderException;
	public String addFile(File file, String projectName, String userName, String description) throws RemoteException, ProjectDoesNotExistException, ServerIsClosedException;
}