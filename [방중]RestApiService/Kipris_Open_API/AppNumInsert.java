package Kipris_Open_API;
import java.io.*;

public class AppNumInsert {

	public static void main(String args[]) throws IOException {
		try {
			String pt10 = "10";
			int year1948 = 1948;
			String pad = "0000000";
	
			
			for (year1948 = 2016; year1948 < 2020; year1948++) {
				FileWriter fw10 = new FileWriter("C:\\Users\\Jang Hanna\\Downloads\\eclipse-workspaces\\"
						+ "AppNum10_" + year1948 + ".txt");

				for (int i = 1; i <= 9999999; i++) {
					String temp = pad.substring(Integer.toString(i).length());
					String str = pt10 + year1948 + temp + i;
					fw10.write(str + '\n');
				}
				System.out.println("데이터가 저장되었습니다.");
				fw10.flush();
				fw10.close();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
