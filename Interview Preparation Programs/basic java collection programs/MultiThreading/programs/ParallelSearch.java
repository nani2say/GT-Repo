import java.util.*;

class NewThread5 extends Thread
	{
	   ParallelSearch  obj;
	   int threadID;
	   
	   NewThread5(ParallelSearch obj, int threadID)
	   {
	        this.obj = obj;
	        this.threadID = threadID;
	   }
	   
	  public void run()
	  {
		  roundRobinSearch();
	  }
	  
	  void roundRobinSearch()
	   {
			for(int i= threadID; i< obj.myArray.length ;i= i + obj.NO_OF_THREADS)  
			{
				if(obj.key ==  obj.myArray[i])  obj.result1 =true;
			}
		  
	   }
	  
	}

public class ParallelSearch
{ 
	boolean result1 = false;
	
	int[] myArray = {8,7,4,3,3,22,45,18,19,12,23,15,2,41,33,14};
	int NO_OF_THREADS =4;
	int key;
	
	public static void main(String[] args)
	{
		ParallelSearch pobj = new ParallelSearch();
		
		NewThread5 threads[] = new NewThread5[pobj.NO_OF_THREADS];
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the key to be searched ");
		pobj.key = sc.nextInt();
		
		for(int i=0;i< pobj.NO_OF_THREADS;i++)
		 {
			threads[i] = new NewThread5(pobj,i);		
			threads[i].start();
		 }
		
		//Waiting for the threads to join */
		try{
			for(int i=0;i< pobj.NO_OF_THREADS;i++)
				threads[i].join();
	       }
		
		catch (Exception e) {}
		 		
		System.out.println("Result from Round Robin Search=>  Key Found :"+pobj.result1);
		
	}

}
