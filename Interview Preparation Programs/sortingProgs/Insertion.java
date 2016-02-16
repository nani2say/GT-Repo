public class Insertion
{

 static void insertSort(int arr[])
  {
   int size = arr.length;
  
   for(int i=1; i<size ;i++)
    {
      int j= i;	
	  
	  while(j>0 &&arr[j] < arr[j-1])
      {
	    int temp = arr[j];
	    arr[j] = arr[j-1];	 
		arr[j-1] = temp;
		
		j--;
	  }	  	
	}   
  }
  
 public static void main(String args[])
 {
    int arr[] = {12,11,9,3,1,4};
    
	Insertion.insertSort(arr);  
 
    for(int i:arr)
    System.out.println(i);
   
 }
} 