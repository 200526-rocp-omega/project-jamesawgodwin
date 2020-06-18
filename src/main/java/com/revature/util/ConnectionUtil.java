package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

	private static Connection conn = null;
	
	//Private constructor PREVENTS us from ever instantiating this class
	private ConnectionUtil() {
		super();
	}
	
	public static Connection getConnection() {
		
		/*
		 * We will be using DriverManager to obtain our connection to the DB
		 * 
		 * We will need to provide it some credential information:
		 * Connection String: jdbc:oracle:thin:@ENDPOINT:PORT:SID
		 * 
		 * jdbc:oracle:thin@ENDPOINT:1521:ORCL
		 * 
		 * @ENDPOINT = 
		 * training.cku14qaihr4c.us-west-2.rds.amazonaws.com
		 * username
		 * password
		 */
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
				try {
					conn = DriverManager.getConnection(
							"jdbc:oracle:thin:@training.cku14qaihr4c.us-west-2.rds.amazonaws.com:1521:ORCL",
							"beaver",
							"chew"); // HARD CODED PASSWORD
					//PUSHING THIS TO GITHB MEANS EVERYONE CAN SEE IT
					//VERY UNSAFE -> use environment variables instead
							
							
				} catch(SQLException e) {
					e.printStackTrace();
				}
		} catch(ClassNotFoundException e) {
			System.out.println("Did not find Oracle JDBC Driver class!");
		}
		
		return conn;
	}
}
