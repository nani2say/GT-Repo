
public class Subtract
 {
  public static void main(String args[])
   {
     int [] arr1 = {1,2,5,7,5} ;
	 int [] arr2 = {3,4,8,9} ;
     int [] res= new int[arr1.length];  
	  
      int i= arr1.length-1;
	  int k=0;
	  
	  for(int j= arr2.length-1; j>=0;j--)
	     {
		    if(arr1[i] < arr2[j])
       			  getSub(arr1,i);
             
			 System.out.println("arr1[i] ="+arr1[i]+"arr2[j]="+arr2[j]);
                 res[k++]=arr1[i--]-arr2[j];				 
          }			   	  
	  
	  //Reverse the list
	  for(int p = 0; p < k / 2; p++) 
     	  {
		     int temp = res[p];
			 res[p]=res[k-1-p];
			 res[k-1-p] = temp;
		  }
	  
	  System.out.println("\n Resultant array ");
     for( int p=0; p<k;p++)
          System.out.println(res[p]);		
		
    }

    static void getSub(int arr1[] , int i)
        {
	      if(i-1==0 && arr1[i-1] ==0) 
		        {
				  System.out.println("Not possible");
				  System.exit(0);
				   
				}
		  else if(arr1[i-1]> 0)
                  {
				    arr1[i] = arr1[i]+10;
					arr1[i-1]--;
					return;
				  }	
           else 
		   {
		   getSub(arr1,i-1);	

		   arr1[i] = arr1[i]+10;
					arr1[i-1]--;
					return;
   
           }
        }
   }