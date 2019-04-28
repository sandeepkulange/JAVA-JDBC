package com.educon.test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import com.educon.pojo.Employee;
import com.educon.utils.DBUtils;
public class Program
{
	private static void insert(Statement statement, Employee emp) throws Exception
	{
		String sql = "insert into EmployeeTable values("+emp.getEmpid()+",'"+emp.getFullName()+"','"+emp.getPermanentAddress()+"','"+emp.getJoinDate()+"',"+emp.getSalary()+")";
		int updateCount = statement.executeUpdate(sql);
		System.out.println(updateCount+" row(s) affected.");
	}
	private static void update(Statement statement, int empid, float salary)throws Exception 
	{
		String sql = "update EmployeeTable set salary="+salary+" where emp_id="+empid+"";
		int updateCount = statement.executeUpdate(sql);
		System.out.println(updateCount+" row(s) affected.");
	}
	private static void deleteEmployees(Statement statement, int empid) throws Exception
	{
		String sql = "delete from EmployeeTable where emp_id="+empid+"";
		int updateCount = statement.executeUpdate(sql);
		System.out.println(updateCount+" row(s) affected.");
	}
	private static void printEmployees(Statement statement) throws Exception
	{
		String sql = "select * from EmployeeTable";
		try(ResultSet rs = statement.executeQuery(sql);)
		{
			while( rs.next())
			{
				Employee emp = new Employee(rs.getInt("emp_id"),rs.getString("full_name"),rs.getString("permanent_address"),rs.getDate("join_date"),rs.getFloat("salary"));	
				System.out.println(emp.toString());
			}
		}
	}
	private static Scanner sc = new  Scanner(System.in);
	private static int menuList( )
	{
		System.out.println("0.Exit");
		System.out.println("1.Add Employee");
		System.out.println("2.Update Employee");
		System.out.println("3.Remove Employee");
		System.out.println("4.Print Employee(s)");
		System.out.print("Enter choice	:	");
		return sc.nextInt();
	}
	public static void main(String[] args) 
	{
		try( Connection connection = DBUtils.getConnection();
			 Statement statement = connection.createStatement();)
		{
			int choice;
			while( ( choice = Program.menuList( ) ) != 0 )
			{
				switch( choice )
				{
				case 1:
					Employee emp = new Employee(36,"Sambhaji Salunkhe","Rethare,Karad",Date.valueOf("2017-01-01"),40000);
					Program.insert(statement, emp);
					break;
				case 2:
					Program.update(statement, 36, 45000);
					break;
				case 3:
					Program.deleteEmployees(statement, 36);
					break;
				case 4:
					Program.printEmployees(statement);
					break;
				}
			}
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}
	
}
