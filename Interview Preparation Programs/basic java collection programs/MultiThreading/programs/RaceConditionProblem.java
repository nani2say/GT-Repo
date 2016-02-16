
/*****  SYNCHRONIZED  Methods ************
 * **********************************
 * Initial Account Balance =1M. 
 * Each of the 3 Threads tries to deposit 50000, So the final balance should be 1.15M.
 * IF you don't use the synchronized keyword before the method it will lead to inconsistent access of the 
 * variable -accountBalance.
 * Try the program with and without synchronized keyword to get the difference.
 **/

class BankAccount
 {	

    double accountBalance =1000000; // 1M

    public void deposit(double amount) // <------ Try the program removing the synchronized keyword key
        {
          accountBalance = accountBalance + amount;
         }

  }


class NewThread3 extends Thread
{ 
  BankAccount acc;	
  String name;
  
   public  NewThread3(String name,BankAccount acc)
   {	   
      super(name);
      this.name= name;
      this.acc = acc;

   }

   // This is the entry point for the thread.
   public void run()
   {
     for(int i=0;i<50000;i++)
      acc.deposit(1);
     
      System.out.println("balance from"+name+" is: "+acc.accountBalance);
   }
}


public class RaceConditionProblem
{

	public static void main(String[] args)
	{
		BankAccount ob = new BankAccount(); 
		
		NewThread3 t1= new NewThread3("First Thread", ob);
		NewThread3 t2= new NewThread3("Second Thread", ob );
		NewThread3 t3= new NewThread3("Third Thread", ob );
       
		t1.start(); t2.start(); t3.start();
		
		//Waiting for the threads to join */
		try{
	        t1.join(); t2.join(); t3.join();
	       }
		catch (Exception e) {}
		 
		
		System.out.println("Final Balance: "+ob.accountBalance);
	}

}
