package shared;

import java.io.Serializable;
import java.util.ArrayList;

public class Person implements Serializable {
	public ArrayList<Project> projectList;
	public String name;
	public String userName;
	public String password;
	public boolean leaderStat;
	public boolean adminStat;
	public boolean status;

	public Person(String name, String userName, String password) {
		this.name = name;
		this.userName = userName;
		this.password = password;
		projectList = new ArrayList<Project>();
		status = true;
		leaderStat = false;
		adminStat = false;
	}

	public Person(String name) {
		this.name = name;
	}

	public ArrayList<Project> getProject() {
		return projectList;
	}
	
	public String printProjectList() {
		String list = "";
		for(Project proj : projectList) {
			list += "Project Name : " + proj.projectName + "\n";
		}
		return list;
	}
	
	public Project getProject(String name) {
		if(projectList.contains(new Project(name))) {
			return projectList.get(projectList.indexOf(new Project(name)));
		}
		return null;
	}

	public void addProject(Project project) {
		projectList.add(project);
	}

	public void setActive() {
		status = true;
	}

	public void setInActive() {
		status = false;
	}

	public void setLeader() {
		leaderStat = true;
	}

	public void demoteLeader() {
		leaderStat = false;
	}

	@Override
	public boolean equals(Object o) {
		boolean bool = false;
		if(o instanceof Person) {
			Person person = (Person)o;
			bool = person.userName.equals(this.userName);
		}
		return bool;
	}
}