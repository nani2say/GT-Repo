
/*****  SYNCHRONIZED  BLOCK ************
 * **********************************
 * Initial Account Balance =1M. 
 * Each of the 3 Threads tries to deposit 50000, So the final balance should be 1.15M.
 * IF you don't use the SYNCHRONIZED  BLOCK in the threads , it will lead to inconsistent access of the 
 * variable -accountBalance.
 * Try the program with and without synchronized block to get the difference.
 **/

class BankAccount2
 {	

    double  accountBalance =1000000;

    public void deposit(double amount) // <------ Synchronized keyword has been removed
       {
          accountBalance = accountBalance + amount;
         }

  }


class NewThread4 extends Thread
{ 
  BankAccount2 acc;	
  String name;
  
   public  NewThread4(String name,BankAccount2 acc)
   {	   
      super(name);
      this.name= name;
      this.acc = acc;

   }

   // This is the entry point for the thread.
   public void run() 
   {
	synchronized(acc)    // <----- This is the synchronized block used when the shared objects class method is not synchronized
	{
     for(int i=0;i<50000;i++)
      acc.deposit(1);
	}
      System.out.println("balance from"+name+" is: "+acc.accountBalance);
   }
}


public class RaceConditionSyncBlock
{

	public static void main(String[] args)
	{
		BankAccount2 ob = new BankAccount2(); 
		
		NewThread4 t1= new NewThread4("First Thread", ob);
		NewThread4 t2= new NewThread4("Second Thread", ob );
		NewThread4 t3= new NewThread4("Third Thread", ob );
       
		t1.start(); t2.start(); t3.start();
		
		//Waiting for the threads to join */
		try{
	        t1.join(); t2.join(); t3.join();
	       }
		catch (Exception e) {}
		 
		
		System.out.println("Final Balance: "+ob.accountBalance);
	}

}
