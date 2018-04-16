package shared;

import java.io.Serializable;
import java.util.Scanner;
import java.util.ArrayList;

public class Project implements Serializable {
	public String projectName;
	public ArrayList<Node> nodes = new ArrayList<>();
	public ArrayList<Person> members = new ArrayList<>();
	public Person projectLeader;
	public boolean status;

	public Project(String name) {
		projectName = name;
		status = true;
	}

	public void upload(Node node) {
		nodes.add(node);
	}
	
	public void addMember(Person member) {
		members.add(member);
	}

	public void setLeader(Person leader) {
		projectLeader = leader;
	}

	@Override
	public boolean equals(Object o) {
		boolean bool = false;
		if(o instanceof Project) {
			Project proj = (Project)o;
			bool = proj.projectName.equals(this.projectName);
		}
		return bool;
	}

	@Override
	public String toString() {
		return this.projectName;
	}

}