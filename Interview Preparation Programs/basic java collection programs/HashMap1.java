
import java.util.*;

class MyObj
 {
    String Name;
	int roll_num; 
 }

 public class HashMap1
 {
    public static void main(String args[])
	 {
	   
	   Map<Integer, MyObj> hm = new HashMap<Integer,MyObj>();
	   
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
	       hm.put(i,ob);
	      }
		
		Iterator<Integer> iter = hm.keySet().iterator();
	    
        while(iter.hasNext())
          {
		     int key = iter.next();
			 MyObj ob = hm.get(key);
			 System.out.println("key :"+key+" Name :"+ob.Name+" Roll Number : "+ob.roll_num);
		  }	    
	 
	 }
 }