package database;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.rowset.CachedRowSet;
import javax.swing.JOptionPane;

import com.sun.rowset.CachedRowSetImpl;

public class DBConnectionFactory 
{
  Connection dbCon = null; 
  String dbURL;
  String username;
  String password;
	  
  public DBConnectionFactory (String dbURL, String username, String password)
  {
      try 
       {
          //getting database connection to MySQL server
    	  this.dbURL = dbURL;
    	  this.username=username;
    	  this.password =password;
          dbCon = DriverManager.getConnection(dbURL, username, password);
         
        }
        catch (SQLException ex)
        {
         System.out.println(ex);
        }
  
  }
  
  public ResultSet getResults(String query)
   {
	  ResultSet rs =null;
	  try
	  {
	 Statement stmt = dbCon.prepareStatement(query);
      
      //Resultset returned by query
      rs = stmt.executeQuery(query);  
      
	  return rs;
	  }
	  
	  catch(SQLException e)
	  {
		  System.out.println("SQL Exception:"+e);
	  }
	  
	  return rs; //even in case of exception we should return something
   }
  
  public int deleteItem(int itemId)
  {
	 int res =-1;
	   
	  try
	    {
	     CallableStatement cs =  dbCon.prepareCall("{call deleteItem(?,?)}");	
		 cs.setInt(2, itemId); 
		 cs.registerOutParameter(1, java.sql.Types.INTEGER);
		 
		 cs.executeUpdate();
		 
		 res = cs.getInt(1);
		 
	    }
	    catch(SQLException e)
	    {
	    	e.printStackTrace();		    	
	    }
		System.out.println("Record deleted from the items table!");
	  
	    return res;   
  }
  
  
  public int saveItem(String itemname,int availqty,float unitprice,char classf,String um,int rlevel,int minqty, int maxqty) 
  {
	  int itemid =-1;
	    try
	    {
	     CallableStatement cs =  dbCon.prepareCall("{call insertItem(?,?,?,?,?,?,?,?,?)}");	
		 cs.setString(2, itemname); cs.setInt(3, availqty);
		 cs.setFloat(4,unitprice); cs.setString(5,  Character.toString(classf));
		 cs.setString(6, um); cs.setInt(7, rlevel);cs.setInt(8, minqty);cs.setInt(9, maxqty);
		 
		 cs.registerOutParameter(1, java.sql.Types.INTEGER);
		 
		 cs.executeUpdate();
		 
		 itemid = cs.getInt(1);
		 
	    }
	    catch(SQLException e)
	    {
	    	e.printStackTrace();		    	
	    }
		System.out.println("Record is inserted into items table!");
	  
	    return itemid;  
   }
  
  public int saveSupplier(String supname,String phone, String city, int itemid) 
  {
	  int supid =-1;
	    try
	    {
	     CallableStatement cs =  dbCon.prepareCall("{call insertSupplier(?,?,?,?,?)}");	
		 cs.setString(2, supname); cs.setString(3, phone);
		 cs.setString(4,city);
		 cs.setInt(5,itemid);
		 
		 cs.registerOutParameter(1, java.sql.Types.INTEGER);
		 
		 cs.executeUpdate();
		 
		 supid = cs.getInt(1);
		 
	    }
	    catch(SQLException e)
	    {
	    	e.printStackTrace();		    	
	    }
		System.out.println("Record is inserted into Suppliers table!");
	  
	    return supid;  
   }
  
