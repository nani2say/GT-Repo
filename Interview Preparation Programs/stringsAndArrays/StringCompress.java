//7:34
import java.util.*;

public class StringCompress
{

  public static String stringCompress(String str)
   {
     char [] str1 = str.toCharArray();
     
     HashMap<Character,Integer> hm = new LinkedHashMap<Character,Integer>();
    
      for(char c: str1)
      {
	    if(hm.containsKey(c)) hm.put(c,hm.get(c)+1);
		else  hm.put(c,1);
	  }	  
     
	 StringBuffer sb = new StringBuffer();
	 
	  Set<Character> st = hm.keySet();
	  
      for(char c: st)
	  {
	    sb.append(c); sb.append(hm.get(c));
	  }
	 
	 return sb.toString();
	 
   }

  public static void main(String args[])
   {  
    System.out.println(StringCompress.stringCompress("xxxyyz")); 
   }

}