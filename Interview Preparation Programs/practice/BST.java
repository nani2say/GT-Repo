//// This just for practice

import java.util.*;

public class BST 
{
  Node root;
  int nodecount;
  
 private class Node
   {
    int data;
	Node left, right;
	
	Node(int data)
	 {
	   this.data = data;
       left= right = null;	 
	 }
	 
   }
 
  public void insert_node2(Node start, int data)
  {
      if(start == null) 
           {            		   
		     start = new Node(data);		 
			}
		   
     else if( data < start.data )
                insert(start.left, data);
     else if ( data > start.data ) 				
	           insert(start.right, data);	     
  }
   
  public void insert_node(int data)
  {
     root = insert(root,data);
  }
 
  public Node insert(Node  start, int data)
   {	 
     if(start == null) 
           { 
             Node  newnode = new Node(data);		   
		     start = newnode;
			 return start; 
			 
			}
		   
     else if( data < start.data )
                start.left =  insert(start.left, data);
     else if ( data > start.data ) 				
	            start.right = insert(start.right, data);
          
	        return start;
    
   }
   
   public void delete_node(int data)
   {
      root=  delete(root, data);
   }
   
   public Node delete(Node start, int data)
   {
     if(start ==null)
	 {
	     System.out.println(" Element not found ! ");
	  }
     else if(data < start.data)
	 {
	   start.left = delete(start.left,data);
	 }
   
    else if(data > start.data)
	{
	   start.right = delete(start.right, data);
	   
	}
     else   //data == start.data
	     {
		    if(start.left ==null && start.right == null) 
			 {
			   start =null;
			 }
			 
			else if(start.left == null)
               {
			     start = start.right;
			   }			
			   
			 else if(start.right == null)
               {
    			     start = start.left;			   			   
			   }	
             else
			  {
			    start.data = get_right_most(start.left); //get right most data of left sub tree 
				start.left = delete(start.left, start.data);
			  }		  
		 }
		  
		   return start;
   }

   
   public int get_right_most(Node start)
   {
    while(start.right!=null) start= start.right;
   
    return start.data;
	
   }
   
   public void inorder(Node  start)
     {
	    if(start!=null) 
		{
		  inorder(start.left);
		  System.out.println(" ->"+start.data);
		  inorder(start.right);
		}
	 }
	 
	public void postorder(Node  start)
     {
	    if(start!=null) 
		{
		  postorder(start.left);		  
		  postorder(start.right);
		  System.out.println(" ->"+start.data);
		}
	 }
	  
	public void postIterative()
    {
	 Node start = root;
	 
	 Stack<Node> st = new Stack<Node>();
	 st.push(root);
	 
	 while(!st.isEmpty())
	 {
	   Node temp= st.peek();
	   
	   if(temp.left!=null) st.push(temp.left);
	   if(temp.right!=null) st.push(temp.right);
	   
	 }
	}	
	  
	 
  public int minHeight(Node start)
    {
	 if(start==null) return 0;
	 
	 else
	   return (1+Math.min(minHeight(start.left),minHeight(start.right)) );
	}  	
	
  public int maxHeight(Node start)
    {
	 if(start==null) return 0;
	 
	 else
	   return (1+Math.max(maxHeight(start.left),maxHeight(start.right)) );
	}  	
   
  public int checkBalance()
   {
      int min = minHeight(root);
	  int max = maxHeight(root);
	  
      System.out.println("min Depth ="+min+" Max Depth = "+max);
      return (max - min);
   }  
   
  public Node rotateLeft(Node start)
   {
     Node temp = start.right;
	 start.right = temp.left;
	 temp.left = start;
	 return temp;
   }  
   
  public Node rotateRight(Node start)
   {
     Node temp = start.left;
	 start.left = temp.right;
	 temp.right = start;
	 return temp;
   } 
  
  public Node arrayBalanceInsert(int arr[], int left, int right)
  { 
    if(left>right) return null;
	
	int mid = (left+right)/2;
	
	Node temp = new Node(arr[mid]);
    temp.left = arrayBalanceInsert(arr,left,mid-1);	
    temp.right = arrayBalanceInsert(arr,mid+1,right); 
	
	return temp;
  }
  
