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

public class CreateProjectFile {
	static Logger logger = Logger.getRootLogger();

	public CreateProjectFile()				//default constructor
	{
		PropertyConfigurator.configure("resources/log4j.properties");	//configure log4j
	}
	DBConnection dbconnectobj = new DBConnection();
	static Connection myConn;
	static Statement mystmt;
	  
	public void createProjectfilepage(){
		Home homeobj = new Home();
		BranchMenu branchMenuobj = new BranchMenu();
		RepoMenu repomenuobj = new RepoMenu();
		logger.info("Entered into Create Project File Page");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date df = new Date();
		
			try{ 
				myConn = dbconnectobj.getConnection();
				myConn.setAutoCommit(false); 
				mystmt = myConn.createStatement();
				
				Scanner myObj = new Scanner(System.in);
				System.out.println("----------------------Create a Project File------------------------"); 
				
				System.out.println("Enter your Project Name :");
				String projectName = myObj.next();
				
				ResultSet project_Idcheck = mystmt.executeQuery("SELECT max(project_id) from project_files");
				project_Idcheck.next();
				int int_project_Id = ((Number) project_Idcheck.getObject(1)).intValue();
				//Date projectfiledate = new Date();
				
				String insertProject = "INSERT into project_files(repo_id, branch_id, project_id)"+
						"VALUES(?,?,?)";
				
				String insertProject_Details = "INSERT into project_details(project_id, project_name, project_created_at)"+
						"VALUES(?,?,?)";
					
				PreparedStatement insertProjectPs = myConn.prepareStatement(insertProject);
				insertProjectPs.setInt(1, repomenuobj.user_Repo_Id);
				insertProjectPs.setInt(2, branchMenuobj.user_Branch_Id);
				insertProjectPs.setInt(3, int_project_Id+1);
				insertProjectPs.executeUpdate();
	
				PreparedStatement insertProject_DetailsPs = myConn.prepareStatement(insertProject_Details);
						
				insertProject_DetailsPs.setInt(1, int_project_Id+1);
				insertProject_DetailsPs.setString(2, projectName);
				insertProject_DetailsPs.setString(3, dateFormat.format(df));
				insertProject_DetailsPs.executeUpdate();
				myConn.commit();
				mystmt.close();
				myConn.close();
				  
				logger.info("Project File Created");  
				/*
				 * 
				 Write a query to add the project file details.
				 INSERT project_files VALUES(?,?,?);
				 
				 ResultSet myRs = mystmt.executeQuery("select * from users");
				while(myRs.next()){
					System.out.println(myRs.getString("user_id")+", "+myRs.getString("password")+", "+myRs.getString("name"));
				}*/
				
				System.out.println("Project "+projectName+" created Sucessfully"); 
				branchMenuobj.branchMenupage();
				
			} catch(Exception exc){
				exc.printStackTrace();
				logger.error("Exception in Create Project File page");
			}
		}

}
