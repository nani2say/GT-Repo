
public class Stars1
 {
  public static void main(String args[])
   {
        int n = Integer.parseInt(args[0]);

        int spaces = n;
		
		for(int i=1;i<=n;i++)
		  {
		    spaces--;
			for(int j=1;j<=spaces;j++)  System.out.printf(" ");
			
			for(int j=1;j<=2*i-1;j++) System.out.printf("*");
			
			System.out.println();
		  	  
		  }
       		
    }
 }