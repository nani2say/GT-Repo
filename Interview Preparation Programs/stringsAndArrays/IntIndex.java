 // You have a single string which contains all the positive numbers upto N concatenated together.
  //If you are given an input number then how would you find the index position of the number in the string.
  //Ex: String str = "12345678910111213141516171819202122232425......upto 10000";
  //input = 20 should return the index of 20 in the string which is 29 
  
  public class IntIndex
  {
    
	public static void main(String args[])
    {
          String str = "12345678910111213141516171819202122232425";
         
          int input = 20;
           String ins = 20+"";
		   
           int len = ins.length() ;          
 
           //System.out.println(len);
 		    int index = 0;
		    int i;
			
            for( i= 0; i<len-1; i++)
              index+= (int)(9*(i+1)*Math.pow(10,i));  //i+1 indicates number of chars for that i+1 digited number
			 
            //i comes out as len-1			 
			//System.out.println(index);
               
            int temp1 =  (int)Math.pow(10,i);
            int temp2 = input - temp1; //say 20 -10  =10,  2 digit numbers before 20			 
            temp2 = temp2*len;  //(since they take up len number ochar for each number)
			
             index+= temp2;
			 
			 System.out.println(index); //This is the final index when a string starts with zero index
			 
			 //this is irrelevant stuff
			 // String t = "Hello";
			 // System.out.println(t.indexOf('e'));
	         // System.out.println(t.substring(1));
	}

  }