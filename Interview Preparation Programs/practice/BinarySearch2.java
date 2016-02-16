
public class BinarySearch2
{

 public static boolean binary(int []arr, int key )
 {
  int left =0, right = arr.length-1;
  int mid;
  
  while(left<=right)
   {
      mid = (left+right)/2;
        
      if(arr[mid] == key) return true; 		
       
      else if(key < arr[mid] ) right = mid - 1;
      else if(key >	arr[mid] ) left  = mid + 1;    
	  
   }
  
  return false;
  
 }

 public static void main(String args[])
  {
     int arr[] = {2,3,5,7,8,9};     
      
     System.out.println(binary(arr, 9)); 	  
	
   }
  
}