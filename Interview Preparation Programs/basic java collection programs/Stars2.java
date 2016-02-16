public class Stars2
 {
  public static void main(String args[])
   {
        int n = Integer.parseInt(args[0]);

        int spaces = 1;
		
	      for(int i=n; i>=1;i--)
		   {
		     for(int j=1;j<=spaces;j++) System.out.printf(" ");
		   
		     for(int j=1;j<=2*i-1;j++) System.out.printf("*");
			 
		     spaces++;
			 System.out.printf("\n");
		   }
       		
    }
 }