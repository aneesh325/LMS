package com.gcit.lms.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Author;
import com.gcit.lms.services.ConnectionUtil;

public abstract class BaseDAO implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2640579006910564407L;
	public static Connection conn = null;	 
	 

	public BaseDAO(Connection conn) 
	{
		super();
		this.conn = conn;
		//Connection connection = connUtil.openConnection();
	}

	public void save(String query, Object vals[]) throws SQLException 
	{
	PreparedStatement pstmt = null;		
      try {
	
	pstmt = conn.prepareStatement(query);
		if(vals != null){	
		int count = 1;
	for(Object O : vals)
    {
    	pstmt.setObject(count, vals);
        count++;   
    }
	pstmt.executeUpdate();
      } }
      catch (SQLException e) 
      {
		// TODO Auto-generated catch block
		e.printStackTrace();

	  }
	}                    //<T>List<T> can be type casted to any of the List. We are saying not to cast to anything 
	//        because I will cast it to one of the List types
	
	public Integer saveWithID(String query, Object vals[]) throws SQLException 
	{
	PreparedStatement pstmt = null;		
      try {
	    pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		if(vals != null){	
		int count = 1;
	for(Object O : vals)
    {
    	pstmt.setObject(count, vals);
        count++;   
    }
	ResultSet rs = pstmt.executeQuery();
	conn.commit();
	if(rs.next())
	{
		return rs.getInt(1);
	}
	else
		return -1;
	
      } }
      catch (SQLException e) 
      {
		// TODO Auto-generated catch block
		e.printStackTrace();
		if(conn != null)
		conn.rollback();	
	  }
    finally
    {
    	if(conn != null)
    	conn.close();	
    	if(pstmt != null)
    	pstmt.close();	
    }
      return null;
	} 
	
	public  <T>List<T> readAll(String query, Object vals[]) throws SQLException  // has a return type  of a 
	                                                                 //generic list <T>List<T> and is accepting parameters
	                                                                 //(String query, Object vals[]) 
	{ 			PreparedStatement pstmt = null;


		try {
		pstmt = conn.prepareStatement(query);
		
//		int count = 1;
//		for(Object O : vals)
//		{
//			pstmt.setObject(count, vals);
//			count++;
//		}
		ResultSet rs = pstmt.executeQuery();
		List<T> temp = (List<T>) extractdata(rs);
		return temp;
		} 
		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		finally
		{

		if(pstmt != null)
		pstmt.close();	
		if(conn != null)
		conn.close();
		}
		return null;
		}

	public abstract <T>List<T> extractdata(ResultSet rs);

	public <T>List<T> readAllFirstLevel(String query, Object[] vals) throws SQLException{
		PreparedStatement pstmt = null;
		try {
			
			pstmt = conn.prepareStatement(query);
			if(vals!=null){
				int count=1;
				for(Object o: vals){
					pstmt.setObject(count, o);
					count++;
				}
			}
			ResultSet rs = pstmt.executeQuery();
			return (List<T>) extractDataFirstLevel(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(pstmt!=null)
				pstmt.close();
			if(conn!=null)
				conn.close();
		}
		return null;
	}

	public abstract <T>List<T> extractDataFirstLevel(ResultSet rs);
}

	
	

