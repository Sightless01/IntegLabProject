import java.io.*;
import java.lang.ArrayIndexOutOfBoundsException;
public class Reader{
	public static void main(String[] args){

		String fileName = "Users.csv";
		String line = null;

		try{
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while((line = bufferedReader.readLine()) != null){
				String[] cut = line.split(",");
				String cut1 = cut[0];
				String cut2 = cut[1];
				String cut3 = cut[2];
				System.out.print(cut1);

			}

			bufferedReader.close();

		}catch(Exception ex){
			ex.printStackTrace();
		}


	}
}