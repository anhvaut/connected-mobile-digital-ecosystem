package uow.cmde.transim.util.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 
 * @author Vu The Tran
 * @since 20/12/2011
 */
public class MySqlConnection {

	 private String userName ;
     private String password;
     private String dbName;
     
     public MySqlConnection(){}
     
     public MySqlConnection(String userName, String password, String dbName)
     {
    	 this.userName = userName;
    	 this.password = password;
    	 this.dbName = dbName;
     }
     
	/***
	 * Connect to database
	 * @return
	 */
	public Connection connect()
	{
		Connection conn = null;

        try
        {
            String url = "jdbc:mysql://localhost/" + dbName;
            Class.forName ("com.mysql.jdbc.Driver").newInstance ();
            conn = DriverManager.getConnection (url, userName, password);
            //System.out.println ("Database connection established");
        }
        catch (Exception e)
        {
            System.err.println ("Cannot connect to database server");
        }
        
        return conn;
    
	}
	
	
	/**
	 * Query string
	 * @param query
	 */
	public ResultSet executeQuery(String query)
	{
		 Statement statement =null;
		 ResultSet rs = null;
		 Connection conn = null;
		 try
		 {
			 conn = connect();
			 statement = conn.createStatement(); 
			 rs=statement.executeQuery(query);
			 
			 while(rs.next())
			 {
				 System.out.println("....." +rs.getString(2));
			 }
			 
			 rs .close();
			 statement .close ();
			 conn.close ();
		 }
	     catch (Exception e)
	     {
	            e.printStackTrace();
	     }
	    
		 
		 return rs;
		
	}
	
	public boolean executeUpdate(String query)
	{
		 Statement statement =null;
		 ResultSet rs = null;
		 Connection conn = null;
		 try
		 {
			 conn = connect();
			 statement = conn.createStatement(); 
			 int updateQuery = statement.executeUpdate(query);
			 
			 statement .close ();
			 conn.close ();
			 
			 return (updateQuery!=0);
		 }
	     catch (Exception e)
	     {
	    	 e.printStackTrace();
	     }
		 
		 return false;
		
	}
	
	public void setUsername(String userName)
	{
		this.userName = userName;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public void setDBName()
	{
		this.dbName = dbName;
	}
}
