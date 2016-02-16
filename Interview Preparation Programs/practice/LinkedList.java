
public class LinkedList
{
 private class Node
  {
    int data;
	Node next;
  
     Node(int data)
	   {
	     this.data = data;
		 next = null;
	   }
   }

  Node head = null;
  
    public void print()
   {
     Node temp = head;
	  
	 if(head == null)
        {
		 System.out.println(" List is Empty! ");
		 return;
		}	 
	  
     while(temp!=null)
	  {
	    System.out.println("-->"+temp.data);
	    temp = temp.next;
	  }
   }
   
   public void insert(Node start, int data)
     {
	   if(start == null) { start = new Node(data); return ;}
	    
	   else 
	    {
	      insert(start.next, data);	   
	    }
	 
	 }
	 
	 public void insert2( int data)
	 {
	  Node temp = head;
	  
	  if(temp == null) { head = new Node(data); return;}
	  
	  while(temp.next!=null) temp = temp.next;
	  
	  temp.next = new Node(data);
	
	 }

  public static void main(String args[])
   {
    LinkedList sl = new LinkedList();
	sl.insert2(23);
   	sl.insert2(43);
	sl.print();
	 
	}
}