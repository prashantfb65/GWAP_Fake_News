/**
Created By:               P R A S H A N T   G A R  G  |  STUDENT ID : 16201447
Created Date:             20-April-2017
Copyright:                University College Dublin
Subject:				  COLLECTIVE INTELLIGENCE (GWAP Application)
Description:              This file provides the Application with JDBC connection 
 						  to perform action on database tables
						  handled here.
Version:                  00.00.00.01

Modification history:
----------------------------------------------------------------------------------------------------------------------------
Modified By         Modified Date (dd/mm/yyyy)                Version               Description
---------------------------------------------------------------------------------------------------------------------------
 **/
package com.jdbc.connection;


/*
 * Inclusion of Header Files
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionFactory {

	Connection conn = null; 	//Creating Connection class variable
	String databaseName = "";	// Holds Database Name 
	String databaseUser = "";	// Holds Username for MySQL database
	String databasePass = "";	// Holds Password for MySQL database

	/**
	 * Method Name: [getConnection]
	 * Returns the Connection object, which can be then used for 
	 * interacting with MySQL database
	 * @param command line arguments
	 * 
	 */
	public Connection getConnection() throws SQLException {
		
		BufferedReader br; 	//BufferedReader variable 
		try {
			br = new BufferedReader(new FileReader("database_configuration.txt"));
			String line = null;			//Represents the Line Being Read from Buffered Reader
			/** 
			 * Reading the Complete File and Retrieving DB Name, User and Password 
			 * from the text file. These three parameters are used to jdbc driver
			 * config
			 */
			try {
				while ((line = br.readLine()) != null) {
					String[] split = line.split("=");
					if("database".equalsIgnoreCase(split[0])){
						databaseName = split[1];
					}if("database_user".equalsIgnoreCase(split[0])){
						databaseUser = split[1];
					}if("database_password".equalsIgnoreCase(split[0])){
						databasePass = split[1];
					}
				}
			} catch (IOException e) {
				System.out.println("Some error while reading the input file");
			}
		} catch (FileNotFoundException e) {
			System.out.println("The DB Configuration File doesn't Exist :: " + e);
		}

		String databaseURL = "jdbc:mysql://localhost:3306/"+ databaseName+ "?autoReconnect=true&useSSL=false";
		Properties props = new Properties();
		props.put("user", databaseUser);
		props.put("password", databasePass);
		conn = DriverManager.getConnection(databaseURL, props);
		return conn;
	}
	/**
	 * End of Method [getConnection]
	 */

	/**
	 * Method Name: [releaseConnection]
	 * Closes the Collection to avoid stale connections within 
	 * application
	 * @param command line arguments
	 * 
	 */
	public void releaseConnection() throws SQLException {
		conn.close();
	}
	/**
	 * End of Method [releaseConnection]
	 */
}

/**
 * End of File
 * 
 */
