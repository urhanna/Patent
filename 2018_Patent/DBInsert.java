package Kipris_Open_API;

import java.sql.*;

public class DBInsert {
	public void insertQuery(StringBuffer sql) {
		Connection connection = null;
		Statement st = null;

		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://203.234.62.172:3306/new_patent?autoReconnect=true&useSSL=false", "root", "ami1223");
			st = connection.createStatement();

			st.executeUpdate(sql.toString());

			st.close();
			connection.close();

			System.out.println("* Data save!");
		} catch (SQLException se1) {
			se1.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void insertData(String id, String title, String adv, String gdv, String author, String listCPC,
			String listIPC, String gid, String odv, String oid, String applicant, String abstracts) {
		Connection connection = null;
		Statement st = null;

		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://203.234.62.172:3306/new_patent?autoReconnect=true&useSSL=false", "root", "ami1223");
			st = connection.createStatement();

			String sql;
			sql = "INSERT INTO patent VALUES ('" + id + "', '" + title + "', '" + adv + "', '" + gdv + "', '" + author
					+ "', '" + listCPC + "', '" + listIPC + "', '" + gid + "', '" + odv + "', '" + oid + "', '"
					+ applicant + "', '" + abstracts + "');";

			st.executeUpdate(sql.toString());

			st.close();
			connection.close();

		} catch (SQLException se1) {
			se1.printStackTrace();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void insertClaim(String id, String claim) {
		Connection connection = null;
		Statement st = null;

		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://203.234.62.172:3306/new_patent?autoReconnect=true&useSSL=false", "root", "ami1223");
			st = connection.createStatement();

			String sql;
			sql = "INSERT INTO patent_claim VALUES ('" + id + "', '" + claim + "');";

			st.executeUpdate(sql.toString());

			st.close();
			connection.close();

		} catch (SQLException se1) {
			se1.printStackTrace();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
