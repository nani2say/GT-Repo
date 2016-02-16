package test;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * Java program to connect to MySQL Server database running on localhost,
 * using JDBC type 4 driver.
 **/

public class Test
{
    public static void main(String args[])
    {
    	
        String dbURL = "jdbc:mysql://localhost:3306/supermarket";
        String username ="nani";
        String password = "1234";
       
        Connection dbCon = null;
        Statement stmt = null;
        ResultSet rs = null;
       
        String query = "select * from sample";
       
        try 
        {
            //getting database connection to MySQL server
            dbCon = DriverManager.getConnection(dbURL, username, password);
           
            //getting PreparedStatment to execute query
            stmt = dbCon.prepareStatement(query);
           
            //Resultset returned by query
            rs = stmt.executeQuery(query);
           
            while(rs.next())
            {
             String fname = rs.getString(1);
             String lname = rs.getString(2);
             System.out.println("First Name : " + fname);
             System.out.println("First Name : " + lname);
             }
           
          }
          catch (SQLException ex)
          {
           System.out.println(ex);
          }
        finally
        {
           //close connection ,stmt and resultset here
        }
       
    }  
   
}