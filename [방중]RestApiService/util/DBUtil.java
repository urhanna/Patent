package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

	private Connection con;

	public DBUtil() {
		getConnection(null);
	}
	
	public DBUtil(String dbname) {
		getConnection(dbname);
	}

	public void getConnection(String dbname) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
//			String url = "jdbc:mysql://150.183.113.120:3306/";
			String url = "jdbc:mysql://150.183.113.143:3306/";
			url += dbname;
			url += "?useUnicode=true&characterEncoding=UTF-8";
			con = DriverManager.getConnection(url, "root", "spark");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet executeQuery(String sql) {
		if (con == null)
			return null;
		try {
			Statement stat = con.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean execute(String query) {
		try {
			Statement stat = con.createStatement();
			stat.execute(query);
			return true;
		} catch (SQLException e) {
			if (e.getMessage().contains("Duplicate")) {
			} else {
				System.out.println("ERR MSG : " + e.getMessage());
				System.out.println("DB ERROR QUERY : " + query);
			}
			return false;
		}
	}

	public boolean sendQuery(String query) {
		Statement stmt;
		try {
			stmt = con.createStatement();
			stmt.execute(query);
			stmt.close();
			return true;
		} catch (SQLException e) {
			if (e.getMessage().contains("Duplicate")) {
			} else {
				System.out.println("ERR MSG : " + e.getMessage());
				System.out.println("DB ERROR QUERY : " + query);
			}
			return false;
		}

	}
}
