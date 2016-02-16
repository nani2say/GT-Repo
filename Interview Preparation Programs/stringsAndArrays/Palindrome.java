// Imagine we have a large string like this "ABCBAHELLOHOWRACECARAREYOUIAMAIDOINGGOOD" which 
// contains multiple palindromes within it, like ABCBA, RACECAR, ARA, IAMAI etc... Now write
 // a method which will accept this large string and return the largest palindrome from this string. 
 // If there are two palindromes which are of same size, it would be sufficient to just return any one of them.


import java.util.*;

class MyComp implements Comparator<String>
  {
    public int compare(String s1, String s2)
	  {
	     if(s1.length() > s2.length()) return 1;
		 else if(s1.length() < s2.length()) return -1;
	     else return 0;
	  }
  }

public class Palindrome
{
  public static boolean checkPalin(String str)
   {
      char [] chstr = str.toCharArray();
	  int len = chstr.length;	
		
		for(int i =0; i<len; i++) //len/2??
        {
		  if(chstr[i]!=chstr[len-i-1]) return false;
		}		
		
	  return true;	
   }
  
  public static void main(String args[])
   {
     ArrayList<String> al = new ArrayList<String> ();
	 
	 String s = "ABCBAHELLOHOWRACECARAREYOUIAMAIDOINGGOOD";
     int len = s.length();
	 
	 for(int i=0; i< len-1; i++)
	  {
	    for(int j =i+1; j<len ;j++)
		 {
		   String sub = s.substring(i,j+1);
 		   boolean test = checkPalin(sub); //since in substring second argument is exclusive
	       
		   if(test == true)  al.add(sub);
		 }
	  }
	  
	  Collections.sort(al,new MyComp());
	 
	  for(String st: al)
       System.out.println(st);	 
      	 
   }
}