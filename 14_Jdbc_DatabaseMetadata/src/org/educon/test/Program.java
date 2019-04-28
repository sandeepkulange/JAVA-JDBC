package org.educon.test;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import com.educon.utils.DBUtils;

public class Program
{
	public static void printJdbcInfo( DatabaseMetaData dm )throws Exception
	{
		System.out.println("JDBC Version	:	"+dm.getJDBCMajorVersion()+"."+dm.getJDBCMinorVersion());
	}
	public static void printProductInfo( DatabaseMetaData dm )throws Exception
	{
		System.out.println("Product Name	:	"+dm.getDatabaseProductName());
		System.out.println("Product Version	:	"+dm.getDatabaseProductVersion());
	}
	public static void printDriverInfo( DatabaseMetaData dm )throws Exception
	{
		System.out.println("Driver Name	:	"+dm.getDriverName());
		System.out.println("Driver Version	:	"+dm.getDriverVersion());
	}
	public static void printCatlogInfo( DatabaseMetaData dm )throws Exception
	{
		System.out.println("User Name	:	"+dm.getUserName());
		System.out.println("URL		:	"+dm.getURL());
		try( ResultSet rs = dm.getCatalogs())
		{
			System.out.println("Catlog Name	:	");
			while( rs.next())
			{
				String catlogName = rs.getString("TABLE_CAT");
				System.out.println("		=>	"+catlogName);
			}
		}
	}
	public static void printTableInfo( DatabaseMetaData dm )throws Exception
	{
		System.out.println("Supported Cols	:	"+dm.getMaxColumnsInTable());
		System.out.println("Supported Rows	:	"+dm.getMaxRowSize());
		System.out.println("Tables	:	");
		try( ResultSet rs = dm.getTables(null, null, "%", null))
		{
			while( rs.next())
			{
				if( rs.getString("TABLE_TYPE").equalsIgnoreCase("TABLE"))
					System.out.println("		:	"+rs.getString("TABLE_NAME"));
			}
		}
		
	}
	public static void printTypeInfo( DatabaseMetaData dm )throws Exception
	{
		System.out.println("Supported Types	:	");
		try( ResultSet rs = dm.getTypeInfo())
		{
			int count = 0;
			while( rs.next())
			{
				++ count;
				System.out.printf("%-20s",rs.getString("TYPE_NAME"));
				if( count == 4 )
				{
					System.out.println();
					count = 0;
				}
			}
			System.out.println();
		}
		
	}
	public static void printTableTypes( DatabaseMetaData dm )throws Exception
	{
		System.out.println("Table Types	:	");
		try( ResultSet rs = dm.getTableTypes())
		{
			while( rs.next())
				System.out.println("		:	"+rs.getString("TABLE_TYPE"));
		}
	}
	private static String getStatus( boolean status )
	{
		return status ? "Supported" : "Not Supported";
	}
	public static void testDatabaseResultSetSupport( DatabaseMetaData dm)throws Exception
	{
		System.out.println("ResultSet Type	:	");
		System.out.println("	TYPE_FORWARD_ONLY	:	"+ getStatus(dm.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY)));
		System.out.println("	TYPE_SCROLL_INSENSITIVE	:	"+ getStatus(dm.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE)));
		System.out.println("	TYPE_SCROLL_SENSITIVE	:	"+ getStatus(dm.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE)));
		
		System.out.println("ResultSet concurrency	:	");
		System.out.println("	TYPE_FORWARD_ONLY & CONCUR_READ_ONLY		:	"+ getStatus(dm.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)));
		System.out.println("	TYPE_SCROLL_INSENSITIVE & CONCUR_READ_ONLY	:	"+ getStatus(dm.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)));
		System.out.println("	TYPE_SCROLL_SENSITIVE & CONCUR_READ_ONLY	:	"+ getStatus(dm.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)));
		
		System.out.println("	TYPE_FORWARD_ONLY & CONCUR_UPDATABLE		:	"+ getStatus(dm.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)));
		System.out.println("	TYPE_SCROLL_INSENSITIVE & CONCUR_UPDATABLE	:	"+ getStatus(dm.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)));
		System.out.println("	TYPE_SCROLL_SENSITIVE & CONCUR_UPDATABLE	:	"+ getStatus(dm.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)));
		
		System.out.println("ResultSet Holdability	:	");
		System.out.println("	HOLD_CURSORS_OVER_COMMIT	:	"+getStatus( dm.supportsResultSetHoldability(ResultSet.HOLD_CURSORS_OVER_COMMIT)));
		System.out.println("	CLOSE_CURSORS_AT_COMMIT		:	"+getStatus( dm.supportsResultSetHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT)));
	}
	public static void main(String[] args) 
	{	
		try( Connection connection = DBUtils.getConnection(); )
		{
			DatabaseMetaData dm = connection.getMetaData();
			
			Program.printJdbcInfo(dm);
			System.out.println("------------------------------------------------------------------------");
			Program.printProductInfo(dm);
			System.out.println("------------------------------------------------------------------------");
			Program.printDriverInfo(dm);
			System.out.println("------------------------------------------------------------------------");
			Program.printCatlogInfo(dm);
			System.out.println("------------------------------------------------------------------------");
			Program.printTableInfo(dm);
			System.out.println("------------------------------------------------------------------------");
			Program.printTypeInfo(dm);
			System.out.println("------------------------------------------------------------------------");
			Program.printTableTypes(dm);
			System.out.println("------------------------------------------------------------------------");
			Program.testDatabaseResultSetSupport(dm);
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}
}