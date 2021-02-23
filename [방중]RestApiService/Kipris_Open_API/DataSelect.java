package Kipris_Open_API;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.io.FileOutputStream;
import java.io.OutputStream;


public class DataSelect {
	private String year;
	public DataSelect(String year) {
		this.year = year;
	}
	public Vector<String> getAppNum() {
		try {
//			AppNumInsert AppNum = new AppNumInsert();
//			AppNum.Excute();
			
			String file = "C:\\Users\\Jang Hanna\\Downloads\\eclipse-workspaces\\AppNum10_";
			file+=this.year;
			file+=".txt";
			
			System.out.println(file);
			
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = "";
			Vector<String> appNum = new Vector<String>(10);

			while ((line = br.readLine()) != null) {
				appNum.addElement(line);
			}
			br.close();
			
			return appNum;
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}
}

