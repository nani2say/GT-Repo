

public class BinarySearch
{
  public static boolean search(int arr[], int left, int right, int key)
    {
	  if(left>right) return false;
	  
	  int mid = (left+right)/2;
	 
	  if(arr[mid] == key) return true;
	  
	  if(arr[mid] > key) return search(arr, left, mid-1, key);
	  
      else return search(arr,mid+1,right,key); 	  
	  
	}
   
 public static void main(String args[])
   {
     int [] arr = {3,7,9,12,18,24};
     
     System.out.println(search(arr,0,arr.length-1,18)); 	 
   
   }
}