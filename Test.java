import java.util.ArrayList;
import java.util.List;

public class Test {
	static List<Project> projList = new ArrayList<Project>();
	public static void main(String[] args) {
		projList.add(new Project("Ivan"));
		projList.add(new Project("Ivan2"));
		projList.add(new Project("Ivan3"));

		System.out.println(getProject("Ivan"));
		System.out.println(getProject("Ivan4"));
	}

	public static Project getProject(String name) {
		if(projList.contains(new Project(name))) {
			return projList.get(projList.indexOf(new Project(name)));
		}
		return null;
	}
}