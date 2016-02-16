
import java.util.*;

class MyObj
 {
    String Name;
	int roll_num; 
 }

 public class HashTable1
 {
    public static void main(String args[])
	 {
	   
	   Hashtable<Integer,MyObj> ht = new Hashtable<Integer,MyObj>();
	   
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
	       ht.put(i,ob);
	      }
		
		Enumeration<Integer> enum1 = ht.keys();
	    
        while(enum1.hasMoreElements())
          {
		     int key = enum1.nextElement();
			 MyObj ob = ht.get(key);
			 System.out.println("key :"+key+" Name :"+ob.Name+" Roll Number : "+ob.roll_num);
		  }	    
		  
		  // update a value
		  MyObj ob = new MyObj();
		   ob.Name="Hello";
		   ob.roll_num =44;
          ht.put(1,ob);
   
            System.out.println("New Value for 1"+ht.get(1).Name);
          	 
	 }
 }