  public CachedRowSet getContentsOfOrdersTable (int offset) throws SQLException
  {
	  CachedRowSet crs = null;
	  
	  try {			      
	      crs = new CachedRowSetImpl();
	      crs.setType(ResultSet.TYPE_SCROLL_INSENSITIVE);
	      crs.setConcurrency(ResultSet.CONCUR_UPDATABLE);
	      crs.setUsername(username);
	      crs.setPassword(password);
	      
	      // In MySQL, to disable auto-commit, set the property relaxAutoCommit to
	      // true in the connection URL.  ************
	      crs.setUrl(dbURL+ "?relaxAutoCommit=true"); 

	      // Regardless of the query, fetch the contents of COFFEES
	      if(offset== -1)
	      crs.setCommand("select orderid, itemid ,sup_id from orders");
	      else
	      crs.setCommand("select orderID, itemid ,sup_id from orders where itemid="+offset);
	      
	      crs.execute();

	      //Debug Stuff
	   //   System.out.println("select itemID, itemname ,unitprice from items where itemname LIKE '"+offset+"'");
	    } 
	    catch (SQLException e)
	    {
	      System.out.println(e);
	    }
	    
	  
	    return crs;
  }
               
	public CachedRowSet getContentsOfLogTable(int monthNum, String yearNum) throws SQLException
	{
		    
		    
			    CachedRowSet crs = null;
			    try {			      
			      crs = new CachedRowSetImpl();
			      crs.setType(ResultSet.TYPE_SCROLL_INSENSITIVE);
			      crs.setConcurrency(ResultSet.CONCUR_UPDATABLE);
			      crs.setUsername(username);
			      crs.setPassword(password);
			      
			      // In MySQL, to disable auto-commit, set the property relaxAutoCommit to
			      // true in the connection URL.  ************
			      crs.setUrl(dbURL+ "?relaxAutoCommit=true"); 

			      // Regardless of the query, fetch the contents of COFFEES
			      crs.setCommand("select itemID, itemName, qty, date from items_log_current where action ='DEL' AND MONTH(date) = '"+monthNum+"'"+ " AND YEAR(date) = "+yearNum);
			      crs.execute();

			      //Debug Stuff
			   //   System.out.println("select itemID, itemname ,unitprice from items where itemname LIKE '"+offset+"'");
			    } 
			    catch (SQLException e)
			    {
			      System.out.println(e);
			    }
			    
			    return crs;
		}
	
	public CachedRowSet getContentsOfItemsTable(String offset) throws SQLException
	{
		    offset = offset+"%"; //to deal with P% kind of sstrings
		    
			    CachedRowSet crs = null;
			    try {			      
			      crs = new CachedRowSetImpl();
			      crs.setType(ResultSet.TYPE_SCROLL_INSENSITIVE);
			      crs.setConcurrency(ResultSet.CONCUR_UPDATABLE);
			      crs.setUsername(username);
			      crs.setPassword(password);
			      
			      // In MySQL, to disable auto-commit, set the property relaxAutoCommit to
			      // true in the connection URL.  ************
			      crs.setUrl(dbURL+ "?relaxAutoCommit=true"); 

			      // Regardless of the query, fetch the contents of COFFEES
			      crs.setCommand("select itemID, itemname ,unitprice from items where itemname LIKE '"+offset+"'");
			      crs.execute();

			      //Debug Stuff
			   //   System.out.println("select itemID, itemname ,unitprice from items where itemname LIKE '"+offset+"'");
			    } 
			    catch (SQLException e)
			    {
			      System.out.println(e);
			    }
			    
			    return crs;
		}
	public CachedRowSet getSuppliers(int offset) throws SQLException
	{		
		CachedRowSet crs = null;

	    try {			      
	      crs = new CachedRowSetImpl();
	      crs.setType(ResultSet.TYPE_SCROLL_INSENSITIVE);
	      crs.setConcurrency(ResultSet.CONCUR_UPDATABLE);
	      crs.setUsername(username);
	      crs.setPassword(password);
	      
	      // In MySQL, to disable auto-commit, set the property relaxAutoCommit to
	      // true in the connection URL.  ************
	      crs.setUrl(dbURL+ "?relaxAutoCommit=true"); 

	      // Regardless of the query, fetch the contents of COFFEES
	      if(offset==-1)
	      crs.setCommand("select * from suppliers");
	      else
	    	  crs.setCommand("select * from suppliers where sup_id="+offset);  
	      crs.execute();

	    } 
	    catch (SQLException e)
	    {
	      System.out.println(e);
	    }
	    
	    return crs;
	}
	
