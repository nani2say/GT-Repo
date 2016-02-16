
/*********  Create a thread by implementing Runnable Interface *******/

class NewThread implements Runnable
{
  String name;	
  Thread t;
  NewThread(String name)
  {
   // Create a new  thread
   t = new Thread(this, name);
   this.name= name;
   System.out.println(name + t); 
  
   // Start the thread
   t.start(); 
  }

// This is the entry point for the second thread.
public void run()
{
   try {
      for(int i = 5; i > 0; i--)
        {
          System.out.println(name+":" + i);
         
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

public class MyThreadRunnable
{
 public static void main(String args[])
  {
	 NewThread t1= new NewThread("First Child "); // create a new thread
	 NewThread t2 = new NewThread("Second Child ");
	 
   try {
      for(int i = 5; i > 0; i--)
           {
        System.out.println("Main Thread: " + i);
        Thread.sleep(1000);
           }
       }
   
   catch (InterruptedException e)
     {
      System.out.println("Main thread interrupted.");
      }
   
     System.out.println("Main thread exiting.");
   }
}