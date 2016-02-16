import java.util.*;

/* this program is written to consider 
   1.multiple starting points for graph
   2.to use HashSet instead of 'isvisited' component at each node during the traversal
*/   
  
class GraphAlgsHashSet
{
 List<Node> roots = new LinkedList<Node>();
 HashSet<Node> visited; 
 private class Node
 {
    int data;
    List<Node> children;  
 
    Node(int data)
    {
	    this.data = data;	  
	    children = new LinkedList<Node>();
	}
	
   List<Node> getChildren()
   {
     return children;
   }   
	 
 }

  public void buildgraph()
    {
	  Node root1 = new Node(1);
	  
	  root1.children.add(new Node(2)); root1.children.add(new Node(3)); root1.children.add(new Node(4));
	
	  // for each child
	  Node id2 = root1.children.get(0);
      id2.children.add(new Node(5)); id2.children.add(new Node(6)); 
	  
	  Node id3 = root1.children.get(1);
	  id3.children.add(new Node(7));
	  
	  Node id4 = root1.children.get(2);
      id4.children.add(new Node(8)); id4.children.add(new Node(9)); id4.children.add(new Node(10)); 	
     
	  roots.add(root1);
      
	  //---
	  Node root2 = new Node(12);	 
	  root2.children.add(id4); //connecting to the previous node in the graph
	  Node n13 = new Node(13);
	  root2.children.add(n13);
	  Node n14 = new Node(14);
	  
	  n13.children.add(n14);
	  n14.children.add(id4.children.get(2)); //connected to a previous node
	  n13.children.add(new Node(15));
	  
	  roots.add(root2);
	  //---  
	  
	 }
	 
   void dfsrecursive()
     {
	   visited = new HashSet<Node>();
	  
	  //for every starting point in the graph.
	  for(Node n: roots)
	    {
		  dfsrec(n);
		}
	 }
	 
	void dfsrec(Node start)
	 {
	   visited.add(start);
	   
	   for(Node child: start.getChildren())
	    {
		 if( visited.contains(child)== false) dfsrec(child);
		}
	  
      System.out.println("->"+start.data);	  
	 
	 }
	 
   void dfsIterative()
    {
	  
	  visited = new HashSet<Node>();
	  
	  //for every starting point in the graph.
	  for(Node n: roots)
	    {
		  dfsIter(n);
		}
	}
	
	void dfsIter(Node start)
	 {
	   
	   if(visited.contains(start)) return; //return if this sub graph is already visited
 	  
       Stack<Node> st = new Stack<Node>(); 
	  	  
	   st.push(start);
	   visited.add(start);
	   	   
	   while(!st.isEmpty())
	     {
		   Node parent = st.peek();
		   
           Node child = null;
       
            if( (child= getUnvisited(parent))!=null)
              {
			    visited.add(child);
                st.push(child);				
			  }			
		    else
              {
			   System.out.println("->"+parent.data);
			   st.pop();
	          }		
		  }	   
	 }
	 
	Node getUnvisited(Node parent)
	 {
	   for(Node ch:parent.getChildren()) 
	     {
		   if(!visited.contains(ch)) return ch;
		 }
	  
	  return null;
	 }
	 
	void bfsIterative()
    {
       visited = new HashSet<Node>();
	  
	  //for every starting point in the graph.
	  for(Node n: roots)
	    {
		  bfsIter(n);
		}
		
    } 
	 
 void bfsIter(Node start)
      {
	   
	   if(visited.contains(start)) return; //return if this sub graph is already visited
	   
 	   Queue<Node> list = new LinkedList<Node>(); 
	   
	   list.add(start);
	   visited.add(start); 
       System.out.println("->"+start.data);
		
	   while(list.isEmpty()==false)
         {
		   Node parent = list.remove();
		   Node child;
		   
		    while((child=getUnvisited(parent))!=null)
			     {
				   System.out.println("->"+child.data);
				   visited.add(child); 
                   list.add(child);				   
				 }
		 }	   
	  }
	
	  public static void main(String args[])
	  {
	     GraphAlgsHashSet graph = new GraphAlgsHashSet();
		 System.out.println("\n building a static graph ");
		 graph.buildgraph();
		 
		 System.out.println("\n traversing using dfs recursive  ");
          graph.dfsrecursive();	
   		  
		 System.out.println("\n\n traversing using dfs Iterative   ");		
		  graph.dfsIterative();
		   
	     System.out.println("\n\n traversing using bfs Iterative   ");
		  graph.bfsIterative();
		  
	     //bfs iterative is not a good algorithm and no one uses it	
	
	}

}