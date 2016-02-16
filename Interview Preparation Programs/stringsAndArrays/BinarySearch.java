
public class BinarySearch
{

 public static boolean binarySearch(int [] arr, int left, int right, int key)
  {
    boolean result;
	
    if(right<left) return false;  
  
    int mid = (left+right)/2;
   	
	if(arr[mid] == key) return true;
	 
	 else if( key < arr[mid] )
	  {
	   right = mid-1;
	  }
	  
	 else if( key > arr[mid] )
	  {
	   left = mid+1;
	  }
    	
	result =  binarySearch(arr,  left,  right, key);
	  
	return result;  
  }

 public static void main(String args[])
   {
	 int [] arr2 = {2,3,5,7,9,11};
	 
	 System.out.println(BinarySearch.binarySearch(arr2,  0, 5,3 ));
	}
}