	public boolean authenticate(String usr, String psw) throws SQLException
	{		
		
	    
	    CachedRowSet crs = null;
	    try {			      
	      crs = new CachedRowSetImpl();
	      crs.setType(ResultSet.TYPE_SCROLL_INSENSITIVE);
	      crs.setConcurrency(ResultSet.CONCUR_UPDATABLE);
	      crs.setUsername(username);
	      crs.setPassword(password);
	      
	      // In MySQL, to disable auto-commit, set the property relaxAutoCommit to
	      // true in the connection URL.  ************
	      crs.setUrl(dbURL+ "?relaxAutoCommit=true"); 

	      // Regardless of the query, fetch the contents of COFFEES
	      crs.setCommand("select * from users where user = '"+usr+"' AND pwd=MD5("+psw+")");
	      crs.execute();

	      //Debug Stuff
	   //   System.out.println("select itemID, itemname ,unitprice from items where itemname LIKE '"+offset+"'");
	    } 
	    catch (SQLException e)
	    {
	      System.out.println(e);
	    }
	    
	    if(crs.size()==0)
	    	return false;
	    else
	    	return true;
		
  
	}
	
 public CachedRowSet getDeficitItems(int offset) throws SQLException
	{
	    CachedRowSet crs = null;

	    try {			      
	      crs = new CachedRowSetImpl();
	      crs.setType(ResultSet.TYPE_SCROLL_INSENSITIVE);
	      crs.setConcurrency(ResultSet.CONCUR_UPDATABLE);
	      crs.setUsername(username);
	      crs.setPassword(password);
	      
	      // In MySQL, to disable auto-commit, set the property relaxAutoCommit to
	      // true in the connection URL.  ************
	      crs.setUrl(dbURL+ "?relaxAutoCommit=true"); 

	      // Regardless of the query, fetch the contents of COFFEES
	      if(offset==-1)
	      crs.setCommand("select itemid , itemname, Available_qty, Min_Qty, Max_Qty from items where Reorder_Level=1");
	      else
	    	  crs.setCommand("select itemid , itemname, Available_qty, Min_Qty, Max_Qty from items where Reorder_Level=1 AND itemid="+offset);  
	      crs.execute();

	      //Debug Stuff
	   //   System.out.println("select itemID, itemname ,unitprice from items where itemname LIKE '"+offset+"'");
	    } 
	    catch (SQLException e)
	    {
	      System.out.println(e);
	    }
	    
	    return crs;
	}
 
 
 public void updateSupplier(int sid, String sname, String sphone, String scity)
 {
	 try
	    {
	     CallableStatement cs =  dbCon.prepareCall("{call updateSupplier(?,?,?,?)}");	
	     cs.setInt(1, sid);
	     cs.setString(2, sname); cs.setString(3,sphone);
	     cs.setString(4, scity);
		 cs.executeUpdate();
	    }
	    catch(SQLException e)
	    {
	    	e.printStackTrace();		    	
	    }
		System.out.println("Record is updated for supplier table!");	
	 
 }
 
 public void updateItem(int itemId , String itemname,int availqty,float unitprice,char classf,String um,
		   int rlevel,int minqty, int maxqty)
  {
	 try
	    {
	     CallableStatement cs =  dbCon.prepareCall("{call updateItem(?,?,?,?,?,?,?,?,?)}");	
	     cs.setInt(1, itemId);
	     cs.setString(2, itemname); cs.setInt(3, availqty);
		 cs.setFloat(4,unitprice); cs.setString(5,  Character.toString(classf));
		 cs.setString(6, um); cs.setInt(7, rlevel);cs.setInt(8, minqty);cs.setInt(9, maxqty);
		 cs.executeUpdate();
	    }
	    catch(SQLException e)
	    {
	    	e.printStackTrace();		    	
	    }
		System.out.println("Record is inserted into items table!");	
		
  }
 
