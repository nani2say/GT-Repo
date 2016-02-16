
import java.util.*;

class MyObj
 {
    String Name;
	int roll_num; 
 }

 public class LinkedHashMap1
 {
    public static void main(String args[])
	 {
	   
	   Map<Integer,MyObj> linkhm = new LinkedHashMap<Integer,MyObj>();
	   
	   Scanner sc = new Scanner(System.in);
	  
	   for(int i=0;i<3;i++)
	    {
		   System.out.println(" Enter Name: ");
           String str= sc.next(); //us it inplace of nextLine - there will not be line skipping problems		 
	       System.out.println(" Enter Roll Num: ");
		   int roll = sc.nextInt();		
		   MyObj ob = new MyObj();
		   ob.Name=str;
		   ob.roll_num =roll;
	       linkhm.put(i,ob);
	      }
		
		Iterator<Integer> iter = linkhm.keySet().iterator();
	    
        while(iter.hasNext())
          {
		     int key = iter.next();
			 MyObj ob = linkhm.get(key);
			 System.out.println("key :"+key+" Name :"+ob.Name+" Roll Number : "+ob.roll_num);
		  }	    
	 
	 }
 }