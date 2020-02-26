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

public class RepoMenu {
	static Logger logger = Logger.getRootLogger();

	public RepoMenu()				//default constructor
	{
		PropertyConfigurator.configure("resources/log4j.properties");	//configure log4j
	}
	DBConnection dbconnectobj = new DBConnection();
	static Connection myConn;
	static Statement mystmt;
	static int user_Repo_Id;
	boolean flag=false;
	public void repoMenupage(){
		Home homeobj = new Home();
		Logout logoutobj = new Logout();
		CreateBranch branchobj = new CreateBranch();
		Main mainobj = new Main();
		BranchMenu branchmenuobj = new BranchMenu();
		RepoMenu repomenuobj = new RepoMenu();
			try{ 
				logger.info("Entered Repo Menu Page");
				myConn = dbconnectobj.getConnection();
				myConn.setAutoCommit(false); 
				mystmt = myConn.createStatement();
				Scanner myObj = new Scanner(System.in);
							
				ResultSet repoCheck = mystmt.executeQuery("SELECT * FROM respository");
				System.out.println("Repo_Id  Repo_Url                Repo_Name     Repo_Description                       Repo_Created_At User_Id");
				System.out.println("-------------------------------------------------------------------------------------------------------------");
				//System.out.println(repoCheck);
				
				
				//ResultSet myRs = mystmt.executeQuery("select * from users");
				while(repoCheck.next()){
					System.out.println(repoCheck.getInt("repo_id")+",  "+repoCheck.getString("repo_url")+",  "+repoCheck.getString("repo_name")+",  "+
					repoCheck.getString("repo_desc")+",  "+repoCheck.getDate("repo_created_at")+",  "+repoCheck.getInt("user_id"));
				}
				System.out.println("-------------------------------------------------------------------------------------------------------------");
				System.out.println("Enter Your Repo ID");
				user_Repo_Id = myObj.nextInt();
				
				ResultSet repocheck = mystmt.executeQuery("SELECT max(repo_id) FROM respository");
				repocheck.next();
				
				
				System.out.println("----------------------------------------------");
				System.out.println("                Repository Menu               ");
				System.out.println("----------------------------------------------");
				
				System.out.println("1.View Branches\n2.Create a Branch\n3.Go to Home\n4.Logout");
				System.out.println("Enter your choice:");
				int choice = myObj.nextInt();
				 
				switch(choice){
					case 1: branchmenuobj.branchMenupage();
							break; 	
					
					case 2: branchobj.createBranchpage();  
							break;
					
					case 3: homeobj.homepage();
						
						break;
						
					case 4: logoutobj.logoutpage();
						break; 
						
					default: System.out.println("Please enter valid choice.");
					homeobj.homepage();
					logger.info("Repo Menu displayed");
				}
			

			} catch(Exception exc){
				exc.printStackTrace();
				logger.error("Exception in Repo Menu Page");
			}
		}
}
