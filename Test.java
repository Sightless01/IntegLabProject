package shared;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;

public class Test {

	public static void main(String[] args) {
		/**
		//get file to upload
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		int returnValue = jfc.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			System.out.println(selectedFile.getAbsolutePath());
			System.out.println(selectedFile.getName());
		}
		**/
		ArrayList<Person> members = new ArrayList<>();
		ArrayList<Project> projects = new ArrayList<>();

		Person person = new Person("User1", "Admin", "adminPass");
		person.adminStat = true;
		members.add(person);

		try {
			FileOutputStream memberOut = new FileOutputStream("members.obj");
            ObjectOutputStream memOut = new ObjectOutputStream(memberOut);
         	memOut.writeObject(members);
            FileOutputStream projectOut = new FileOutputStream("projects.obj");
            ObjectOutputStream projOut = new ObjectOutputStream(projectOut);
         	projOut.writeObject(projects);
            memOut.close();
            memberOut.close();
			projOut.close();
			projectOut.close();
		} catch(IOException i) {
			i.printStackTrace();
		}

		
	}
}