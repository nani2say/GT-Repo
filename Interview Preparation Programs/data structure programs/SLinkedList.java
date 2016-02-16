import java.util.*;


class Node
{
  int data;
  Node next;

  Node()
  {
	this.next = null;
  }
  
  int getData()
   { 
      return this.data;
   }   
  
   public void setData(int x) 
               {
			    this.data = x;
			   }
   
    public void setNext(Node nxt)
     {
	   this.next = nxt;
	  }
     
	 public Node getNext()
	  {
	   return this.next;
	  }
   }


public class SLinkedList
{
  Node head;
  int size =0;
  
  public void insertFront(int data)
  {
    Node temp = new Node();
	temp.setData(data);
	
	if(head == null) 
	  {
	    head = temp;        
        head.setNext(null); 		
	  }
	 else
     {
	   temp.setNext(head);
	   head = temp;
	 }	 	 
	 size++;
  }

  public void insertAfter(int key, int data)
   {
     Node temp  = head;
	 
	 Node newNode = new Node();
	 newNode.setData(data);
	 
	 while(temp!= null)
	 {
	   if(temp.getData() == key)
         {
		   Node temp3 = temp.getNext();
		   temp.setNext(newNode);
		   newNode.setNext(temp3);
		   size++;		   
		   return;
		 }	     
		 temp= temp.getNext();
	 }
	 
	 System.out.println("Specified Node not found!");
   }
  
  
   public Node remove(int key)
   {
     Node temp = head;
	 Node target;
	 
	 if(head==null) return null;
	 
	 if(temp.getData()==key)
	 {
         head = head.getNext();		 
         size--; 			
	     return temp;			
	  }
	  
     while(temp.getNext()!=null)
	  {
	    if(key == temp.getNext().getData())
		  {
		   target= temp.getNext();
		   temp.setNext(temp.getNext().getNext());
  		   size--;
		   return target;
		  }
	    temp = temp.getNext();
	  }
	  
	  return null;
   }
   
   public Node removeLast()
   {
      Node target = null;
	  Node temp =head;
      
	     if(temp==null)  return null;
		 
		 if(temp.getNext()==null)
		  {
		    target = head;
			head =null;
			
			size--;
		    return target;
		  }
	     
     	 while(temp.getNext()!=null)
		  {
		    if(temp.getNext().getNext()==null)
			 {
			   target = temp.getNext();
			   temp.setNext(null);
			   size--;
			    return target;
			 }
		  
		   temp = temp.getNext();
		  }
		 
      return target;
   }
   
   public Node removeFirst()
   {
     Node target= null;
	 Node temp = head;
	 if(head == null) return null;
	 
	 target = head;
	 head = head.getNext();
     size--;
	 
     return target; 	  
   }
   
   public Node removePos(int pos)
   {
      Node target = null;
	  Node temp = head;
	  int index=2;
	  
	  if(pos<1 || pos> size) {  System.out.println("Invalid position! "); return null;}
	  
	  else if(pos == 1)    { target =removeFirst();  return target; }
	  
	  else if(pos == size) { target= removeLast();   return target; }
	  
	  while(temp.getNext()!= null)
	   {
	     if(index ==pos)
		  {
		   target = temp.getNext();
		   temp.setNext(target.getNext());
		   size--;
		   return target;
		  }
	   }
	  
	  return target;
	  
   }
   
   //remove duplicates using hashmap
   public void removeDups()
   {
     HashMap<Integer,Integer> hm  = new HashMap<Integer,Integer> ();
     Node temp = head;
	 Node prev = null;
	 
     while(temp!= null)
          {
		    if(hm.get(temp.data)!=null)
			 {
               prev.next= temp.next;			   
			 }
			 else
			 {
			  hm.put(temp.data,1);
			  prev = temp;                          			  
			 }
			 temp = temp.next; 
		  }	 
 	  
   }
   
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
	    System.out.println("-->"+temp.getData());
	    temp = temp.getNext();
	  }
   }
   
   
   public static void main(String args[])
   {
    SLinkedList sl = new SLinkedList();
	// sl.insertFront(23); sl.insertFront(43);
	// sl.print();
 
     	 Scanner sc = new Scanner(System.in);
	     int choice;
		 int data;
		 Node target;
		 
	while(true)
	{	
	  System.out.println("1.InsertFront 2.Remove 3.Insert after 4.Display List 5.Remove Last 6.Remove first 7.Remove Position \n 0 to Exit");
      choice = sc.nextInt();
	
	 switch(choice)
	  {
	   case 1:
	           System.out.println("Enter data ");
			   data = sc.nextInt();  
	           sl.insertFront(data);
			   break;
	   case 2:
	           System.out.println("Enter element to be removed ");
			   data = sc.nextInt(); 
               target = sl.remove(data);
               if(target==null)
			       System.out.println("Element not found ");
    		   else
	 		       System.out.println("Element deteled: "+target.getData());				   
			   break;			   
	   
	   case 3:
	           System.out.println("Enter new data ");
			   data = sc.nextInt();  
	           System.out.println("Enter element after which this data needs to be inserted ");
			   int key = sc.nextInt();  
	           sl.insertAfter(key,data); 
			   break;
	   
	   case 4:
	           sl.print();
               break;			   
	   
	   case 5:
	            target = sl.removeLast();
	            if(target==null)
			       System.out.println("list is empty ");
    		   else
	 		       System.out.println("Element deteled: "+target.getData());				   
			   break;	
		
		case 6:	   
		        target = sl.removeFirst();
	            if(target==null)
			       System.out.println("list is empty ");
    		   else
	 		       System.out.println("Element deleted: "+target.getData());				   
			   break;	
			   
		case 7:
                System.out.println("Enter position");
			    data = sc.nextInt(); 
                target = sl.removePos(data);
	            if(target==null)
			       System.out.println("Could not delete! ");
    		   else
	 		       System.out.println("Element deleted: "+target.getData());				   
			   break;		

        case 8:
              sl.removeDups();
              sl.print(); 			  
			  break;  
	   default:
	           System.exit(0); 
	           
	  } //switch	
	
    }//while
   }
}