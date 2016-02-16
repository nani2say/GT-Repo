
//program to find the subsequence in a given set of integers
// input {7,5,2,3,4,9,1,6,12} sub sequence {3,4,9} 

public class SubSequence
{ 

//will return -1 if not found and index of starting number if found

 static int bruteforce(int [] input , int [] subseq)
 {    
      int size = input.length;
      int sub = subseq.length;
	  
	  for(int i =0; i< size -sub+1; i++)
	   {
	     if(input[i] == subseq[0])
		  {
		    int k = i;
			int j;
			
		    for(j=0; j<sub ;j++)
			 {
			   if(input[k++]!=subseq[j]) break;			 
			 }		  
		    
            if(j == sub) return i; //the starting pos of seq 			
		    
		  }
	    
	   }
	 
    return -1;	 
	  
 }

  public static void main(String args[])
   {
    int input[] = {7,5,2,3,4,9,1,6,12};
    int sbseq [] = {9,1,6};   
     
	System.out.println(SubSequence.bruteforce(input, sbseq)); 
	
   }
}