package com.educon.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.educon.pojo.Employee;
public class Program
{
	public static void main(String[] args) 
	{
		Connection connection = null;
		Statement statement = null;
		try
		{
			InputStream inputStream = new FileInputStream("Config.properties");
			Properties p = new  Properties();
			p.load(inputStream);
			
	
			Class.forName(p.getProperty("DRIVER"));
			
			connection = DriverManager.getConnection(p.getProperty("URL"), p.getProperty("USER"), p.getProperty("PASSWORD"));
			
			statement = connection.createStatement();
			
			String sql = "select * from EmployeeTable";
			ResultSet rs = statement.executeQuery(sql);
			
			while( rs.next())
			{
				Employee emp = new Employee();
				
				emp.setEmpid(rs.getInt("emp_id"));
				
				emp.setFullName(rs.getString("full_name"));		
				
				emp.setPermanentAddress(rs.getString("permanent_address"));
				
				emp.setJoinDate(rs.getDate("join_date"));
			
				emp.setSalary(rs.getFloat("salary"));	
				
				System.out.println(emp.toString());
			}
			rs.close();
		}
		catch( SQLException | ClassNotFoundException | IOException ex )
		{
			ex.printStackTrace();
		}
		finally 
		{
			//Step 5 : Close the resources
			try 
			{
				if( statement != null )
					statement.close();
				if( connection != null )
					connection.close();
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
}