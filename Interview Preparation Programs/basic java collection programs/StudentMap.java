import java.util.*;

class Student
{
 public int rollno;
 public int marks;
 
 Student(int roll, int mark)
   {
      rollno = roll;
	  marks= mark;
    } 
}

//This comparator is designed to make the Priority queue hold highest marks item at top
class Mycomp implements Comparator<Student>
  {
   public int compare(Student s1, Student s2)
      {
	    if(s1.marks<s2.marks) return 1;
		else if(s1.marks==s2.marks) return 0;
		else return -1;
	  }
  }

public class StudentMap
{
 static void process(ArrayList<Student> records)
   {
   
     for(Student s: records)
	     System.out.println("Roll no"+s.rollno);
		 
     Mycomp mc = new Mycomp();
     HashMap<Integer,PriorityQueue<Student>> hm = new HashMap <Integer,PriorityQueue<Student>> ();
	 
     for(Student st: records)
	    {
		  PriorityQueue<Student> pqold = hm.get(st.rollno);
		  if(pqold==null)
		  {		    
		    PriorityQueue<Student> pq = new PriorityQueue<Student>(100,mc);
			pq.add(st);
			hm.put(st.rollno, pq);
		  }
		  else
		  {
		   pqold.add(st);
		   hm.put(st.rollno, pqold);
		  }
		}
		
	   Set set = hm.entrySet();
       
	   Iterator itr = set.iterator();
	   
	   HashMap<Integer, Float> result = new HashMap<Integer, Float>();  //roll number , marks 
	   
	   while(itr.hasNext())
	    {
		  Map.Entry me = (Map.Entry)itr.next();
		  
		  Integer roll = (Integer)me.getKey();
		  
		  System.out.println("New Record- roll no "+roll);
		  
		  PriorityQueue<Student> pqnew = (PriorityQueue<Student>) me.getValue();
          
		  int count =0;
		  int sum =0;
		  
		  while(pqnew.size()!=0 && count!=5)
		   { 
		    count++;
			int temp =pqnew.poll().marks;	
			System.out.println("temp= "+temp);
			sum +=  temp;  
		   }
          
          System.out.println("sum= "+sum);
		  
		  float avg = (float)sum/count; 
		  
		  result.put(roll,avg);
		}
	   
         Iterator<Integer> iter = result.keySet().iterator();   
    
	      while(iter.hasNext())
		  {
		    Integer rol = (Integer)iter.next();
		    Float val = result.get(rol);
			
			System.out.println("Roll "+rol+ " Avg ="+val);
		  }
   
    }

 public static void main(String args[])
 {
  ArrayList<Student> al = new ArrayList<Student> ();
  
  al.add(new Student(1,23));
  al.add(new Student(1,21));al.add(new Student(1,22));
    al.add(new Student(1,24));al.add(new Student(1,25));al.add(new Student(1,26)); //mixed
  
  al.add(new Student(2,23)); al.add(new Student(2,24));al.add(new Student(2,20));al.add(new Student(2,25));

  al.add(new Student(2,21));al.add(new Student(2,22));
 
  process(al);
  
   // Mycomp mc = new Mycomp();
   // PriorityQueue<Student> pq = new PriorityQueue<Student>(100,mc);
   // pq.add(al.get(0));pq.add(al.get(1)); pq.add(al.get(2));pq.add(al.get(3));pq.add(al.get(4));pq.add(al.get(5));
   
   // while(pq.size()!=0)
    // {
	// System.out.println(" item ="+pq.poll().marks);
	// }
 }

}