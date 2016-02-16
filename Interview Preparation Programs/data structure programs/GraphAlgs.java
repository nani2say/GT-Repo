import java.util.*;

class GraphAlgs
{
 Node root;
 
 private class Node
 {
    int data;
	boolean visited ;
    ArrayList<Node> children;  
 
   Node(int data)
    {
	    this.data = data;
	   this.visited= false; 
	  children = new ArrayList<Node>();
	}
 }

  public void buildgraph()
    {
	  root = new Node(1);
	  
	  root.children.add(new Node(2)); root.children.add(new Node(3)); root.children.add(new Node(4));
	
	  // for each child
	  Node id2 = root.children.get(0);
      id2.children.add(new Node(5)); id2.children.add(new Node(6)); 
	  
	  Node id3 = root.children.get(1);
	  id3.children.add(new Node(7));
	  
	  Node id4 = root.children.get(2);
      id4.children.add(new Node(8)); id4.children.add(new Node(9)); id4.children.add(new Node(10)); 	  
	  
	 }
	 
	//DFS visits the root node and then its children nodes until it reaches the end node, then moves up to the parent nodes. 
	 public void dfsrecursive(Node start)
	 {
	  if(start!=null)
	     System.out.print("->"+start.data);
	  
      for(Node temp:start.children)
         {
		  dfsrecursive(temp);
	 	 }	  
	  }
     
     public void bfsrecursive(Node start)
      {
	     if(start==root && start!=null)
	     System.out.print("->"+start.data);
	  
      for(Node temp:start.children)
         {
		  System.out.print("->"+temp.data);		 
	 	 }	 
		 
	   for(Node temp:start.children)
         {		  
		   bfsrecursive(temp);
	 	 }	 
	  } 	 
		 
	 public void bfsIterative()
      {		
		  Queue<Node> que = new LinkedList<Node> ();		  
		  que.add(root);
		
		while(que.size()!=0)
		    {
			  Node parent = que.remove();
			  System.out.print("->"+parent.data);
			  for(Node temp:parent.children)
			   que.add(temp);
			}
		
	  }	 
	  
	  public void dfsIterative()
	  {
	    Stack<Node> stk = new Stack<Node>();
        stk.push(root);
              
		while(!stk.isEmpty())
         {
		 
		   Node parent = stk.peek();
		   Node child;
		   
		   if(parent.visited==false)
		          {
				     System.out.print("->"+parent.data);
				     parent.visited = true;
				  }
				  
		    if((child = getUnvisited(parent)) != null )
              	{
				 stk.push(child);
				}
             else
               {
			     stk.pop();
			   }			 
		 }		
	   
	  }
		 
	  public Node getUnvisited(Node parent)
         {
			for(Node tmp:parent.children)
			 {
			   if(tmp.visited == false)
			      return tmp;
			 }
			
			return null;
			
	       }	  
	  public static void main(String args[])
	  {
	    GraphAlgs graph = new GraphAlgs();
		 System.out.println("\n building a static graph ");
		 graph.buildgraph();
		 
		 System.out.println("\n traversing using dfs recursive  ");
          graph.dfsrecursive(graph.root);	
        	    
		 System.out.println("\n\n traversing using bfs recursive   ");
		 graph.bfsrecursive(graph.root);			
		
    	 System.out.println("\n\n traversing using bfs Iterative   ");
		  graph.bfsIterative();
		  
		 System.out.println("\n\n traversing using dfs Iterative   ");		
		  graph.dfsIterative();
	  }

}