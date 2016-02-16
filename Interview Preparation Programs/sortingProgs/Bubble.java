

public class Bubble
{

 static void bubble(int arr[])
  {
   int size = arr.length;
   
   for(int i=0; i<size-1 ;i++)
    {
	  for(int j=0;j<size-i-1;j++)
	     {
		  if(arr[j]>arr[j+1]) 
		    {
			  int temp = arr[j+1];
			  arr[j+1] =arr[j];
			  arr[j] = temp;
			}
		 
		 }
	}
  }
  
 public static void main(String args[])
 {
    int arr[] = {12,11,9,3,1,4};
    
	Bubble.bubble(arr);  
 
    for(int i:arr)
    System.out.println(i);
   
 }
} 