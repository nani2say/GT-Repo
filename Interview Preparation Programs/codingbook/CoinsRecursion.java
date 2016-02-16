//Given an infinite number of quarters (25 cents), dimes (10 cents), nickels (5 cents) and pennies (1 cent),
// write code to calculate the number of ways of representing n cents.


import java.util.*;

public class CoinsRecursion
{

 static int  coinrecurse(int num, int denom)
 {
   int next_den = 0;
   int count =0;
   
   switch(denom)
   {
     case 25: 
	       next_den =10;
		   break; 
    
	
     case 10: 
	       next_den =5;
		   break;
     
     case 5: 
	       next_den =1;
		   break;
		   
     case 1: return 1;		   
   }
   
   for(int i=0; i*denom<=num ;i++)
    {
	  count+= coinrecurse((num -i*denom), next_den);
	}
	
	return count;
 }
 
 public static void main(String args[])
  {
    Scanner sc = new Scanner(System.in);
    System.out.println(" enter the amount in cents ");
    int cents = sc.nextInt();
    System.out.println(coinrecurse(cents,25));
   
  }
}