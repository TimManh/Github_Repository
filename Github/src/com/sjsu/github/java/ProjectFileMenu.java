//SJSU CMPE 138 Spring 2019 Team-2
package com.sjsu.github.java;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.sjsu.github.util.DBConnection;

public class ProjectFileMenu {
	static Logger logger = Logger.getRootLogger();

	public ProjectFileMenu()				//default constructor
	{
		PropertyConfigurator.configure("resources/log4j.properties");	//configure log4j
	}
	DBConnection dbconnectobj = new DBConnection();
	static Connection myConn;
	static Statement mystmt;
	  
	public void projectFileMenupage(){
		Home homeobj = new Home();
		Logout logoutobj = new Logout();
		CreateProjectFile projectfileobj = new CreateProjectFile();
		BranchMenu branchobj = new BranchMenu();
		RepoMenu repomenuobj = new RepoMenu();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date df = new Date();
		
		
			try{ 
				logger.info("Entered Project File Menu Login Page");
				myConn = dbconnectobj.getConnection();
				myConn.setAutoCommit(false); 
				mystmt = myConn.createStatement();
				Scanner myObj = new Scanner(System.in);
				
				ResultSet fileCheck = mystmt.executeQuery("SELECT * FROM project_files where repo_id="+repomenuobj.user_Repo_Id+" AND branch_id="+branchobj.user_Branch_Id);
				System.out.println("Repo_Id  Branch_Id   Project_Id");
				System.out.println("-------------------------------");
				while(fileCheck.next()){
					System.out.println(fileCheck.getInt("repo_id")+",          "+fileCheck.getString("branch_id")+",          "+fileCheck.getString("project_id"));
				}
								
				
				System.out.println("----------------------------------------------");
				System.out.println("               Project File  Menu             ");
				System.out.println("----------------------------------------------");
				System.out.println("1.Commit\n2.Pull Request\n3.Go to Home\n4.Logout");
				System.out.println("Enter your choice:");
				int choice = myObj.nextInt();
				
				
				int repo_id = repomenuobj.user_Repo_Id;
				int branch_id = branchobj.user_Branch_Id;
				ResultSet commit_Idcheck = mystmt.executeQuery("SELECT max(commit_id) from commits");
				commit_Idcheck.next();
				int int_commitid = ((Number) commit_Idcheck.getObject(1)).intValue();
				
				ResultSet pullreq_Idcheck = mystmt.executeQuery("SELECT max(pullreq_id) from pull_requests");
				pullreq_Idcheck.next();
				int int_pullreq_Id = ((Number) pullreq_Idcheck.getObject(1)).intValue();
				
				switch(choice){
					case 1:	
							System.out.println("Enter your comment:");
							String comment = myObj.next();
				
				String insertCommits = "INSERT into commits(commit_id, repo_id, branch_id)"+
									"VALUES(?,?,?)";
							
				String insertCommit_Details = "INSERT into commit_details(commit_id, comments, commit_created_at)"+
									"VALUES(?,?,?)";
								
				PreparedStatement insertcommitsps = myConn.prepareStatement(insertCommits);
				insertcommitsps.setInt(1, int_commitid+1);
				insertcommitsps.setInt(2, repo_id);
				insertcommitsps.setInt(3, branch_id);
				insertcommitsps.executeUpdate();
				
				PreparedStatement insertcommitdetailsps = myConn.prepareStatement(insertCommit_Details);
									
				insertcommitdetailsps.setInt(1, int_commitid+1);
				insertcommitdetailsps.setString(2, comment);
				insertcommitdetailsps.setString(3, dateFormat.format(df));
				insertcommitdetailsps.executeUpdate();
				myConn.commit();
				mystmt.close();
				myConn.close();
				System.out.println("Commit Performed Sucessfully:");
				System.out.println("-----------------------------");
				logger.info("Commit Performed");
				homeobj.homepage();
				break;
				
				
			case 2:	
						//System.out.println("Enter your comment:");
						//String comment = myObj.next();
			
			String insertPull = "INSERT into pull_requests(repo_id,pullreq_id, branch_id)"+
								"VALUES(?,?,?)";
						
			String insertPullreq_details = "INSERT into pullreq_details(pullreq_id, pull_request_date)"+
								"VALUES(?,?)";
							
			PreparedStatement insertPullPs = myConn.prepareStatement(insertPull);
			insertPullPs.setInt(1, repo_id);
			insertPullPs.setInt(2, int_pullreq_Id+1);
			insertPullPs.setInt(3, branch_id);
			insertPullPs.executeUpdate();
			
			PreparedStatement insertPullreqdetailsps = myConn.prepareStatement(insertPullreq_details);
								
			insertPullreqdetailsps.setInt(1, int_pullreq_Id+1);
			insertPullreqdetailsps.setString(2, dateFormat.format(df));
			insertPullreqdetailsps.executeUpdate();
			myConn.commit();
			mystmt.close();
			myConn.close();
			System.out.println("Pull Request Performed Sucessfully:");
			System.out.println("-----------------------------------");
			logger.info("Pull Request Performed");
				
				/*
				 * Scanner myObje = new Scanner(System.in); //user_id(pk), password, name,
				 * email, city, state, country
				 * 
				 * System.out.println("Enter your name :"); name = myObje.next();
				 * 
				 * user_id = 13;
				 * 
				 * System.out.println("Enter your email Id :"); email = myObje.next();
				 * 
				 * System.out.println("Enter your password :"); password = myObje.next();
				 */
							
							
							//SQL QUERY TO CREATE A COMMIT with the comment we have taken from user If successful print. created
							//Print commented successfully.
							branchobj.branchMenupage();
							break; 	
						 
					case 3: homeobj.homepage();
						
						break;
						
					case 4: logoutobj.logoutpage();
						break; 
						
					default: System.out.println("Please enter valid choice.");
					homeobj.homepage();
					logger.info("Project File Menu Displayed");
				}
				
				
				
			} catch(Exception exc){
				exc.printStackTrace();
				logger.error("Exception in Project File Menu Page");
			}
		}


}
