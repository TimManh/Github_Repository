//SJSU CMPE 138 Spring 2019 Team-2
package com.sjsu.github.java;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.sjsu.github.util.DBConnection;

public class Home {
	static Logger logger = Logger.getRootLogger();

	public Home()				//default constructor
	{
		PropertyConfigurator.configure("resources/log4j.properties");	//configure log4j
	}
	RepoMenu repoMenuobj = new RepoMenu();
	CreateRepo createRepoobj = new CreateRepo();
	Logout logoutobj = new Logout();
	
	DBConnection dbconnectobj = new DBConnection();
	static Connection myConn;
	static Statement mystmt;
	  
	public void homepage(){
			Home homeobj= new Home();
			ViewFollowers followersobj = new ViewFollowers();
			ViewFollowing followingsobj = new ViewFollowing();
			FollowUser followuser = new FollowUser();
			try{ 
				logger.info("Entered into Home Page");
				myConn = dbconnectobj.getConnection();
				myConn.setAutoCommit(false);
				mystmt = myConn.createStatement();
				Scanner myObj = new Scanner(System.in);
				
				System.out.println("----------------------------------------------");
				System.out.println("                      HOME                    ");
				System.out.println("----------------------------------------------");
				
				System.out.println("1.View Repositories\n2.Create Repository\n3.View Followers\n4.View Following\n5.Follow a user\n6.Logout");
				System.out.println("Enter your choice:");
				logger.info("Home menu Displayed");
				int choice = myObj.nextInt();
				 
				switch(choice){
					case 1:	repoMenuobj.repoMenupage();
							break; 	
					
					case 2: createRepoobj.createRepopage(); 
							break;
					
					case 3: 
						followersobj.viewfollowerpage();
						break;
						
					case 4: followingsobj.viewfollowingpage();
						break;
						
					case 5:  followuser.followuserpage();
						break;
						
					case 6: logoutobj.logoutpage();
						break;
						
					default: System.out.println("Please enter valid choice.");
								homeobj.homepage();
				}
				
				
				
			} catch(Exception exc){
				exc.printStackTrace();
				logger.error("Exception in Home page");
			}
		}
}
