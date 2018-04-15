package shared;

/**
 * @(#)User.java
 *
 *
 * @author 
 * @version 1.00 2018/4/15
 */

import java.io.*;

public class User implements Serializable {
	private String name;
	
	public String getName(){
		return name;
	}
	public void setName(String n){
		this.name = n;
	}    
}