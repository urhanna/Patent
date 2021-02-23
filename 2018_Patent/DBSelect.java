package Kipris_Open_API;

import java.sql.*;
import java.util.Vector;

public class DBSelect {
	public Vector<String> selectAppNum(String table) {
		Connection connection = null;
		Statement st = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://203.234.62.172:3306/old_patent?autoReconnect=true&useSSL=false", "root", "ami1223");
			st = connection.createStatement();

			String sql = "SELECT distinct id FROM " + table + ";";

			ResultSet rs = st.executeQuery(sql);

			Vector<String> appNum = new Vector<String>(10);

			while (rs.next()) {
				appNum.addElement(rs.getString("id"));
			}
			// System.out.println(appNum);
			return appNum;

		} catch (SQLException se1) {
			se1.printStackTrace();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
