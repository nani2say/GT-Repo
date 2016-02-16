// Question:  xxyyyz  => x2y3z1

import java.util.*;
import java.util.Map.*;

public class Compress
{

  public static void main(String args[])
   {
     String str = "xxyyyz";
     LinkedHashMap<Character,Integer> hmap = new LinkedHashMap<Character,Integer>();
     
	 for(int i=0; i<str.length(); i++)
	  {
	   char ch = str.charAt(i);
	   Integer count = hmap.get(ch);
	   
	   if(count==null)
	   hmap.put(ch,1);
	   else
	   hmap.put(ch,count+1); 
	  
	  }
	  
	  Set<Entry<Character,Integer>> al = hmap.entrySet();
 
      for(Entry e: al)
      {
		System.out.print(e.getKey()+""+e.getValue());
	  }	 
	
	}
		
}