package org.educon.test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;

import com.educon.pojo.Employee;
import com.educon.utils.DBUtils;

public class Program
{
	public static void main1(String[] args) 
	{
		String sql= "{call sp_insert_employee(?,?,?,?,?)}";
		
		try( Connection connection = DBUtils.getConnection();
			 CallableStatement statement = connection.prepareCall(sql);)
		{
			statement.setInt(1, 36);
			statement.setString(2, "Shital Kulange");
			statement.setString(3, "Katraj,Pune");
			statement.setDate(4, Date.valueOf("2008-06-15"));
			statement.setFloat(5, 45000);
			
			boolean status = statement.execute();
			//true if the first result is a ResultSet object;
			//false if it is an update count or there are no results
			
			System.out.println(status+"	"+statement.getUpdateCount());
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}
	public static void main2(String[] args) 
	{
		String sql= "{call sp_update_employee(?,?)}";
		
		try( Connection connection = DBUtils.getConnection();
			 CallableStatement statement = connection.prepareCall(sql);)
		{
			statement.setFloat(1, 48000);
			
			statement.setInt(2, 36);
			
			boolean status = statement.execute();
			//true if the first result is a ResultSet object;
			//false if it is an update count or there are no results
			
			System.out.println(status+"	"+statement.getUpdateCount());
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}
	public static void main3(String[] args) 
	{
		String sql= "{call sp_delete_employee(?)}";
		
		try( Connection connection = DBUtils.getConnection();
			 CallableStatement statement = connection.prepareCall(sql);)
		{
			statement.setInt(1, 36);
			
			boolean status = statement.execute();
			//true if the first result is a ResultSet object;
			//false if it is an update count or there are no results
			
			System.out.println(status+"	"+statement.getUpdateCount());
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}
	public static void main(String[] args) 
	{
		String sql= "{call sp_get_employees()}";
		
		try( Connection connection = DBUtils.getConnection();
			 CallableStatement statement = connection.prepareCall(sql);)
		{
			boolean status = statement.execute();
			//true if the first result is a ResultSet object;
			//false if it is an update count or there are no results
			System.out.println("Status	:	"+status);
			try( ResultSet rs = statement.getResultSet())
			{
				while( rs.next())
				{
					Employee emp = new Employee(rs.getInt("emp_id"),rs.getString("full_name"),rs.getString("permanent_address"),rs.getDate("join_date"),rs.getFloat("salary"));	
					System.out.println(emp.toString());
				}
			}
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}
}
