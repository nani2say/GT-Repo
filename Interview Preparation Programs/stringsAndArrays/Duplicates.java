import java.util.*;

public class Duplicates
 {
 
  public static String dups( String str)
   {
    char [] strc = str.toCharArray();
	LinkedList<Character> ls  = new LinkedList<Character>();
    
   for(char c: strc)
    {
	  if(!ls.contains(c)) ls.add(c);	  
	}   
	
	StringBuffer sb = new StringBuffer();
	
	for(Character c: ls)
	{
	  sb.append(c);
	}
	
	return sb.toString();
   } 
   
   public static void main(String args[])
   {  
    System.out.println(Duplicates.dups("xxyxyz")); 
   }
 }