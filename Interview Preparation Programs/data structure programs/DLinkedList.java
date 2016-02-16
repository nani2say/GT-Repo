import java.util.*;

class DNode
{
  int data;
  DNode next, prev;
  
  DNode()
  {
	this.next = null;
    this.prev = null;
  }
  
   public int getData()
   { 
      return this.data;
   }   
  
   public void setData(int x) 
               {
			    this.data = x;
			   }
			   
	public void setNext(DNode nxt)
     {
	   this.next = nxt;
	  }
     
	 public DNode getNext()
	  {
	   return this.next;
	  }

    public void setPrev(DNode prev)
     {
	   this.prev = prev;
	  }
     
	 public DNode getPrev()
	  {
	   return this.prev;
	  }		 	  
}

public class DLinkedList
{
 DNode head;
 
 public void insertFront(int data)
 {
   DNode newNode = new DNode();
   newNode.setData(data);
   
   if(head== null)  { head = newNode; return ;}
   
   newNode.setNext(head);
   head.setPrev(newNode);   
 
   head= newNode;
   
 }
 
 public void insertLast(int data)
 {
   DNode newNode = new DNode();
   newNode.setData(data);
   
   if(head== null)  { head = newNode; return ;}
   
    DNode temp = head;
	
   while(temp.getNext()!=null)
     {
	  temp = temp.getNext();
	  }
	 
	 temp.setNext(newNode);
	 newNode.setPrev(temp);
	 
 }
 
 public void insertAfter(int key, int data)
 {
   DNode newNode = new DNode();
   newNode.setData(data);
   
   if(head==null)  {System.out.println("element not found"); return; }
   
   DNode temp = head; 
   
   while(temp!=null && temp.getData()!= key )
    {
	 temp= temp.getNext();
	}
   
   if(temp == null)  {System.out.println("element not found"); return; }
  
   DNode temp2 = temp.getNext(); 
   temp.setNext(newNode);
   newNode.setNext(temp2);
   newNode.setPrev(temp);
 
   if(temp2!=null)
       temp2.setPrev(newNode);   
   
 }
 
 public void print()
  {
    DNode temp = head;
	
	if(head==null) 
	 { 
	   System.out.println("List is empty "); 
	   return;
     }
	 
	 while(temp!=null)
	  {
	    System.out.println("-->"+temp.getData());
	    temp = temp.getNext();
	  }
  }
 
 public DNode remove(int key)
  {
      DNode target;
      DNode temp = head;
      if(head==null)  {System.out.println("list is empty"); return null; }	  
   
      while(temp!=null && temp.getData()!=key)
	   {
	     temp = temp.getNext();
	   }
	   
	  if(temp == null) return null;
	 
       if(temp == head)
	   { 
	     head= head.getNext();
		}
		
       target = temp;
	   
       if(temp.getPrev()!=null) 
          {
		    temp.getPrev().setNext(temp.getNext());			
           }		  
	  if(temp.getNext()!=null)
	      {
            temp.getNext().setPrev(temp.getPrev());
            }	

        return target;			
   }
 
 public static void main(String args[])
   {
    DLinkedList dl = new DLinkedList();
    
	 Scanner sc = new Scanner(System.in);
	     int choice;
		 int data;
		 DNode target;
		 
	while(true)
	{	
	  System.out.println("1.InsertFront 2.Remove 3.Insert after 4.Display List 5.Remove Last 6.Remove first 7.Remove Position 8.Insert Last\n 0 to Exit");
      choice = sc.nextInt();
	
	 switch(choice)
	  {
	   case 1:
	           System.out.println("Enter data ");
			   data = sc.nextInt();  
	           dl.insertFront(data);
			   break;
		
        case 2:		
		 System.out.println("Enter element to be removed ");
			   data = sc.nextInt(); 
               target = dl.remove(data);
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
	           dl.insertAfter(key,data); 
			   break;		

	   case 4:
	           dl.print();
               break;			   
	  
       case 8:
	           System.out.println("Enter data ");
			   data = sc.nextInt();  
	           dl.insertLast(data);
			   break;
	  				    
	   default:
	           System.exit(0); 
	           
	  } //switch	
	
    }//while 
   
   }

}