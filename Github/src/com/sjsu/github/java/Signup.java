//SJSU CMPE 138 Spring 2019 Team-2
package com.sjsu.github.java;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import java.util.Base64; 
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.sjsu.github.util.DBConnection;

public class Signup {
	
	static Logger logger = Logger.getRootLogger();
	static Cipher cipher;  
	
	public Signup()		//default constructor
	{
		PropertyConfigurator.configure("resources/log4j.properties");//configure log4j
	}
	DBConnection dbconnectobj = new DBConnection();
	static Connection myConn;
	static Statement mystmt;
	
	static String name;
	static int user_id;
	static String email;
	static String password;
	static String e_password;
	static String city;
	static String state;
	static String country;
	
	  
	public void signuppage() throws SQLException{
		Main mainobj = new Main();
			try{ 
				logger.info("Entered Signup Page");
				myConn = dbconnectobj.getConnection();
				myConn.setAutoCommit(false); // transaction
				mystmt = myConn.createStatement();
				
				System.out.println("----------------------SignUp------------------------"); 
				Scanner myObj = new Scanner(System.in);
				
				System.out.println("Enter your name :");
				name = myObj.next();
								
				ResultSet user_Id = mystmt.executeQuery("SELECT max(user_id) from user");
				user_Id.next();
				int int_user_Id = ((Number) user_Id.getObject(1)).intValue();
				
				System.out.println("Enter your email Id :");
				email = myObj.next();
				
				System.out.println("Enter your password :"); 
				password = myObj.next();
				//=====================Password Encryption Start======================
				
				//String passwordToHash = "password";
		        String e_Password = null;
		        
		            MessageDigest md = MessageDigest.getInstance("MD5");	
		            md.update(password.getBytes());
		            byte[] bytes = md.digest();
		            StringBuilder sb = new StringBuilder();
		            for(int i=0; i< bytes.length ;i++)
		            {
		                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		            }
		            e_Password = sb.toString();
				
		       //=====================Password Encryption End=========================
				
				System.out.println("Enter your city :"); 
				city = myObj.next();
				
				System.out.println("Enter your state :"); 
				state = myObj.next();
				
				System.out.println("Enter your country :"); 
				country = myObj.next();
				
				System.out.println("User with user name: "+name+" Sucessfully Signed up");
				
				String insertSignup = "INSERT into user(user_id, e_password, name, email,city,state,country)"+
				"VALUES(?,?,?,?,?,?,?)";
				
				PreparedStatement insertps = myConn.prepareStatement(insertSignup);
				
				insertps.setInt(1,int_user_Id+1);
				//insertps.setString(2, password);
				insertps.setString(2, e_Password);
				insertps.setString(3, name);
				insertps.setString(4, email);
				insertps.setString(5, city);
				insertps.setString(6, state);
				insertps.setString(7, country);
				insertps.executeUpdate();
				
				//transaction start
				myConn.commit();
				mystmt.close();
				myConn.close();
				logger.info("Displayed Signup Menu");
				
				mainobj.main(null);
				
			} catch(Exception exc){
				exc.printStackTrace();
				logger.error("Exception in Signup Page");
			}finally {
				myConn.setAutoCommit(true);        //transaction end
			}
			
		}
}
