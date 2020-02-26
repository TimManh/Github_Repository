//SJSU CMPE 138 Spring 2019 Team-2
package com.sjsu.github.java;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.sjsu.github.util.DBConnection;

public class ViewFollowers {
	static Logger logger = Logger.getRootLogger();

	public ViewFollowers()				//default constructor
	{
		PropertyConfigurator.configure("resources/log4j.properties");	//configure log4j
	}
	DBConnection dbconnectobj = new DBConnection();
	static Connection myConn;
	static Statement mystmt;
	  
	public void viewfollowerpage(){
		BranchMenu branchobj = new BranchMenu();
		Home homeobj = new Home();
		Login loginobj = new Login();
		Logout logoutobj = new Logout();
		ProjectFileMenu projectfilemenuobj = new ProjectFileMenu();
		CreateProjectFile projectfileobj = new CreateProjectFile();
			try{ 
				logger.info("Entered into View Followers Page");
				myConn = dbconnectobj.getConnection();
				myConn.setAutoCommit(false); 
				mystmt = myConn.createStatement();
				Scanner myObj = new Scanner(System.in);
				System.out.println("----------------------Followers------------------------"); 
				
				String [] arrayuser_id = loginobj.useremail.split("@");//%"+arrayuser_id[0]+"%;"
				
				ResultSet checkuser_Id = mystmt.executeQuery("SELECT user_id from user where email like '%"+arrayuser_id[0]+"%' ");
				checkuser_Id.next();
				int int_checkuser_Id = ((Number) checkuser_Id.getObject(1)).intValue();
				
				ResultSet followersCheck = mystmt.executeQuery("SELECT * FROM followers where user_id="+int_checkuser_Id);
				System.out.println("User_Id  Follower_Id");
				System.out.println("--------------------");
				
				while(followersCheck.next()){
					System.out.println(followersCheck.getInt("user_id")+",           "+followersCheck.getString("follower"));
				}
				System.out.println("--------------------");
				//SQL QUERY TO VIEW THE FOLLOWERS
				//select * from Followers;
				/*
				ResultSet myRs = mystmt.executeQuery("select * from users");
				while(myRs.next()){
					System.out.println(myRs.getString("user_id")+", "+myRs.getString("password")+", "+myRs.getString("name"));
				}*/
				
				System.out.println("1.Go to Home\n2.Logout");
				System.out.println("Enter your choice:");
				int choice = myObj.nextInt();
				 
				switch(choice){
					case 1: homeobj.homepage();
							break;
					case 2: logoutobj.logoutpage();
							break; 
					
					default: System.out.println("Please enter valid choice.");
							homeobj.homepage();

				}
				logger.info("Followers Displayed");
				
				
			} catch(Exception exc){
				exc.printStackTrace();
				logger.error("Exception in View Followers Page");
			}
		}
}
