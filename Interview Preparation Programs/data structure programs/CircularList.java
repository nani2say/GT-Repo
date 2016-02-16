import java.util.*;

public class CircularList
{
   Node head;
   
   private class Node
   {
     private int data;
     private Node next;
     
     Node(int data)
	 {
	  this.data= data;
	  next =null;
	 }
   }
 
   CircularList()
    { head = null;}

  public void add(int data)
    {
	  Node newNode = new Node(data);
	  
	  if( head == null )
	  {
	    newNode.next = newNode;
        head= newNode;		
	  }
	  else
	  {
	    Node temp = head;
		while(temp.next != head)  temp = temp.next; 
	  
	     newNode.next =  temp.next;
         temp.next = newNode;
	  }
	}
	
	public Node remove(int data) // implementation with 2 pointers
	{
	  Node target;
      Node temp = head;
	  Node trail = null;
	  
	  if(head == null) return null;
	  
	    do
         { 
		   if(temp.data == data ) break;	
		   
		   trail = temp;
		   temp = temp.next;
		 }	while(temp!= head) ; 
       	
       if(temp.data == data) //if data found then 3 cases 
	      { 
		   target = temp;
		    
			if(count()==1){ head = null; } // for single element
		    
			else if(trail== null) // for first element delete case
			{
			  Node end = head;
			  while(end.next!=head) end= end.next; //traversing till the last node
			  
			  end.next = head.next;
			  head= head.next;
			}
			
			else // normal case
			{
			 trail.next = temp.next;
			}
		  } //big IF
	   	else 
		 { 
		     target =null;
         }		
		 
		 return target;
	     
	}
	
	public Node remove2(int data) //implementation with single pointer
	{
	 // total 4 cases needs to be covered => 
	  Node target;
      Node temp = head;
	  
	   if(head == null) return null; //case 1
	  
	   else if( head.data == data  &&  head.next == head )  //case 2
        {
			  head = null;
			  return temp;
	     }
		else if( head.data == data  &&  head.next != head ) //case 3
        {
          target = head;		
		  Node end = head;
		  
			  while(end.next!=head) end= end.next; //traversing till the last node
			  
			  end.next = head.next;
			  head= head.next;
		}	  
     	else if(count() !=1) //case 4: normal case
	     {
	 
	       while(temp.next.next!= head && temp.next.data != data) temp = temp.next;
       		
           if(temp.next.data == data ) //element found
             {
			  target = temp.next;
			  temp.next = temp.next.next;
			 }
            else target = null; //element not found			 		    
	     }
		else target =null; //case 5 where length =1 and its not head
		
		 return target;
	}
	
	public int count()
	{
	 Node temp = head;
	 int count =0;
	 
	 if(head ==null) {return 0;}
	 
	 else
	  {
	     do{
		     temp = temp.next;
			 count++;
		 }while(temp!=head);
	  
	  }
	  
	  return count;
	}
	
	public void print()
	{
	  if(head == null) System.out.println(" List is empty! ");
	  
	  else
	  {
	    Node temp = head;
        do{
		 System.out.println("->"+temp.data);
		temp = temp.next;
		}while(temp!=head);		
	  }
	}
  
   public static void main(String args[])
   {
     CircularList cl = new CircularList();
	 
	  Scanner sc = new Scanner(System.in);
	     int choice;
		 int data;
		 Node target;
		 
	while(true)
	{	
	  System.out.println("1.Insert 2.Remove 3.Insert after Pos 4.Display List 5. get count 0.to Exit");
      choice = sc.nextInt();
	
	 switch(choice)
	  {
	   case 1:
	           System.out.println("Enter data ");
			   data = sc.nextInt();  
	           cl.add(data);
			   break;
	   case 2:
	           System.out.println("Enter element to be removed ");
			   data = sc.nextInt(); 
               target = cl.remove2(data);
               if(target==null)
			       System.out.println("Element not found ");
    		   else
	 		       System.out.println("Element deleted: "+target.data);				   
			    break;			   
	   
	   case 3:
	           // System.out.println("Enter new data ");
			   // data = sc.nextInt();  
	           // System.out.println("Enter element after which this data needs to be inserted ");
			   // int key = sc.nextInt();  
	           // sl.insertAfter(key,data); 
			   break;
	   
	   case 4:
	           cl.print();
               break;			   
	   
       case 5:
                int count = cl.count();
             			System.out.println(" Count = "+count);
                  break;						
	   default:
	           System.exit(0); 
	           
	  } //switch	
	
    }//while
   } //main
}