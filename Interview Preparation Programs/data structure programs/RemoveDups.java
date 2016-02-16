import java.util.*;

public class RemoveDups
{
 
 public static LinkedList<Integer> dups(LinkedList<Integer>  mylist)
  {
      LinkedHashSet<Integer> ls = new  LinkedHashSet<Integer>(mylist);	 
	  mylist = new LinkedList<Integer>(ls);
	  return mylist;
  }
  
 public static void main(String args[])
   {
     LinkedList<Integer> mylist = new LinkedList<Integer>();
	 mylist.add(23); mylist.add(33); mylist.add(23); mylist.add(43); mylist.add(23);
	 mylist.add(36);mylist.add(43);
	 
  	 mylist= dups(mylist);
	 System.out.println(mylist);
	 
   }
}