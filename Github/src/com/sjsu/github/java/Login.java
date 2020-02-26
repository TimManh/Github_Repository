//SJSU CMPE 138 Spring 2019 Team-2
package com.sjsu.github.java;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.sjsu.github.util.DBConnection;

public class Login {
	static Logger logger = Logger.getRootLogger();
	static Cipher cipher;  

	public Login()				//default constructor
	{
		PropertyConfigurator.configure("resources/log4j.properties");	//configure log4j
	}
	DBConnection dbconnectobj = new DBConnection();
	Home homeobj = new Home();
	Main mainobj = new Main();
	static Connection myConn;
	static Statement mystmt;
	static String useremail;
	boolean flag=false;
	  
	public void loginpage(){
			try{ 
				logger.info("Entered into Login Page");
				myConn = dbconnectobj.getConnection();
				myConn.setAutoCommit(false);
				mystmt = myConn.createStatement();
				
				Scanner myObj = new Scanner(System.in);
				System.out.println("----------------------Login------------------------"); 
				System.out.println("Enter your email Id :");
				useremail = myObj.next();
				System.out.println("Enter your password :"); 
				String userpass = myObj.next();
				
				//======================Password Encryption Start============================
				
				  //String passwordToHash = "password";
		            String encrypt_Password = null;		        
		            MessageDigest md = MessageDigest.getInstance("MD5");	
		            md.update(userpass.getBytes());
		            byte[] bytes = md.digest();
		            StringBuilder sb = new StringBuilder();
		            for(int i=0; i< bytes.length ;i++)
		            {
		                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		            }
		            encrypt_Password = sb.toString();
		
				//======================Password Encryption End============================
				
				ResultSet credCheck = mystmt.executeQuery("SELECT email,e_password FROM USER");
				
				while(credCheck.next()) {
				if((useremail.equalsIgnoreCase(credCheck.getString("email")))&&(encrypt_Password.equalsIgnoreCase(credCheck.getString("e_password")))){
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
					homeobj.homepage();
					logger.info("User Logged in");
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
				logger.error("Exception in Login Page");
			}
		}
}
