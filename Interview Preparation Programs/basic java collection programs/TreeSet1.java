import java.util.*;


 public class TreeSet1
 {
    public static void main(String args[])
	 {
	  
	   TreeSet ts = new TreeSet();
	   
	   ts.add(17);
	   ts.add(7);
	   ts.add(3);
	   ts.add(5);
	   ts.add(4);
	   
  Iterator iterator = ts.iterator();
        System.out.print("Tree set data: ");
 
        //Displaying the Tree set data
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
		
	 //String s ="HiHello";	
	 //System.out.println("index is "+s.indexOf("ll"));	
	 
	 
	// ^(\(\d{3}\)|\d{3})-?\d{3}-?\d{4}$
	 }
	 
 }