//SJSU CMPE 138 Spring 2019 Team-2
package com.sjsu.github.java;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.sjsu.github.util.DBConnection;

public class CreateBranch {
	static Logger logger = Logger.getRootLogger();

	public CreateBranch()				//default constructor
	{
		PropertyConfigurator.configure("resources/log4j.properties");	//configure log4j
	}
	DBConnection dbconnectobj = new DBConnection();
	static Connection myConn;
	static Statement mystmt;
	  
	public void createBranchpage(){
		Home homeobj = new Home();
		RepoMenu repoMenuobj = new RepoMenu();
		logger.info("Entered into Create Branch Page");
			try{ 
				myConn = dbconnectobj.getConnection();
				myConn.setAutoCommit(false); 
				mystmt = myConn.createStatement();
				
				Scanner myObj = new Scanner(System.in);
				System.out.println("----------------------Create a Branch------------------------"); 
				
				System.out.println("Enter your Branch Name :");
				String branchName = myObj.next();
				
				ResultSet branch_Idcheck = mystmt.executeQuery("SELECT max(branch_id) from branches");
				branch_Idcheck.next();
				int int_branchid = ((Number) branch_Idcheck.getObject(1)).intValue();
				  
				String insertBranches = "INSERT into branches(repo_id, branch_id, branch_name)"+
						"VALUES(?,?,?)";
				
				PreparedStatement insertbranchesps = myConn.prepareStatement(insertBranches);
				insertbranchesps.setInt(1, repoMenuobj.user_Repo_Id);
				insertbranchesps.setInt(2, int_branchid+1);
				insertbranchesps.setString(3, branchName);
				insertbranchesps.executeUpdate();
				
				myConn.commit();
				mystmt.close();
				myConn.close();
				logger.info("Branch Created");
				/*
				 * 
				 Write a query to add the branch details.
				 
				 INSERT branches VALUES('?','?');
				 
				 
				 ResultSet myRs = mystmt.executeQuery("select * from users");
				while(myRs.next()){
					System.out.println(myRs.getString("user_id")+", "+myRs.getString("password")+", "+myRs.getString("name"));
				}*/
				
				System.out.println("Branch "+branchName+" created Sucessfully"); 
				repoMenuobj.repoMenupage();
				
			} catch(Exception exc){
				exc.printStackTrace();
				logger.error("Exception in Create Branch page");
			}
		}

}
