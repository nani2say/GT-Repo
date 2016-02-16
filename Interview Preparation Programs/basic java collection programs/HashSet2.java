import java.util.*;

class MyObj
 {
    String Name;
	int roll_num; 
 }

public class HashSet2
 {
  public static void main(String args[])
    {
	  HashSet<MyObj> hs = new HashSet<MyObj>();
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
	      hs.add(ob);
	    }
		
	 System.out.println("\n Displaying the List: \n");
	 
	 for(MyObj ob: hs)
         System.out.println("Name :"+ob.Name+" Roll Number : "+ob.roll_num);
		 
	 System.out.println("\n Printing using Iterator ");
       
         Iterator<MyObj> itr = hs.iterator();     	    
		
		while(itr.hasNext())
		{
		    MyObj ob = itr.next();
		    System.out.println("Name :"+ob.Name+" Roll Number : "+ob.roll_num);
		}
		
	   HashSet<String> hs2 = new HashSet<String>();
        hs2.add("nani"); hs2.add("nani");hs2.add("bujji");
   		
		 for(String ob: hs2)
		   System.out.println(ob);
		
	}
 
 }