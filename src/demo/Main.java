package demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

	public static void main(String[] args) throws SQLException {
		final String url = "jdbc:mysql://localhost:3306/social?serverTimezone=EST";
		final String username = "root";
		final String password = "rootpwd!";
		
		Connection conn = DriverManager.getConnection(url, username, password);
		System.out.println("It works");
		
		conn.close();
	}

}
