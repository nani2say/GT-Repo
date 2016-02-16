public class QuickSort
{

 static void quickSort(int arr[], int left, int right)
  {  
    if(left>=right) return;
 
    int piv = left;
    int i = left; 	
    int j = right;
	
	while(i<j)
	 {
	   while(arr[i]<arr[piv] && i< right) i++;
	   
	   while(arr[j] >arr[piv]) j--;
	  
	   if(i<j)
	    {
		  int temp = arr[j]; arr[j] = arr[i]; arr[i] = temp;		
		}	 
	 }
   
     //swap j , piv
      int temp = arr[j];
	  arr[j] = arr[piv];
	  arr[piv] = temp;
      
	  quickSort(arr,left,j-1);
	  quickSort(arr,j+1, right);
      	  
  }
  
 public static void main(String args[])
 {
    int arr[] = {14,6,12,2,7,11,9,3,1,0};
    
	QuickSort.quickSort(arr, 0, arr.length-1);  
 
    for(int i:arr)
    System.out.println(i);
   
 }
} 