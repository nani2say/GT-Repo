
//this algorithm is from career cup book

import java.util.*;

public class RemoveDupsBuffer
{
  
  public static LinkedList<Character> remove(LinkedList<Character> list)
  {
    boolean vars[] = new boolean[256]; 
    LinkedList<Character> result = new LinkedList<Character>();
		
     for(char temp: list)
      {
	    if(vars[temp] == false)
         {     		
		   result.add(temp);
		   vars[temp] = true;
	    }
	  }

    return result;	  
  }
 
 public static void main(String args[])
  {
   LinkedList<Character> list = new LinkedList<Character> ();
   
   list.add('1'); list.add('2'); list.add('1'); list.add('3'); list.add('7');
   list.add('2'); list.add('7');   
  
   list = RemoveDupsBuffer.remove(list);
  
    for(char c: list)
	System.out.println(c);
  }
}