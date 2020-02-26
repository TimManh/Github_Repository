//SJSU CMPE 138 Spring 2019 Team-2
package com.sjsu.github.java;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.sjsu.github.util.DBConnection;

public class Logout {
	static Logger logger = Logger.getRootLogger();

	public Logout()				//default constructor
	{
		PropertyConfigurator.configure("resources/log4j.properties");	//configure log4j
	}
	DBConnection dbconnectobj = new DBConnection();
	Main mainobj = new Main();
	static Connection myConn;
	static Statement mystmt;
	
	public void logoutpage(){
		try{
			logger.info("Entered into Logout Page");
			myConn = dbconnectobj.getConnection();
			myConn.setAutoCommit(false);
			mystmt = myConn.createStatement();
			System.out.println("------Thank you....You are currently Logged out------"); 
			System.out.println("=======//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\======");
			System.out.println("-------Please Login again or Signup to continue------");
			logger.info("Logout message displayed");
			/* 
			 
			 SQL QUERY TO LOGOUT.
			 ResultSet myRs = mystmt.executeQuery("select * from users");
			while(myRs.next()){
				System.out.println(myRs.getString("user_id")+", "+myRs.getString("password")+", "+myRs.getString("name"));
			}
			 */
			 
			mainobj.main(null);
			
		} catch(Exception exc){
			exc.printStackTrace();
			logger.error("Exception in Logout Page");
		}
	}
	
}
