package org.educon.test;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import com.educon.utils.DBUtils;

public class Program
{
	public static void main(String[] args) 
	{	
		try( Connection connection = DBUtils.getConnection(); 
			 Statement statement = connection.createStatement();)
		{
			String sql = "select * from EmployeeTable";
			try( ResultSet rs = statement.executeQuery(sql))
			{
				ResultSetMetaData md = rs.getMetaData();
				System.out.println("--------------------------------------------------------");
				System.out.println("Catlog Name	:	"+md.getCatalogName(1));
				System.out.println("Table Name	:	"+md.getTableName(1));
				System.out.println("--------------------------------------------------------");
				for( int count = 1; count <= md.getColumnCount(); ++ count )
				{
						String columnName = md.getColumnName(count);
						String columnType = md.getColumnTypeName(count);
						int columnSize = md.getPrecision(count);
						System.out.printf("%-30s%s( %d )\n",columnName, columnType,columnSize);
				}
				System.out.println("--------------------------------------------------------");
			}
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}
}
