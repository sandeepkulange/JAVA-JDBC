package org.educon.test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import com.educon.utils.DBUtils;

public class Program
{
	public static void main(String[] args) 
	{
		String sql= "{call sp_fund_transfer(?,?,?,?,?)}";
		
		try( Connection connection = DBUtils.getConnection();
			 CallableStatement statement = connection.prepareCall(sql);)
		{
			statement.setInt(1, 1001);
			statement.setInt(2, 1002);
			statement.setFloat(3, 10000);
			
			statement.registerOutParameter(4, Types.FLOAT);
			statement.registerOutParameter(5, Types.FLOAT);
			
			boolean status = statement.execute();
			System.out.println(status);
			
			System.out.println("Source Balance	:	"+statement.getFloat(4));
			System.out.println("Destination Balance	:	"+statement.getFloat(5));
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}
}
