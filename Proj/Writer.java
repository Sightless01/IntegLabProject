import java.io.*;
import java.util.*;

public class Writer{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		String fileName = "Users.csv";

		try{

			FileWriter fr = new FileWriter(fileName, true);
			BufferedWriter bw = new BufferedWriter(fr);

			System.out.print("Enter input: ");
			String in = sc.nextLine();

			bw.write("\r\n"+ in);

			bw.close();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}