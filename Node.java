package shared;

import java.io.File;
import java.io.Serializable;
import java.util.Calendar;

public class Node implements Serializable {
	public File file;
	public final String date;
	public String author;
	public String fileName;
	public String description;

	public Node(File file, String author, String description) {
		Calendar calendar = Calendar.getInstance();
		this.file = file;
		date = calendar.getTime().toString();
		this. author = author;
		fileName = file.getName();
		this.description = description;
	}

	@Override
	public String toString() {
		return "File Name: " + fileName + "\nDate Uploaded: " + date + "\nDescription: " + description;
	}

	@Override
	public boolean equals(Object o) {
		boolean bool = false;
		if(o instanceof Node) {
			Node node = (Node)o;
			bool = node.fileName.equals(this.fileName);
		}
		return bool;
	}
}