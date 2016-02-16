public class SnakeArray
 {
  int N;
  int i=0;
  int j=-1;
  int k=1;
  int [][] arr;
  
  public static void main(String args[])
    { 
	  SnakeArray as = new SnakeArray();
	  as.N = Integer.parseInt(args[0]);	  
	  as.compute();  
       	   
    }
	
	void compute()
	{
	   arr = new int[N][N];
	   int n = N;
	  
	   for(int turns =0; turns <2*(N-1)+1 ;turns++)
	    {
		 // System.out.println("turn num -"+turns);
		   takeTurn(turns%4, n);
		   if(turns%4==3) n=n-2;		
		}  
		
	   for(int i=0;i<N;i++)
        {
           for(int j=0;j<N;j++)
		       System.out.printf(" "+arr[i][j]);
			   
		    System.out.println();	   
          }		
	}
	
  void takeTurn(int flag, int n1)
	 {
        if(flag==0)
         {
            int n =n1;            
			for(int p=0;p<n;p++) arr[i][++j]= k++;
			//System.out.println(" i="+i+" j="+j);
             		  
          }		 
	    if(flag==1)
         {
            int n =n1-1;			
			for(int p=0;p<n;p++) arr[++i][j]= k++;
		//	System.out.println(" i="+i+" j="+j);
             		  
          }
          if(flag==2)
         {
            int n =n1-1;            
			for(int p=0;p<n;p++) arr[i][--j]= k++;
		//	System.out.println(" i="+i+" j="+j);
             		  
          }		 
	    if(flag==3)
         {
            int n =n1-2;            
			for(int p=0;p<n;p++) arr[--i][j]= k++;    
         //   System.out.println(" i="+i+" j="+j);			
          }		 
	  		  	  	  
	   }
	 	 
 }