package com.app.tests;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.app.utilities.Driver;

public class JDBCConnection {
	
	String oracleDbUrl = "jdbc:oracle:thin:@ec2-18-59-14-118.us-east-2.compute.amazonaws.com:1521:xe";
	String oracleDbUsername = "hr" ;
	String oracleDbPassword =  "hr";
  
	 @Test(enabled = false)
	  public void oracleJDBC() throws SQLException{
// 	Connection > Statement > ResultSet > Columndata→ This the hierarchy that we should establish in our project. 
//  We can also close all of them as soon as we created to not forget at the end.  	 
		 Connection connection = DriverManager.getConnection(oracleDbUrl, oracleDbUsername, oracleDbPassword);
//		 Statement statement = connection.createStatement(); // This statement will allow us only to use next() method, other methods such as last(), first()
		 //will not work, we need to use below statement 
		 
		 Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); // We can use this, even while we are working on
		 // database, if somebody added new rows, we can jump to that row also to read it. 
		 ResultSet resultSet = statement.executeQuery("select * from countries"); // ResultSet is the the whole data table given from our query.
		 
		 resultSet.next();  // When we call the result set, it's pointing to outside initially, by calling next
		                    // it will take the result set on top of the first column and print it below. 
//		 resultSet.next();  // if I keep saying next, it will go to the next row each time
//		 System.out.println(resultSet.getString(1));
//		 System.out.println(resultSet.getString("country_name")); // If the data is string we need to place it as string.
//		 System.out.println(resultSet.getInt("region_id"));
	
	// This steps provides all data in a single row. we can use while loop for this instead of calling multiple times. 	
//		while( resultSet.next()) {
//		 System.out.println(resultSet.getString(1)+"-"+ resultSet.getString("country_name")+"-"+resultSet.getInt("region_id"));
//		
//		}
//		 resultSet.next();
//		 System.out.println(resultSet.getRow()); // Will give the row number
//		 resultSet.last();
//		 System.out.println(resultSet.getRow());
		 
		 // Find out how many records in the resultSet
		 
		 System.out.println(resultSet.getRow());
		 
		 resultSet.beforeFirst(); // when we use first, we are using the top row, because in while loop, we used next()
		 while( resultSet.next()) {
	     System.out.println(resultSet.getString(1)+"-"+ resultSet.getString("country_name")+"-"+resultSet.getInt("region_id"));
		 }
	
		 resultSet.close();
		 statement.close();
		 connection.close();
	 }
	  @Test
	  public void jdbcMetaData() throws SQLException {  // Without metadata, we can not know, how many column int the table, how many row to scroll up and down, column names etc.
		                            // these info comes with metadata. So when we say SELECT * FROM EMPLOYEES,  we can not get any without metadata
                                    // So, we need more info about result set. 
		 
		     Connection connection = DriverManager.getConnection(oracleDbUrl, oracleDbUsername, oracleDbPassword);
//			 Statement statement = connection.createStatement();  
			 Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); //TYPE_SCROLL_INSENSITIVE: It allows us to go up and down on the table data(rows), use this always necessary
			                                                                                                                  // CONCUR_READ_ONLY: As a tester we want to only read the data, we do not use other options     
		//	 String sql = "select employee_id,last_name,job_id, salary from employees";
			 String sql = "select * from employees";
			 ResultSet resultSet = statement.executeQuery(sql); 
		  
			 //Database Metadata
			 DatabaseMetaData dbMetadata = connection.getMetaData();
			 System.out.println("User : " + dbMetadata.getUserName());
			 System.out.println("Database Type : " + dbMetadata.getDatabaseProductName());
			 
			 // resultSet metadata
			 ResultSetMetaData rsMedata = resultSet.getMetaData() ;  // We must use  ResultSetMetaData to access the results
			 System.out.println("Columns count:" + rsMedata.getColumnCount());
			 System.out.println(rsMedata.getColumnName(1));
			 
			//print all column names using a loop
			 
			 int column = rsMedata.getColumnCount();
			 for (int i = 1; i <=column ; i++) {
				 System.out.println(i +"-->"+ rsMedata.getColumnName(i));
				
			}
			 // Using metadata we accessed the whole data from rows and columns, now we need to store these data using Lists and Maps
			 
			 // Throw resultSet into a List of Maps
			 //Create a list of maps
			 
			 List<Map<String,Object>> list = new ArrayList<>();
			 
			 ResultSetMetaData rsMdata = resultSet.getMetaData() ; // We copied it here to see again, otherwise we could use the one above also
			 
			 int colCount = rsMdata.getColumnCount();
			 
			 while(resultSet.next()) {
				 Map<String,Object> rowMap = new HashMap<>(); // We will add all column info for one row each time and add each map which is a row 
				                                              // to our above list at the end, then we will have all table info in the list above.
			
				 for (int i = 1; i <= colCount; i++) {  // instead of adding each column info in each row one by one we use for loop
					rowMap.put(rsMdata.getColumnName(i), resultSet.getObject(i));//We always get the key value which is the columns using ResultSetMetaData (rsMdata) 
					                                                             // and we always get the object which is the column values using resultSet, this is how we store all each row info in a map
					 
				}
			 
				 list.add(rowMap);
			 
			 }
			 // printing all employee_id  from the list of map we collected.
			 for(Map<String,Object> emp : list) {
				 System.out.println(emp.get("EMPLOYEE_ID"));
			 }
			 
			 resultSet.close();
			 statement.close();
			 connection.close();
	  }

}







