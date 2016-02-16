import java.util.*;

class Student  
 {
   int sid; int marks;
   
   Student(int id, int mark)
    {
	  sid = id; marks = mark;
	}
 }
 
 public class StMap
 {
  HashMap<Integer,PriorityQueue<Integer>> hmap = new HashMap <Integer,PriorityQueue<Integer>>();
  ArrayList<Student> list = new  ArrayList<Student>();
  

  void process()  //list is declared global
   {
     for(Student s:list)
	 {
	   if(hmap.containsKey(s.sid))
	     {
		   PriorityQueue<Integer> pq = (PriorityQueue<Integer>)hmap.get(s.sid);
		   pq.add(s.marks);
		 }
		else
          {
		    PriorityQueue<Integer> pq = new PriorityQueue<Integer>(10,Collections.reverseOrder());
		    pq.add(s.marks);
			hmap.put(s.sid, pq);
		  }		
	 }
	 
	 //printing highest elemnt for each student
	 
	 Set<Map.Entry<Integer,PriorityQueue<Integer>>> set = hmap.entrySet();
	 
	 for(Map.Entry<Integer,PriorityQueue<Integer>> en: set)
	 {
	   PriorityQueue<Integer> pq = (PriorityQueue<Integer>)en.getValue(); 
	   System.out.println("Sid: "+(Integer)en.getKey()+ " Highest "+(Integer)pq.peek());
	 }
   
   }
  
  public static void main(String args[])
  {
    StMap ob = new StMap();
   
    ob.list.add(new Student(1,25)); ob.list.add(new Student(2,35));
    ob.list.add(new Student(1,17));ob.list.add(new Student(2,15));
	ob.list.add(new Student(1,67));ob.list.add(new Student(2,55));
    
    ob.process();	
  }  
	
 
 }