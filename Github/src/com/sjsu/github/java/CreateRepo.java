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

public class CreateRepo {
	static Logger logger = Logger.getRootLogger();

	public CreateRepo()				//default constructor
	{
		PropertyConfigurator.configure("resources/log4j.properties");	//configure log4j
	}
	DBConnection dbconnectobj = new DBConnection();
	static Connection myConn;
	static Statement mystmt;
	  
	public void createRepopage(){
		Home homeobj = new Home();
		Login loginobj = new Login();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date df = new Date();
		
			try{ 
				logger.info("Entered into Create Repo Page");
				myConn = dbconnectobj.getConnection();
				myConn.setAutoCommit(false); 
				mystmt = myConn.createStatement();
				
				Scanner myObj = new Scanner(System.in);
				
				System.out.println("----------------------Create Repository------------------------"); 
				
				ResultSet repo_Id = mystmt.executeQuery("SELECT max(repo_id) from respository");
				repo_Id.next();
				int int_repo_Id = ((Number) repo_Id.getObject(1)).intValue();
				
				String [] arrayuser_id = loginobj.useremail.split("@");//%"+arrayuser_id[0]+"%;"
				
				ResultSet checkuser_Id = mystmt.executeQuery("SELECT user_id from user where email like '%"+arrayuser_id[0]+"%' ");
				checkuser_Id.next();
				int int_checkuser_Id = ((Number) checkuser_Id.getObject(1)).intValue();
				
				System.out.println("Enter your Repo Url :");
				String repoUrl = myObj.next();
				
				System.out.println("Enter your Repo Name :");
				String repoName = myObj.next();
				
				System.out.println("Enter your Repo Desc :"); 
				String repoDesc = myObj.next();
				
				ResultSet user_Id = mystmt.executeQuery("SELECT max(user_id) from user");
				user_Id.next();
				int int_user_Id = ((Number) user_Id.getObject(1)).intValue();
				
				String createRepo = "INSERT into respository(repo_id, repo_url, repo_name, repo_desc ,repo_created_at,user_id)"+
						"VALUES(?,?,?,?,?,?)";
				
				PreparedStatement insertps = myConn.prepareStatement(createRepo);
				
				insertps.setInt(1,int_repo_Id+1);
				insertps.setString(2, repoUrl);
				insertps.setString(3, repoName);
				insertps.setString(4, repoDesc);
				insertps.setString(5, dateFormat.format(df));
				insertps.setInt(6, int_checkuser_Id);
				insertps.executeUpdate();
				
				myConn.commit();
				mystmt.close();
				myConn.close();
				logger.info("Repo Created");
				
				System.out.println("Repository "+repoName+" created Sucessfully"); 
				homeobj.homepage();
			} catch(Exception exc){
				exc.printStackTrace();
				logger.error("Exception in Create Repo page");
			}
		}

}
