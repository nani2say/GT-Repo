public class Selection
{

 static void selectSort(int arr[])
  {
   int size = arr.length;
   
   for(int i=0; i<size-1 ;i++)
    {
	  for(int j=i+1;j<size;j++)
	     {
		  if(arr[i]>arr[j]) 
		    {
			  int temp = arr[j];
			  arr[j] =arr[i];
			  arr[i] = temp;
			}
		 
		 }
	}
  }
  
 public static void main(String args[])
 {
    int arr[] = {12,11,9,3,1,4};
    
	Selection.selectSort(arr);  
 
    for(int i:arr)
    System.out.println(i);
   
 }
} 