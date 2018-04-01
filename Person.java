import java.util.ArrayList;

public class Person {
	public String name;
	public String userName;
	public String password;
	private ArrayList<Project> projectList;

	public Person(String name, String userName, String password) {
		this.name = name;
		this.userName = userName;
		this.password = password;
		projectList = new ArrayList<Project>();
	}
	
	public void printProjectList() {
		for(Project proj : projectList) {
			System.out.println(proj.projectName);
		}
	}
	
	public Project getProject(String name) {
		if(projectList.contains(new Project(name))) {
			return projectList.get(projectList.indexOf(new Project(name)));
		}
		return null;
	}
}