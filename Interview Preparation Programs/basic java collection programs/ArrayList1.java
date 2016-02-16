import java.util.*;

class MyObj
 {
    String Name;
	int roll_num; 
 }

public class ArrayList1
 {
  public static void main(String args[])
    {
	  ArrayList<MyObj> arrlist = new ArrayList<MyObj>();
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
	      arrlist.add(ob);
	    }
		
	 System.out.println("\n Displaying the List: \n");
 
      for(int i=0;i<arrlist.size();i++)
        	  System.out.println("Name :"+arrlist.get(i).Name+" Roll Number : "+arrlist.get(i).roll_num);
		
	}
 
 }