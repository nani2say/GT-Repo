import java.util.*;


class MyComp implements Comparator<Integer>
 {
   public int compare(Integer x, Integer y)
     {
	   return -(x-y); //reverse the priority
	 }
 }

public class PQ
{
  public static void main(String args[])
   {
      PriorityQueue<Integer> pq = new PriorityQueue<Integer> (5,new MyComp());
	  
	  pq.add(12); pq.add(7); pq.add(32); pq.add(22);
   
      while(pq.size()!=0)
	   {
	     System.out.println(pq.poll());
	   }
   }
}