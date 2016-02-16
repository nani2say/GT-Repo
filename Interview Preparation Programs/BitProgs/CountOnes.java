

public class CountOnes 
{
  static countOne(int value)
  {
    int count = 0;
   
   while (value > 0) 
   {           // until all bits are zero
        if ((value & 1) == 1)     // check lower bit
            count++;
			
        value >>= 1;              // shift bits, removing lower bit
   }
    
	return count;        
  }
   
  public static void main(String args[])
   {
       int num = 9;
	   CountOnes.countOne(num); 
   }
   

}