package uow.cmde.transim.util.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 
 * @author Vu The Tran
 * @since 20/12/2011
 */
public class MySqlHandler {

	 private String host;
	 private String username ;
     private String password;
     private String dbName;
     private PreparedStatement pstmt;
     private Connection conn;
     private ResultSet rs;
     
     
     public MySqlHandler()
     {
  
     }
     
	/**
	 * connect
	 * @return
	 * @throws Exception
	 */
	public Connection connect() throws Exception
	{

        String url = "jdbc:mysql://" + host + "/" + dbName;
        Class.forName ("com.mysql.jdbc.Driver").newInstance ();
        conn = DriverManager.getConnection (url, username, password);
          
        return conn;
    
	}
	
	
	
	/**
	 * executeQuery
	 * @return
	 * @throws Exception
	 */
	public ResultSet executeQuery() throws Exception
	{
		 rs = pstmt.executeQuery();
	    
		 return rs;
		
	}
	
	/**
	 * executeUpdate
	 * @return
	 * @throws Exception
	 */
	public boolean executeUpdate() throws Exception
	{
		 int update = pstmt.executeUpdate();
			 
		 return (update!=0);	
	}
	
	/**
	 * close
	 * @throws Exception
	 */
	public void close() throws Exception
	{
		if(rs!=null) rs.close();
		if(pstmt!=null) pstmt.close();
		if(conn!=null) conn.close();
	}
	
	/**
	 * setQuery
	 * @param query
	 * @throws Exception
	 */
	public void setQuery(String query) throws Exception
	{
		conn = connect();
		pstmt = conn.prepareStatement(query);
	}
	
	/**
	 * getPreparedStatement
	 * @return
	 */
	public PreparedStatement getPreparedStatement()
	{
		return pstmt;
	}
	
	public void setHost(String host)
	{
		this.host = host;
	}
	/**
	 * setUsername
	 * @param userName
	 */
	public void setUsername(String userName)
	{
		this.username = userName;
	}
	
	/**
	 * setPassword
	 * @param password
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	/**
	 * setDatabase
	 * @param dbName
	 */
	public void setDatabase(String dbName)
	{
		this.dbName = dbName;
	}
}
