
import java.util.*;


 public class Stack1
 {
    public static void main(String args[])
	 {
	  
	  Stack<String> st = new Stack<String>();
	   
	   st.push("Hi");
	   st.push("Lastbutone");st.push("Buttwo");st.push("Recent2");st.push("MostRecent");
       
             System.out.println(st);   
	   
	    System.out.println(st.pop());	  
	    System.out.println(st.pop());
	    System.out.println(st.pop());
		System.out.println("---------");
		
		double ft = 356.33567;
		System.out.println(Math.ceil(ft/0.01)*0.01);
	  
 
	 }
	 
 }