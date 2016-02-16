import java.util.*;

public class TestStr
{ 

 public static  void permute(String offset, String str)
 {
    if(str.length()==0)
	  System.out.println(offset);	
	
    else
      {
	    for(int i=0; i< str.length(); i++)
		{
		  permute(offset+str.charAt(i), str.substring(0,i)+str.substring(i+1,str.length()));		  
		}		
	  }	 
 }

 
 public static ArrayList<String> getPerms(String s)
 {
    ArrayList<String> permutations = new ArrayList<String>();
 
   if (s == null) { // error case
             return null;
          } 
 else if (s.length() == 0) 
 { // base case
 permutations.add("");
 return permutations;
 }

 char first = s.charAt(0); // get the first character
 String remainder = s.substring(1); // remove the first character
 ArrayList<String> words = getPerms(remainder);
 for (String word : words) {
 for (int j = 0; j <= word.length(); j++) {
 permutations.add(insertCharAt(word, first, j));
 }
 }
 return permutations;
 }
 
 public static String insertCharAt(String word, char c, int i) {
 String start = word.substring(0, i);
 String end = word.substring(i);
 return start + c + end;
 }
 public static void main(String args[] )
  {
    //TestStr.permute("","abcd"); 
    ArrayList<String> al = TestStr.getPerms("abcd");
  
    for(String st: al)
	 {
	  System.out.println(st);
	 }
  }
}