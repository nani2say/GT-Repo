//this is with reference to book - "programming interviews exposed".  Linked list problem
//List Flattening

public class FlattenList
{
  Node head, tail;
  
  class Node
   {
    Node prev, next, child;
	int data;
   
    Node (int x)
	{
	 data = x ;
	 prev = next = child = null;
	}
   }
   
 FlattenList()
  {
    head = new Node(5);
    head.prev = null;
	Node l1n1 = new Node(33);
	Node l2n1 = new Node(6);
	head.next = l1n1;
	head.child = l2n1;
	
	Node l1n2 = new Node(17);
	
	l1n1.prev=head;
	l1n1.next= l1n2;
	l1n1.child=null;
	
	Node l1n3 = new Node(2);
	
	l1n2.next= l1n3;
	l1n2.prev = l1n1;
	l1n2.child=null;
	
	Node l1n4 = new Node(1);
	
	l1n3.next=l1n4;
	l1n3.prev = l1n2;
	
    l1n4.next=null;
	l1n4.prev=l1n3;
	l1n4.child =null;
	
	Node l2n2 = new Node(25);
	l1n3.child= l2n2;
	
	l2n2.prev = l2n1;
	l2n2.next = null;
	l2n1.next = l2n2;
    		
	Node l3n1 = new Node(8);
	l2n2.child = l3n1;
  
    tail =l1n4;
  
  }
   
  public void normalTraverse(Node start)
  {
    if(start!=null)
    System.out.println("Data: "+start.data);
	
	if(start.child!=null) normalTraverse(start.child);
	
	if(start.next!=null)  normalTraverse(start.next);
  }  
  
 public void flatTraversal()
  {  
   Node curr = head;
  
   while(curr!=null)
    {
	  System.out.println("Data: "+curr.data);
	  curr= curr.next;
	}
  } 
   
   
   
 public void flatten()
  {
   Node curr = head;
   
   while(curr!=null)
    {
	  if(curr.child!=null)
	  {
	    //appending at the end
		tail.next = curr.child;
		tail = curr.child;
	  }	  
	  curr = curr.next;	
	}
	 
  } 
  
  public void unflatten(Node start)
   {	  
     if(start.child!=null)
	   { 
	     Node temp = start.child;
	     Node curr = start;
		 while(curr.next!= temp)
		  {
		   curr = curr.next; 
		  }
		  
		  curr.next = null; //cutt off from this level
		  temp.prev = null; //child cutting off from parent
		  
       	  unflatten(temp);		  
	   }
	 else if(start.next!=null)
       {
	     unflatten(start.next);
	   }	 
   }
      
 public static void main(String args[])
    {
      FlattenList fl = new FlattenList();
	  fl.normalTraverse(fl.head);
	  fl.flatten();
	  System.out.println(" \n Flat Traversal ");
	  fl.flatTraversal();
	  
	  System.out.println(" \n Normal Traversal ");
	  fl.normalTraverse(fl.head);
	  
	  fl.unflatten(fl.head);
	  System.out.println(" \n Flat Traversal after Unflattening ");
	  fl.flatTraversal();
	  
	  	  System.out.println(" \n Normal Traversal after Unflattening");
	  fl.normalTraverse(fl.head);
	}
}