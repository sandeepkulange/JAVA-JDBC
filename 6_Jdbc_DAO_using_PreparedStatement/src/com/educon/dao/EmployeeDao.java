package com.educon.dao;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.educon.pojo.Employee;
import com.educon.utils.DBUtils;

public class EmployeeDao implements Closeable
{
	private PreparedStatement insertStatement;
	private PreparedStatement updateStatement;
	private PreparedStatement deleteStatement;
	private PreparedStatement selectStatement;
	public EmployeeDao( )throws Exception  
	{
		Connection connection = DBUtils.getConnection();
		this.insertStatement = connection.prepareStatement("insert into EmployeeTable values(?,?,?,?,?)");
		this.updateStatement = connection.prepareStatement("update EmployeeTable set salary=? where emp_id=?");
		this.deleteStatement = connection.prepareStatement("delete from EmployeeTable where emp_id=?");
		this.selectStatement = connection.prepareStatement("select * from EmployeeTable");
	}
	public int insert( Employee emp )throws Exception
	{
		this.insertStatement.setInt(1, emp.getEmpid());
		this.insertStatement.setString(2, emp.getFullName());
		this.insertStatement.setString(3, emp.getPermanentAddress());
		this.insertStatement.setDate(4, emp.getJoinDate());
		this.insertStatement.setFloat(5, emp.getSalary());
		int updateCount = this.insertStatement.executeUpdate( );
		return updateCount;
	}
	public int update( int empid, float salary )throws Exception
	{
		this.updateStatement.setFloat(1, salary);
		this.updateStatement.setInt(2, empid);
		int updateCount = this.updateStatement.executeUpdate( );
		return updateCount;
	}
	public int delete( int empid )throws Exception
	{
		this.deleteStatement.setInt(1, empid);
		int updateCount = this.deleteStatement.executeUpdate( );
		return updateCount;
	}
	public List<Employee> getEmployees( ) throws Exception
	{
		List<Employee> empList = new ArrayList<Employee>();
		try(ResultSet rs = this.selectStatement.executeQuery( );)
		{
			while( rs.next())
			{
				Employee emp = new Employee(rs.getInt("emp_id"),rs.getString("full_name"),rs.getString("permanent_address"),rs.getDate("join_date"),rs.getFloat("salary"));	
				empList.add(emp);
			}
		}
		return empList;
	}
	@Override
	public void close() throws IOException 
	{
		try 
		{
			this.insertStatement.close();
			this.updateStatement.close();
			this.deleteStatement.close();
			this.selectStatement.close();
			DBUtils.closeConnection();
		} 
		catch (Exception cause) 
		{
			throw new IOException(cause);
		}
	}
}
