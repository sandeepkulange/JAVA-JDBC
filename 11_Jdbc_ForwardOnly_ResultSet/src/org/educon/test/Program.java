package org.educon.test;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import com.educon.utils.DBUtils;

public class Program
{
	public static void print( ResultSet rs )throws Exception
	{
		String fullName = rs.getString("full_name");
		int empid = rs.getInt("emp_id");
		float salary = rs.getFloat("salary");
		Date joinDate = rs.getDate("join_date");
		System.out.printf("%-30s%-5d%-10.2f%-15s\n",fullName, empid, salary, joinDate);
	}
	public static void main(String[] args) 
	{	
		try( Connection connection = DBUtils.getConnection();
			 Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY); )
		{
			String sql = "select full_name, emp_id, salary, join_date  from EmployeeTable";
			try( ResultSet rs = statement.executeQuery(sql))
			{
				while( rs.next())
					Program.print(rs);
			}
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}
}
