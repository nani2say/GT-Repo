
import java.util.*;
import java.math.BigDecimal;


 public class Machine2
 {
    public static void main(String args[])
	 {
	   ArrayList<Float> a1 = new ArrayList<Float>();
	   ArrayList<Float> train1 = new ArrayList<Float>();
	   ArrayList<Float> result = new ArrayList<Float>();
	   
	   
	   Float temp;
	   Scanner sc = new Scanner(System.in);
	   
	   a1.add(1.6f);a1.add(2.6f);a1.add(3.1f);a1.add(3.5f);a1.add(1.3f);a1.add(2.7f);a1.add(2.6f);
	   
	   System.out.println("Entert the training data ..");
	   for(int i=0;i<7;i++)
	    { 
	    temp = sc.nextFloat();
		train1.add(temp);
	    }
     		
	  for(int i=0;i<7;i++)
	    { 
	       temp = a1.get(i) - train1.get(i);
           temp = temp*temp;
           
           result.add(temp); 		   
    	  }
	
	 Float sum =0.0f;
	
	  for(Float d:result)
          {
		    sum+=d;
		  }	  
        
       sum =1/sum;

       System.out.println("Final Sum = " +sum);	   
	 }
 }