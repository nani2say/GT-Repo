import java.util.*;

public class PermutationExposedBook
{
 static String in = "abcd";
 static StringBuilder sb = new StringBuilder(); 
 static boolean used[] = new boolean[in.length()]; 
  
 public static void permute()
 {
   if(sb.length()== in.length())
   {
    System.out.println(sb);
    return;
   }
   else
   {
     for(int i=0;i<in.length(); i++)
	  { 
	    if(used[i]==true)  continue;
		
		sb.append(in.charAt(i));
		used[i] = true;
		permute();
		used[i]=false;
		
		sb.setLength(sb.length()-1);
	   
	  }   
   } 
 }

  public static ArrayList<String> permuteStore()
 {
  ArrayList<String> alist = new ArrayList<String>();
   ArrayList<String> temp;
 
   if(sb.length()== in.length())
   {
    //System.out.println(sb);
	alist.add(sb.toString());
    return alist;
   }
   else
   {
     for(int i=0;i<in.length(); i++)
	  { 
	    if(used[i]==true)  continue;
		
		sb.append(in.charAt(i));
	    	used[i] = true;
	       temp	= new ArrayList<String>();
		   temp = permuteStore();
		   alist.addAll(temp);
	    	used[i]=false;
		    
		sb.setLength(sb.length()-1);
	   
	  }
     return alist;	  
   } 
 }

 
 public static void main(String args[])
 {
  //PermutationExposedBook.permute();
  
    ArrayList<String> slist = PermutationExposedBook.permuteStore();
    
	for(String s:slist)
	 {
	   System.out.println(s);  
	 }	
 }
}