 public int lowestCommonAncestor(int x, int y)
   {
     Node curr = root;
	 
	 while(curr!=null)
	  {
	     if(curr.data >x && curr.data >y)  curr = curr.left;
		  
		 else if(curr.data <x && curr.data <y)  curr = curr.right; 
	  
	     else return curr.data;
	  }
   
     return -1; //no ancestor found
    } 
  
  
  //root level 0. get linked lists of all level nodes
  public ArrayList<LinkedList<Node>> getNodesAtEachLevel()
   {
     ArrayList<LinkedList<Node>> storage = new  ArrayList<LinkedList<Node>> ();
     int level =0;
	 
	 LinkedList<Node> temp = new LinkedList<Node>();
     temp.add(root);
    
	 storage.add(level,temp);
	
     while(true) //will break when all levels are reached
      {
	    temp = new LinkedList<Node>();
		
	    for(int i=0; i< storage.get(level).size(); i++)
		{
		  Node node =  storage.get(level).get(i);
		  
		  if(node.left!=null) temp.add(node.left);
		  if(node.right!=null) temp.add(node.right);		
		}
	    
		if(temp.size()!=0) storage.add(level+1, temp);
	    else  break;
		   
        level++;		
	  }	 
	 
	 //printing the final list
	 level=0;
	 for(LinkedList<Node> list: storage)
	 {
	    System.out.println(" level= "+level);
		
	    for(Node t: list)
		  {
		    System.out.println("->"+t.data);
		  }
		  System.out.println();
	  
	  level++;
	 }
	 
     return storage;
   }  
  
  public void getSpecificLevel(Node start, int level, LinkedList<Node> list)
    {
	
	 if(level ==0) list.add(start);
	             
	 else
	 { 
	    if(start.left!=null)
	    getSpecificLevel(start.left, level-1, list);
            
	    if(start.right!=null)
	    getSpecificLevel(start.right, level-1, list);	   
	 }
	 
	}  
  
  
 public static void main(String args[])
   {
      BST bst = new BST ();
	  
   
	  Scanner sc = new Scanner(System.in);
	     int choice;
		 int data;
		// Node  target;
	
 	
	while(true)
	{	
	  System.out.println("\n 1.Insert 2.Remove 3.Insert after Pos 4.Display List 5. get count 6. get nodes at each level 0.to Exit");
      choice = sc.nextInt();
	
	 switch(choice)
	  {
	   case 1:
	            System.out.println("Enter data ");
			    data = sc.nextInt();  
	            bst.insert_node2(bst.root, data);
			   break;
			   
	   case 2:
	            System.out.println("Enter element to be removed ");
			    data = sc.nextInt(); 
                bst.delete_node(data);				   
			    break;			   
	   
	   case 3:
	           // System.out.println("Enter new data ");
			   // data = sc.nextInt();  
	           // System.out.println("Enter element after which this data needs to be inserted ");
			   // int key = sc.nextInt();  
	           // sl.insertAfter(key,data); 
			   break;
	   
	   case 4:
	           if( bst.root==null ) System.out.println(" BST is empty! ");
	           bst.inorder(bst.root);
               break;			   
	   
       case 5:
                // int count = cl.count();
             			// System.out.println(" Count = "+count);
                   break;						
	   
	   case 6:
	           bst.getNodesAtEachLevel();
			   break;
	  
       case 7:
	           System.out.println("Enter level number ");
			   int level = sc.nextInt();
			   LinkedList<Node> temp = new LinkedList<Node>();
	           bst.getSpecificLevel(bst.root,level,temp);
			   for(Node t:temp)
			   System.out.print(" -> "+t.data); 
			   break;
			   
	  default:
	           System.exit(0); 
	           
	  } //switch	
	
    }//while
   
 
/* 
   //Hard coding the binary search tree for balancing tests
   
    // $$$$$$$ This is for Left Rotation $$$$$$$$$$   
	bst.insert_node(23); bst.insert_node(17);bst.insert_node(45); bst.insert_node(33);
	bst.insert_node(56);bst.insert_node(27);bst.insert_node(38); bst.insert_node(49);
	bst.insert_node(64);
	
	bst.postorder(bst.root);
	
	System.out.println(bst.checkBalance());
	System.out.println("rotating left since the tree is not balanced ");
	bst.root = bst.rotateLeft(bst.root);
	System.out.println("Printing after rotation ");
	bst.postorder(bst.root);
    
	System.out.println(bst.checkBalance());
 */	

 /*
  //$$$$$$$$$$$$$ This is for Right Rotation $$$$$$$$$$$
	bst.insert_node(23); bst.insert_node(32);bst.insert_node(15); bst.insert_node(7);
	bst.insert_node(3);bst.insert_node(10);bst.insert_node(19); bst.insert_node(18);
	bst.insert_node(21);	
	bst.postorder(bst.root);
	
	System.out.println(bst.checkBalance());
	System.out.println("rotating Right since the tree is not balanced on right");
	bst.root = bst.rotateRight(bst.root);
	System.out.println("Printing after rotation ");
	bst.postorder(bst.root);
	
	*/
	
 //$$$$$$$$$$ This is to balance a tree using a sorted array $$$$$$$	
 /*    
	int arr[] = {3,7,10,15,18,19,21,23,32} ;
	bst.root = bst.arrayBalanceInsert(arr,0,arr.length-1);
		
    bst.insert_node(2);
	//bst.inorder(bst.root);
	
	System.out.println(bst.lowestCommonAncestor(10,3));
 */

//$$$$$$$$$ this is to call a program to get linkedlists at each level of the bst 
   
   }
}