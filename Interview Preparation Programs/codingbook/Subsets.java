import java.util.*;

public class Subsets
{
 ArrayList<Integer> set;
 ArrayList<ArrayList<Integer>> result;
 
 final int size =4;
 
 Subsets()
 {
   result = new ArrayList<ArrayList<Integer>>();
   set = new ArrayList<Integer> ();
   
   for(int i = 0; i<size; i++)
     {
	   set.add((int)(Math.random()*100));
	 }
 }
 
  public ArrayList<Integer> add2(int start, int end)
  {
    ArrayList<Integer> temp = new ArrayList<Integer>();
   
    for(int i =start; i<=end ;i++)
    temp.add(set.get(i));
	
	return temp;
  }
 
 public ArrayList<ArrayList<Integer>> subN(int n)
   { 
     if( n > set.size()) return null;
	 
	 int step = n-1; 
	 
    ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
    ArrayList<Integer> temp;
	
	 int start =0;
	   for(int j=step ;j<set.size();j++)
	    {
		  temp = add2(start,start+step);
		  start++;
          res.add(temp);		  
		}

     return res;
	 
   }
   
 public void findSubsets()
 {
  result = new ArrayList<ArrayList<Integer>>();
  ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
  
   for(int i=1; i<set.size(); i++)
      {
	     res = subN(i);
		 
		 for(ArrayList<Integer> al: res)
		   {   result.add(al); }
	  }
 } 
 
 public void recursiveSubs()
  {
   //recurse(set);
   result = recurseStore(set);
  }
 
 public void recurse(ArrayList<Integer> al)
   {
     ArrayList<Integer>  p = new ArrayList<Integer>();
	  
      int size =al.size();
	  	   
	  if (size ==1)
    	 {   //temp.add(al);
            // return temp; 
            return;		
		}
		 
     else
         {
          for(int i =0 ;i < size;i++)
		     {
			  p = new ArrayList<Integer>();
			  
			   for(int j=0; j< size; j++)
			       {
                    if(i!=j)
 				     p.add(al.get(j));				   
				   }
			     
				// System.out.println("alpha"+p);
				 result.add(p);
				 
				   recurse(p);
				 		 
   		      }
         }		 
   }
 
  public ArrayList<ArrayList<Integer>>  recurseStore(ArrayList<Integer> al)  //same as normal recurse BUT with temporary storages
   {
     ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
	 ArrayList<ArrayList<Integer>> tempres; 
	 
     ArrayList<Integer>  p = new ArrayList<Integer>();
	  
      int size =al.size();
	  	   
	  if (size ==1)
    	 {   //temp.add(al);
            // return temp; 
            return null;		
		}
		 
     else
         {
          for(int i =0 ;i < size;i++)
		     {
			  p = new ArrayList<Integer>();
			  
			   for(int j=0; j< size; j++)
			       {
                    if(i!=j)
 				     p.add(al.get(j));				   
				   }
                   
				   res.add(p); //first add the current sets
				   
				   tempres  = new ArrayList<ArrayList<Integer>>(); 
				   tempres  = recurseStore(p);
				   
				   if(tempres!=null)
				   {
				    for(ArrayList<Integer> alist: tempres)
				      {
					    res.add(alist);
					  }
				 	}	 
   		      }
			  
			  return res;
         }		 
   }
 
 
 
 public void process()
   {
     HashSet<ArrayList<Integer>> hs = new HashSet<ArrayList<Integer>>();
	 
	 for(ArrayList<Integer> al: result)
		   {
		     hs.add(al);
		   }
     
	 System.out.println("Printing the Final Result from HASH set");
	 int i =1;
    
	for(ArrayList<Integer> al: hs)
		  {
		    System.out.println();
                             
 			   for(Integer x: al) 
			        System.out.print(" "+x);
					
					
		  } 
   
   }
 
 public void printResult()
  {
    System.out.println("Printing the Final Result sets");
	int i =1;
    	 for(ArrayList<Integer> al: result)
		  {
		    System.out.println("set "+i++);
                             
 			   for(Integer x: al) 
			        System.out.println(" "+x);
					
					
		  } 
  }
 
 public void print()
  {
    System.out.println("Printing the set ");
  
    for(Integer i: set)
    System.out.println(i);
  
  }
 
 public static void main(String args[])
   {
      Subsets sb = new Subsets();
	   sb.print();
       //sb.findSubsets();
	   
	   sb.recursiveSubs();
	   
	    sb.process();
	   //sb.printResult();
   }


}
