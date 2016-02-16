

//Compress using HashMap

import java.util.*;

public class Compress
{
 public static void main(String args[])
   {
     String s ="aabbcccd";
	 
	 Map<Character, Integer> hm = new LinkedHashMap<Character, Integer>();
    
	 char carr[] = s.toCharArray();
	 
     for(char ch:carr)
	  {
	    if(hm.containsKey(ch))
		   {
		     hm.put(ch,hm.get(ch)+1);
		   
		   }
		  else  hm.put(ch,1);
	  
	  }
	  
	  Set<Character> set = hm.keySet();
	  
	  for(char ch:set)
	    {
		 System.out.print(ch+""+hm.get(ch));
		}
	 
   }
}
