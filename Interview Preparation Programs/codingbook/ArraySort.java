

public class ArraySort
{
 
 public static void merge(int a[], int b[], int m, int n)
   {
     int k = m+n-1;
	 int i = m-1;
	 int j = n-1;
	 
	 while(i>=0 && j>=0)
	  {
	    if(a[i] >= b[j])
	     a[k--] = a[i--];
		else
		 a[k--]= b[j--];
		 
	  }
	  
	  while(j>=0)
	   {
	     a[k--] = b[j--];
	   }
   
      System.out.println("Printing Result \n");
	  
	  for(int t:a)
	    System.out.println(t);
   } 

 public static void main(String args[])
   {
     int A[] = new int[9];
	 int B[] = new int [4];
	 
	  A[0] = 12; A[1] = 16; A[2] = 19; A[3] = 82; A[4] = 87;
   
      B[0] = 14; B[1] = 18; B[2] = 36; B[3] = 54; 
	  
	  merge(A,B,5,4);
   }
}