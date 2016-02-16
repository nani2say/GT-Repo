import java.util.*;

public class Combination
{
 
 static String input = "abcd";
 static StringBuilder sb = new StringBuilder();
 
 static ArrayList<Integer> inlist = new ArrayList<Integer>();
 static ArrayList<Integer> outlist = new ArrayList<Integer>();
 
 static boolean [] used = new boolean[input.length()]; 
 
 public static void combine(int start)
 {
  for( int i = start; i< input.length(); i++)
     {
       sb.append(input.charAt(i));
       System.out.println(sb);	   
	   
	   if(i < input.length()) combine(i+1);
	   
	   sb.setLength(sb.length()-1);
	 }
 } 
 
 //generating the subsets using the same logic of combinations in programming interviews exposed
 public static void subset(int start)
  {
    for( int i = start; i< inlist.size(); i++)
     {
       outlist.add(inlist.get(i));
      
       //---printing--	  
	   System.out.print("{");
       for(int temp: outlist) System.out.print(temp+",");	   
	   System.out.print("}");
	   //------
	   
	   if(i < inlist.size()) subset(i+1);
	   
	   outlist.remove(outlist.size()-1);
       //System.out.println("list size "+outlist.size());	 
	 }   
  }

  
public static void permute()
 {
   if(sb.length() == input.length())
    {
 	 System.out.println(sb);
     return ;
    }
  
   for(int i=0; i<input.length(); i++)
    {
	   if(used[i] == true) continue;
	   
	   sb.append(input.charAt(i));
	   
	   used[i] = true;
	   permute();
	   used[i] = false;
	   
	   sb.setLength(sb.length()-1);
	   
	}   
 }

 public static void permute2(String offset, String input)
 {
   if(input.length() == 0)
     { 
	   System.out.println(offset);
       return; 	  
   	  }
 
   for(int i=0; i<input.length(); i++)
     {
	  String s1 = input.charAt(i)+offset;
	  String s2 = input.substring(0,i)+input.substring(i+1); 
	  permute2(s1,s2);
	 }   
	  
 }
 
 public static void main(String args[])
 {
   //combine(0);  
  // inlist.add(1); inlist.add(2); inlist.add(3); inlist.add(4);
   //subset(0);
 
   // permute();
 
    permute2("", "abcd");
 }
}