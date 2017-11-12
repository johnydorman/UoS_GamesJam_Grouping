package Utils;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Utils {
	
	private Utils(){}

	public static FileReader getDataFile(String fileName){
		//TODO: exceptions shouldn't be handled here
		try {
			return new FileReader("./data/" + fileName);
		} catch (FileNotFoundException e) {
			// File not found can't continue
			e.printStackTrace();
			System.exit(0);
		}
		return null;
	}

}
