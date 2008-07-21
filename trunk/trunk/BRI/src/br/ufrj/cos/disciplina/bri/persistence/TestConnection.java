package br.ufrj.cos.disciplina.bri.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {

	private DB db;

	/**
	 * Default constructor method.
	 */
	public TestConnection() {
		db = new DB();
	}

	/**
	 * Tests the connection.
	 */
	public void test() {
		db.dbConnect("jdbc:mysql://localhost/bri", "root", "admin");
	}
}

/**
 * Inner class for help DB connection testing.
 */
class DB {
	
	/**
	 * Default constructor method. 
	 */
	public DB() {
		
	}

	/**
	 * Connects to database.
	 * @param db_connect_string - the connection string
	 * @param db_userid - the user id
	 * @param db_password - the password
	 */
	public void dbConnect(String db_connect_string, String db_userid,
			String db_password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			@SuppressWarnings("unused")
			Connection conn = DriverManager.getConnection(db_connect_string,
					db_userid, db_password);
			System.out.println("Connection with database OK");
		} catch (SQLException e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
};
