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

public class AdminLogin {
	static Logger logger = Logger.getRootLogger();

	public AdminLogin()				//default constructor
	{
		PropertyConfigurator.configure("resources/log4j.properties");	//configure log4j
	}
	
	DBConnection dbconnectobj = new DBConnection();
	Home homeobj = new Home();
	Main mainobj = new Main();
	Logout logoutobj = new Logout();
	static Connection myConn;
	static Statement mystmt;
	static String useremail;
	boolean flag=false;
	  
	public void adminloginpage(){
			try{ 
				logger.info("Entered into Admin Login Page");
				myConn = dbconnectobj.getConnection();
				myConn.setAutoCommit(false); 
				mystmt = myConn.createStatement();
				
				Scanner myObj = new Scanner(System.in);
				System.out.println("------------------Admin Login------------------------"); 
				System.out.println("Enter your email Id :");
				useremail = myObj.next();
				System.out.println("Enter your password :"); 
				String userpass = myObj.next();
				
				ResultSet credCheck = mystmt.executeQuery("SELECT email,e_password FROM USER");
				while(credCheck.next()) {
				if((useremail.equalsIgnoreCase("admin@gmail.com"))&&(userpass.equalsIgnoreCase("admin"))){
						flag=true;
						break;
							}else {
								flag = false;
							}
						}
					
				if(flag==false) {
					System.out.println("Enter valid credentials");
					mainobj.main(null);
				}else {
					System.out.println("login successful");
					logger.info("Admin Logged in");
					System.out.println("--------------------------------------");
					System.out.println("               Admin Menu             ");
					System.out.println("--------------------------------------");
					System.out.println("1.Delete User\n2.Delete Repository\n3.Rename Repo Name\n4.Rename Branch Name\n5.Logout");
					System.out.println("Enter your choice:");
					
					int choice = myObj.nextInt();
					 
					switch(choice){
						case 1:	
							
							ResultSet userCheck = mystmt.executeQuery("SELECT user_id, name FROM user");
							System.out.println("User_Id  Name");
							System.out.println("-------------");
							while(userCheck.next()){
								System.out.println(userCheck.getInt("user_id")+",      "+userCheck.getString("name"));
							}
							System.out.println("-------------");
							System.out.println("Enter User Id of user whom you want to delete:");
							String user_Id = myObj.next();
							int int_userid = Integer.parseInt(user_Id);
							
				
				String deleteUser = ("DELETE FROM user where user_id=?");
									
				PreparedStatement deleteUserPs = myConn.prepareStatement(deleteUser);
				deleteUserPs.setInt(1, int_userid);
				deleteUserPs.executeUpdate();
									
				myConn.commit();
				mystmt.close();
				myConn.close();
				logger.info("User Deleted");
				System.out.println("User with User Id "+int_userid+" deleted Sucesfully");
				homeobj.homepage();
				break; 	
								 	
						case 2: 
							ResultSet repoCheck = mystmt.executeQuery("SELECT repo_id, repo_name FROM respository");
							System.out.println("Repo_Id  Repo_Name");
							System.out.println("------------------");
							while(repoCheck.next()){
								System.out.println(repoCheck.getInt("repo_id")+",          "+repoCheck.getString("repo_name"));
							}
							System.out.println("------------------");
							System.out.println("Enter Repo Id of Repository which you want to delete:");
							String repo_Id = myObj.next();
							int int_repoid = Integer.parseInt(repo_Id);
										
							String deleteRepo = ("DELETE FROM respository where repo_id=?");
							PreparedStatement deleteRepoPs = myConn.prepareStatement(deleteRepo);
							deleteRepoPs.setInt(1, int_repoid);
							deleteRepoPs.executeUpdate();
							
							myConn.commit();
							mystmt.close();
							myConn.close();
							logger.info("Repository Deleted");
							System.out.println("Repository with Repo Id "+int_repoid+" deleted Sucesfully");
							homeobj.homepage();
							break;
						
						case 3: 
							ResultSet repocheck = mystmt.executeQuery("SELECT repo_id, repo_name FROM respository");
							System.out.println("Repo_Id  Repo_Name");
							System.out.println("------------------");
							while(repocheck.next()){
								System.out.println(repocheck.getInt("repo_id")+",          "+repocheck.getString("repo_name"));
							}
							System.out.println("------------------");
							System.out.println("Enter Repo Id of Repository which you want to Rename:");
							String repo_id = myObj.next();
							int int_repoId = Integer.parseInt(repo_id);
							System.out.println("Enter New Name of Repository with repo id "+int_repoId+"");
							String new_Repo_Name = myObj.next();
										
							String renameRepo = ("UPDATE respository set repo_name = ? where repo_id = ?;");
							PreparedStatement renameRepoPs = myConn.prepareStatement(renameRepo);
							renameRepoPs.setString(1, new_Repo_Name);
							renameRepoPs.setInt(2, int_repoId);
							renameRepoPs.executeUpdate();
							
							myConn.commit();
							mystmt.close();
							myConn.close();
							logger.info("Repository Renamed");
							System.out.println("Repository with Repo Id "+int_repoId+" renamed Sucesfully as "+new_Repo_Name+"");
							homeobj.homepage();
							break;
								
						
						case 4: 
							ResultSet branchcheck = mystmt.executeQuery("SELECT branch_id, branch_name FROM branches");
							System.out.println("Branch_Id  Brnach_Name");
							System.out.println("----------------------");
							while(branchcheck.next()){
								System.out.println(branchcheck.getInt("branch_id")+",          "+branchcheck.getString("branch_name"));
							}
							System.out.println("----------------------");
							System.out.println("Enter Branch Id of Branch which you want to Rename:");
							String branch_id = myObj.next();
							int int_branchId = Integer.parseInt(branch_id);
							System.out.println("Enter New Name of Branch with repo id "+int_branchId+"");
							String new_Branch_Name = myObj.next();
										
							String renameBranch = ("UPDATE branches set branch_name = ? where branch_id = ?;");
							PreparedStatement renameBranchPs = myConn.prepareStatement(renameBranch);
							renameBranchPs.setString(1, new_Branch_Name);
							renameBranchPs.setInt(2, int_branchId);
							renameBranchPs.executeUpdate();
							
							myConn.commit();
							mystmt.close();
							myConn.close();
							logger.info("Branch Renamed");
							System.out.println("Branch with Branch Id "+int_branchId+" renamed Sucesfully as "+new_Branch_Name+"");
							homeobj.homepage();
							break;
						
						case 5: logoutobj.logoutpage();
						default: 
							System.out.println("Please enter valid choice.\n");
							mainobj.main(null);
						   		 
					}
					
				}
				
				
				
				//System.out.println("user name is: "+useremail+" password is :"+userpass);
				
				
				
				
			/*
			 * while (rec.next()) {
			 * 
			 * 
			 * if (useremail == rec.getString("email")) { if (userpass ==
			 * rec.getString("password")) { System.out.println("Logged in!"); } else {
			 * System.out.println("Password did not match username!"); } } else {
			 * System.out.println("Username did not match the database"); }
			 * 
			 * }
			 */
		  
				/*
				 * 
				 Write a query to check if the email and password is correct. 
				 If it is correct-> go ahead
				 else-> ask for mail and password again. (Use goto:)
				 
				 
				 ResultSet myRs = mystmt.executeQuery("select * from users");
				while(myRs.next()){
					System.out.println(myRs.getString("user_id")+", "+myRs.getString("password")+", "+myRs.getString("name"));
				}*/
				
				
				
			} catch(Exception exc){
				exc.printStackTrace();
				logger.error("Exception in Admin Login Page");
			}
		}

}
