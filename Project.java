public class Project {
	public String projectName;

	public Project(String name) {
		projectName = name;
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