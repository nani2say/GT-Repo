public class StarDiamond
 {
  public static void main(String args[])
   {
        int n = Integer.parseInt(args[0]);
           
		  star1(n);
         star2(n-1);    		        		
    }
	
	static void star1(int n)
	 {
	  int spaces = n;
		
		for(int i=1;i<=n;i++)
		  {
		    spaces--;
			for(int j=1;j<=spaces;j++)  System.out.printf(" ");
			
			for(int j=1;j<=2*i-1;j++) System.out.printf("*");
			
			System.out.println();
		  	  
		  }
       		
	 }
	 
	 static void star2(int n)
	 {
	   int spaces = 0;
		
	      for(int i=n; i>=1;i--)
		   {
		     for(int j=1;j<=spaces;j++) System.out.printf(" ");
		   
		     for(int j=1;j<=2*i-1;j++) System.out.printf("*");
			 
		     spaces++;
			 System.out.printf("\n");
		   }
	 
	 }	
 }