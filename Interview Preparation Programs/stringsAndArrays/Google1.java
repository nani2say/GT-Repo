
import java.util.*;

public class Google1
{
 
 static int func(int [] arr) 
{
  if(arr == null) 
 {
  System.out.println("array null");
   return -1;
 }
 
  if(arr.length ==0) return 0; 
 
  Arrays.sort(arr); //O(nlogn)
 
 for(int j:arr)
 System.out.println(j);
 
 
 int count =1;
 int max =0;
 
for(int i=0;i<arr.length-1 ;i++) //O(n)
{
  if(arr[i] == arr[i+1]) continue;

  if(arr[i]+1  ==  arr[i+1]) 
    count++;

   else  count =1;
 
  if(max<count) max = count;

  System.out.println(" max ="+max+" count ="+count);
  
  } //for 
 
return max;

}//func

public static void main(String args[])
 {
   int arr[] = {3, 1, 5, 4, 8, 7};
   System.out.println(Google1.func(arr)); 
  }
}