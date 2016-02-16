

public class SequenceSum
{
  public static int findMax(int arr[])
   {
     int len = arr.length;
	 int max = 0;
	 int sum =0;
	 
	 for(int i =0; i<len ; i++ )
	  {
	    sum+=arr[i];
		
		if(max < sum)
		  max = sum;
		 
        else if(sum <0)
         {
		    sum = 0;
		 }		
	  }

      return max;	  
   }
   
  public static void main(String args[])
   {
     // int [] arr = {2, -8, 3, -2, 4, -10};	 
	 int [] arr2 = {2,3,5,-6,2,1};
	 
	 int max = SequenceSum.findMax(arr2);
	 
     System.out.println(max);
	 
   }
}