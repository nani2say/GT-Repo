import java.util.*;

 public class HashSet1
  {
    public static void main(String args[])
    {
	  HashSet<Integer> hs = new HashSet<Integer>();
	  Scanner sc = new Scanner(System.in);
	  
	  for(int i=0;i<3;i++)
	    {	 
	     System.out.println(" Enter a number ");
		 int num = sc.nextInt();		
	      hs.add(num);
	    }
		
	 System.out.println("\n Displaying the List: \n");
 
      for(Integer x: hs)
        	  System.out.println("Num :"+x);
		
	}
  
  }