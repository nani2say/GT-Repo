
// Print all valid phone numbers of length n subject to following constraints: 

// 1.If a number contains a 4, it should start with 4 
// 2.No two consecutive digits can be same 
// 3.Three digits (e.g. 7,2,9) will be entirely disallowed, take as input
// http://www.careercup.com/question?id=15190680

import java.util.*;

public class PhonenumGenerate
{
 static List<Integer> list; 

 public static boolean validate(long num, int len)
  {
    long div = (long) Math.pow(10, len-1);
    int d1 =  (int)(num/div); 
    boolean four_ok = false;
	
	
	if(d1 == 4) four_ok = true;	
    
	int prev =-1;
	
	while(num >0)
	 {
	   int dig_num = (int)num%10;
	   
	   if(dig_num==4 && four_ok==false)  return false;
	   
	   if(list.contains(dig_num)) return false;
       	
	   if(prev == dig_num) return false;
		
		prev = dig_num;
	 
	   	num/=10;		
	 }
	
   return true;
  }

 public static void main(String args[])
  {     
  
    PhonenumGenerate.list = new LinkedList<Integer>();
	list.add(7); list.add(2); list.add(9);
	
	Scanner sc = new Scanner(System.in); 
    System.out.println(" enter number of digits ");
    int digit = sc.nextInt(); 
    
	long start = (long)Math.pow(10,digit-1);
	long end =  (long)Math.pow(10,digit) -1;
	 
	System.out.println(" start ="+start+" end = "+end); 
  
    for(long i = start; i<=end; i++)
	{
	  if(PhonenumGenerate.validate(i,digit))
	  {
	    System.out.println(i);
	  }
	
	} 
    
	//to convert a num to string and then toCharArray will make things easy 
	//int x =123;
    //String s= String.valueOf(x);
	//System.out.println(s);
  }
}