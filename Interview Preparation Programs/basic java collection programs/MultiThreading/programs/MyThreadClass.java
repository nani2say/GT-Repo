
/*********** Create a thread by extending Thread Class **************/

	class NewThread2 extends Thread
	{
	  String name;
		 
	   NewThread2(String name)
	   {
	      super(name);
	      this.name= name;
	      System.out.println(name+ this);
	   }

	   // This is the entry point for the thread.
	   public void run()
	   {
	      try
	      {
	         for(int i = 5; i > 0; i--)
	         {
	            System.out.println(name+": " + i);
				// Let the thread sleep for a while.
	            Thread.sleep(500);
	          }
	        } 
	      catch (InterruptedException e)
	      {
	         System.out.println(name+" interrupted.");
	      }
	      
	      System.out.println("Exiting thread -"+name);
	   }
	}

	public class  MyThreadClass
	{
	   public static void main(String args[])
	   {
		   NewThread2 t1= new NewThread2("First Child "); // create a new thread
		   NewThread2 t2= new NewThread2("Second Child ");
		   t1.start(); t2.start();
		   
	      try 
	      {
	         for(int i = 5; i > 0; i--)
	         {
	            System.out.println("Main Thread: " + i);
	            Thread.sleep(1000);
	         }
	      } 
	      catch (InterruptedException e) {
	         System.out.println("Main thread interrupted.");
	      }
	      System.out.println("Main thread exiting.");
	   }
	}