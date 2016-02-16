public class MergeSort
{


 static void divide(int arr[], int left, int right)
  {  
    if(left>=right) return;
	
	 int mid = (left+right)/2;
	 
	 divide(arr,left,mid);
	 divide(arr,mid+1,right);
	 merge(arr,left,mid+1,right);
	
  }
  
  static void merge(int arr[], int left, int mid, int right)
  {	
  
     int i = left;
	 int j = mid;
	 int arr2[] = new int[right-left+1];
	 int k =0;
	 
	 while(i<mid  && j<=right)
	  {
	       if(arr[i]<arr[j]) arr2[k++] =arr[i++];
            else arr2[k++] =arr[j++];		   
	  }
	 
	 while(i<mid) arr2[k++] =arr[i++];
	 
	 while(j<=right) arr2[k++] =arr[j++];
	 
	 i=left;
	 k=0;
	 
	 while(i<=right)
	 {
	  arr[i] = arr2[k++];
	  i++;
	 }
  }
  
 public static void main(String args[])
 {
    int arr[] = {12,11,9,3,1,4};
    
	MergeSort.divide(arr, 0, arr.length-1);  
 
    for(int i:arr)
    System.out.println(i);
   
 }
} 