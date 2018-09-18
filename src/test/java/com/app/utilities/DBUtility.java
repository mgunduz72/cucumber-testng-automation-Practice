package com.app.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DBUtility {
	private static Connection connection;
	private static Statement statement;
	private static ResultSet resultSet;
	
	public static void establishConnection(DBType dbType) throws SQLException {
		switch(dbType) {  // this method is only for establishing connection with database
			case ORACLE:
				connection = DriverManager.getConnection(ConfigurationReader.getProperty("oracledb.url"),
														ConfigurationReader.getProperty("oracledb.user"),
														ConfigurationReader.getProperty("oracledb.password"));
				break;
			default:
				connection = null; // if we have any other database other than oracle, connection will be null.
				
		}
	}
	
	// WE MAY HAD MULTIPLE ENUM OPTIONS SUCH AS ORACLE, MYSQL, MARIADB ETC. but we had only one which is oracle , so we used only that in switch statement above.
//	enum DBType{   we added this in a separate utility class as public
//		ORACLE, MYSQL, MARIADB
//	}
	
	public static int getRowsCount(String sql) throws SQLException {
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		resultSet = statement.executeQuery(sql);
		resultSet.last();
		return resultSet.getRow();
	}
	
	public static List<Map<String,Object>> runSQLQuery(String sql) throws SQLException{  // Since we created the connection above, now we can create statements to send queries
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		resultSet = statement.executeQuery(sql);  // After sending the queries, now we are creating the resultSet.
		
		List<Map<String,Object>> list = new ArrayList<>(); // Now it's time to store those data inside the list of maps
		ResultSetMetaData rsMdata = resultSet.getMetaData();
		  
		int colCount = rsMdata.getColumnCount();
		  
		while(resultSet.next()) {
			  Map<String,Object> rowMap = new HashMap<>();
			  
			  for(int col = 1; col <= colCount; col++) {
				  rowMap.put(rsMdata.getColumnName(col), resultSet.getObject(col));	  
			  }
			  
			  
			  list.add(rowMap);
		}
		
		return list;
		
	}
	
	public static void closeConnections() {
		try{
			if(resultSet != null) {
				resultSet.close();
			}
			if(statement != null) {
				statement.close();
			}
			if(connection != null) {
				connection.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}


