

import java.util.*;

class CustomComparator implements Comparator<Integer>
  {
   public int compare(Integer x, Integer y)
     {
	  if (x>y) return -1;
	  else if(x<y) return 1;
	  return 0;
	 }
  
  }

 public class PriorityQueue1
 {
    public static void main(String args[])
	 {
	  
	   PriorityQueue<Integer> pq= new PriorityQueue<Integer>(10,new CustomComparator());
	   pq.add(17);
	   pq.add(25);
	   pq.add(13);
	   pq.add(19);
	   pq.add(13);
	   
	   while(pq.size()!=0)
	    {
		 System.out.println(pq.poll());
		}
          	 
	 }
 }