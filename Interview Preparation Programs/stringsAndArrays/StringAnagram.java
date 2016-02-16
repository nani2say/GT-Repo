//program to sort an array of strings. If they are anagrams they should be togather

import java.util.*;

class CustomComp implements Comparator<String>
 {
   public String mySort(String s)
      {
	   char [] chars = s.toCharArray();
	   
	   Arrays.sort(chars);
	   
	   return new String(chars);
	   
	  }
	  
   public int compare(String s1, String s2)
   {
      s1 = mySort(s1);
	  s2 = mySort(s2);	  
	  
	  return s1.compareTo(s2);
   }   
 }
 
 public class StringAnagram
  {
  
   public static void main(String args[])
    {
	  
    String sarray [] =  new String[6];
	
	sarray[0]= "Hello"; sarray[1]= "eHllo"; 
    sarray[2]= "Bell"; sarray[3]= "eBll";
	sarray[4]= "Coal"; sarray[5]= "Paste";
	
	Arrays.sort(sarray, new CustomComp());
	
	for(String s: sarray)
	   System.out.println(s);
	
	} 
  } 