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

public class BranchMenu {
	static Logger logger = Logger.getRootLogger();

	public BranchMenu()				//default constructor
	{
		PropertyConfigurator.configure("resources/log4j.properties");	//configure log4j
	}
	DBConnection dbconnectobj = new DBConnection();
	static Connection myConn;
	static Statement mystmt;
	static int user_Branch_Id;
	public void branchMenupage(){
		
		BranchMenu branchobj = new BranchMenu();
		Home homeobj = new Home();
		Logout logoutobj = new Logout();
		ProjectFileMenu projectfilemenuobj = new ProjectFileMenu();
		CreateProjectFile projectfileobj = new CreateProjectFile();
		RepoMenu repomenuobj = new RepoMenu();
		BranchMenu branchMenuobj = new BranchMenu();
		PreparedStatement psmt = null;
		Connection conn = null;

			try{ 
				logger.info("Entered into Branch Menu Page");
				myConn = dbconnectobj.getConnection();
				myConn.setAutoCommit(false); 
				mystmt = myConn.createStatement();
				Scanner myObj = new Scanner(System.in);
				
				ResultSet branchCheck = mystmt.executeQuery("SELECT * FROM branches where repo_id="+repomenuobj.user_Repo_Id);
				System.out.println("Repo_Id  Branch_Id  Branch_Name");
				System.out.println("-------------------------------");
				//System.out.println(branchCheck);
				
				
				//ResultSet myRs = mystmt.executeQuery("select * from users");
				while(branchCheck.next()){
					System.out.println(branchCheck.getInt("repo_id")+",          "+branchCheck.getString("branch_id")+",          "+branchCheck.getString("branch_name"));
				}
				System.out.println("-------------------------------");
				System.out.println("Enter Your Branch ID");
				user_Branch_Id = myObj.nextInt();
				
				System.out.println("----------------------------------------------");
				System.out.println("                  Branch Menu                 ");
				System.out.println("----------------------------------------------");
				logger.info("Displayed Branch Menu");
								
				System.out.println("1.View Project Files\n2.Create a Project File\n3.View Commits\n4.View Pull Requests\n5.Go to Home\n6.Logout");
				System.out.println("Enter your choice:");
				int choice = myObj.nextInt();
				 
				switch(choice){
					case 1:	projectfilemenuobj.projectFileMenupage();
							break; 	
					
					case 2: projectfileobj.createProjectfilepage(); 
							break;
					
					case 3: 
						
						ResultSet commitsCheck = mystmt.executeQuery("select distinct cd.commit_id, cd.comments, cd.commit_created_at from commit_details cd, "
								+ "commits c where cd.commit_id=c.commit_id and c.repo_id="+repomenuobj.user_Repo_Id+" and c.branch_id="+branchMenuobj.user_Branch_Id+"");
						
						System.out.println("Commit_Id  Comments                Commit Created At");
						System.out.println("----------------------------------------------------");
						
						while(commitsCheck.next()){
							System.out.println(commitsCheck.getInt("commit_id")+",         "+commitsCheck.getString("comments")+",         "+commitsCheck.getString("commit_created_at"));
						}
						System.out.println("----------------------------------------------------");
						
						branchobj.branchMenupage();
						break;
						
					case 4: 
						ResultSet pullCheck = mystmt.executeQuery("select distinct pd.pullreq_id, pd.pull_request_date from pullreq_details pd, pull_requests p "
							+ "where pd.pullreq_id=p.pullreq_id and p.repo_id="+repomenuobj.user_Repo_Id+" and p.branch_id="+branchMenuobj.user_Branch_Id+"");
						
						System.out.println("Pull_Request_Id   Pull_Request_Date");
						System.out.println("-----------------------------------");
						
						while(pullCheck.next()){
							System.out.println(pullCheck.getInt("pullreq_id")+",                "+pullCheck.getString("pull_request_date"));
						}
						System.out.println("-----------------------------------");
						
						branchobj.branchMenupage();
						break;
					
					case 5: homeobj.homepage();
						
						break;
						
					case 6: logoutobj.logoutpage();
						break; 
						
					default: System.out.println("Please enter valid choice.");
						homeobj.homepage();
				}
				
				
				
			} catch(Exception exc){
				exc.printStackTrace();
				logger.error("Exception in Branch Menu page"); 
			}
		}

}
