package Kipris_Open_API;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataRemove {
	public static void main(String[] args) {
		Connection connection = null;
		Statement st = null;

		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://203.234.62.172:3306/new_patent?autoReconnect=true&useSSL=false", "root", "ami1223");
			st = connection.createStatement();

			String file = "C:\\Users\\ami\\Desktop\\file\\AppNum.txt";
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = "";

			int i = 1;
			while ((line = br.readLine()) != null) {

				String sql = "delete from patent where id = '" + line + "';";
				st.executeUpdate(sql.toString());

				System.out.println(i + " Data Remove!");
				i++;
			}
			
			br.close();
			st.close();
			connection.close();

			System.out.println("* Data Removed!");
		} catch (SQLException se1) {
			se1.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
