//SJSU CMPE 138 Spring 2019 Team-2
package com.sjsu.github.java;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.sjsu.github.util.DBConnection;

public class FollowUser {
	static Logger logger = Logger.getRootLogger();

	public FollowUser()				//default constructor
	{
		PropertyConfigurator.configure("resources/log4j.properties");	//configure log4j
	}
	DBConnection dbconnectobj = new DBConnection();
	static Connection myConn;
	static Statement mystmt;
	  
	public void followuserpage(){
		BranchMenu branchobj = new BranchMenu();
		Home homeobj = new Home();
		Login loginobj = new Login();
		Logout logoutobj = new Logout(); 
			try{ 
				logger.info("Entered into Follow User Page");
				myConn = dbconnectobj.getConnection();
				myConn.setAutoCommit(false);
				mystmt = myConn.createStatement();
				Scanner myObj = new Scanner(System.in);
				System.out.println("----------------------Follow a user------------------------"); 
						
				
				String [] arrayuser_id = loginobj.useremail.split("@");//%"+arrayuser_id[0]+"%;"
				
				ResultSet checkuser_Id = mystmt.executeQuery("SELECT user_id from user where email like '%"+arrayuser_id[0]+"%' ");
				checkuser_Id.next();
				int int_checkuser_Id = ((Number) checkuser_Id.getObject(1)).intValue();		
				
				//SQL query to list all users which he is not following		
				ResultSet followUser = mystmt.executeQuery("select user_id,email, name from user where user_id NOT IN("+int_checkuser_Id+") and user_id NOT IN "
													   + "(select follower from followers where user_id="+int_checkuser_Id+")");
				System.out.println("User_Id  Email                                       Name");
				System.out.println("---------------------------------------------------------");
				
				while(followUser.next()){
					System.out.println(followUser.getInt("user_id")+",      "+followUser.getString("email")+",                                "+followUser.getString("name"));
				}
				System.out.println("---------------------------------------------------------");
				System.out.println("Enter the User Id of the user you want to follow:");
				String user_Id = myObj.next();
				
				//SQL query to add this user as his follower. Print that the user is added successfully
				String insertFollower = "INSERT INTO followers (user_id,follower) VALUES(?,?)";
				
				PreparedStatement insertps = myConn.prepareStatement(insertFollower);
				
				insertps.setInt(1,int_checkuser_Id);
				insertps.setString(2, user_Id);
				insertps.executeUpdate();
				
				myConn.commit();
				mystmt.close();
				myConn.close();
				
				System.out.println("User is Added Sucessfully"); 
				logger.info("Follow User Page End");
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
			} catch(Exception exc){
				exc.printStackTrace();
				logger.error("Exception in Follow User page");
			}
		}
}
