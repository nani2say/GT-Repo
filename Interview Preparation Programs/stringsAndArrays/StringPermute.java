import java.util.*;

public class StringPermute
{
  
  public static void stringpermute(String constnt, String perm)
    {
	  int len = perm.length();
	  
	 if(len == 0)
	      System.out.println(constnt);
	 else
      {
	    for(int i=0; i< len ; i++)
		  {		  
  		    String arg1 = constnt + perm.charAt(i);
		    String arg2 = perm.substring(0,i)+perm.substring(i+1,len);  //substring(int beginIndex, int endIndex)
		    stringpermute(arg1, arg2);                           //**** beginindex is inclusive and endindex is exclusive		  		  		  
		  }
	  }	 
 }  
	
	
 //This method will return a array list of string combinations	
 public static ArrayList<String> stringpermute2(String constnt, String perm)
    {
	 ArrayList<String> temp = new ArrayList<String>();	 
	 ArrayList<String> temp2 = new ArrayList<String>();
	 
	  int len = perm.length();
	  
	 if(len == 0)
	      { //System.out.println(constnt);
		    temp.add(constnt);
			return temp;
		  }
	 else
      {
	    for(int i=0; i< len ; i++)
		  {
		   String arg1 = constnt + perm.charAt(i);
		   String arg2= perm.substring(0,i)+perm.substring(i+1,len);  //substring(int beginIndex, int endIndex)
		   temp2 = stringpermute2(arg1,arg2 );                           //**** beginindex is inclusive and endindex is exclusive
		  
		   for(String st:temp2)
		      temp.add(st);
			  
		  }
          return temp;	 
	 }	 
	}  

	
  public static void main(String args[])
     {
	    //StringPermute.stringpermute("","abc"); //normal recursive
		ArrayList<String> result = StringPermute.stringpermute2("","abcd"); //recursive with arraylist
		
		for(String st: result)
		  System.out.println(st);
	 }
}