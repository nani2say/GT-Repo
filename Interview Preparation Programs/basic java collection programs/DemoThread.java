
class MyThread extends Thread
{
 MyThread(String s)
   {
    super(s);
   }
 public void run()
  {
   System.out.println("Hello from Thread"+ this);
  }

}

class Thread2 implements Runnable
{
  public void run()
  {
   System.out.println("Hi from Thread"+ super);
  }
}

public class DemoThread
{
 public static void main(String args[])
   {
      MyThread mt = new MyThread("T1");
	  mt.start();
	  
	  Thread t2 = new Thread(new Thread2(), "T2");
       t2.start();	  
   }
}