 public void storeImage(int itemid ,String fileName) 
  {
	 try{
	    PreparedStatement ps;
	    ps= dbCon.prepareStatement("insert into images values(?,?)");
	    FileInputStream fileInputStream=new FileInputStream(fileName);//filanme has the filepath
	    byte b[]=new byte[fileInputStream.available()];
	    fileInputStream.read(b);
	    fileInputStream.close();
	    ps.setObject(1, itemid);
	    ps.setBytes(2, b);
	    int val=ps.executeUpdate();
	    if(val>=1) System.out.println("Image succesfully inserted!!");
	  }
	 catch(Exception e)
	   {
		 System.out.println(" Image insert Failed!! "+e);
	   }
  }
 
 public ResultSet retriveImage(int itemId)
 {
	ResultSet rs = null; 
    PreparedStatement ps;
   try
   {

     if(itemId>0)
      {
          ps=dbCon.prepareStatement("select * from images where itemid=?");
          ps.setObject(1, itemId);
          rs=ps.executeQuery(); 

      }
     else
     {
    	 JOptionPane.showMessageDialog(null,"ID is missing in the retrieveImage function." );
     }

   	}
   catch(Exception e)
   	{
	   e.printStackTrace();
   	}
   
   return rs;
  }
	
 public ArrayList<String> fillItemSuggestions()
 { 
	 ArrayList<String> ar = new  ArrayList<String>();
	 
	 try{
	 ResultSet rs =getResults("Select * from items");
		 
	 while (rs.next())
	  {
		    ar.add(rs.getString("itemname"));
	  }	    
	 }
	 catch(Exception e){}
	 
	// for(String s:ar)
	//	 System.out.println("Item: "+s);
	 
	 return ar;
	 
 }
 
 public HashMap<String,Integer> fillSupplier(int itemid)
 {
	 HashMap<String,Integer> hm = new  HashMap<String,Integer> ();
	 
	 try
	 {
		 ResultSet rs =getResults("select sup_name, sup_id from suppliers WHERE sup_id IN( Select sup_id from item_sup_rel where itemid ="+itemid+")"); 
	   
		 while(rs.next())
		 {
			 hm.put(rs.getString("sup_name"), rs.getInt("sup_id"));
		 }
	  
	 }
	 catch(Exception e){}
    
	 return hm;
 }
 
 public int placeOrder(int itemid, int supid, int qty)
  {
	 int orderid =-1;
	 try
	    {
	     CallableStatement cs =  dbCon.prepareCall("{call placeOrder(?,?,?,?)}");	
	     cs.setInt(2, itemid);
	     cs.setInt(3, supid);
	     cs.setInt(4, qty);
         cs.registerOutParameter(1, java.sql.Types.INTEGER);
		 
		 cs.executeUpdate();
		 
		 orderid = cs.getInt(1);
	    }
	    catch(SQLException e)
	    {
	    	e.printStackTrace();		    	
	    }
		System.out.println("Record is inserted into Orders table!");	
	 
		return orderid;
  }
 
 public int updateOrders(int orderid, int qty , char status)
 {
	 int ret =-1;
	 
	 try
	    {
	     CallableStatement cs =  dbCon.prepareCall("{call updateOrders(?,?,?)}");	
	     cs.setInt(1, orderid);
	     cs.setInt(2, qty); cs.setString(3,  Character.toString(status));		 
		 ret = cs.executeUpdate();
	    }
	    catch(SQLException e)
	    {
	    	e.printStackTrace();		    	
	        
	    }
	  
	 if(ret!=-1)
		System.out.println("Record is updated for  Orders table!");		 
	 
	 return ret;
	 
 }
 
public void close()
  {
	try {
		  dbCon.close();
		 } // Try to close the connection
	catch (Exception e) {}      // Do nothing on error. At least we tried.
	dbCon = null; 
  }

  /** Automatically close the connection when we're garbage collected */
  protected void finalize()
  {	
	  close();	  
  }
}
