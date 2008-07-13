package br.ufrj.cos.disciplina.bri.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
	
	private DB db;

	public TestConnection() {
		db = new DB();
	}

	public void test() {
		db.dbConnect("jdbc:mysql://localhost/bri", "root", "admin");
	}
}

class DB {
	public DB() {
	}

	public void dbConnect(String db_connect_string, String db_userid,
			String db_password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(db_connect_string,
					db_userid, db_password);
			System.out.println("Connection With Database OK");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
};
