
/***  Producer Consumer Problem *******/

/* wait() tells the calling thread to give up the monitor and go to sleep until some other thread 
 * enters the same monitor  and calls notify( ).
 * notify() wakes up the first thread that called wait() on the same object.
 * notifyAll() wakes all threads in the waiting set.
 */

public class ProducerConsumerTest {
   public static void main(String[] args) {
      Widgets c = new Widgets();
      Producer p1 = new Producer(c, 1);
      Consumer c1 = new Consumer(c, 1);
      p1.start(); 
      c1.start();
   }
}
class Widgets {
   private int contents;
   private boolean available = false;
   public synchronized int get() 
   {
      while (available == false) {
         try
         {
            wait();
         }
         catch (InterruptedException e) {
         }
      }
      available = false;
      notifyAll();
      return contents;
   }
   
   public synchronized void put(int value) {
      while (available == true)
      {
         try {
            wait();
         }
         catch (InterruptedException e) { 
         } 
      }
      contents = value;
      available = true;
      notifyAll();
   }
}

class Consumer extends Thread
{
   private Widgets widgets;
   private int number;
   public Consumer(Widgets c, int number)
   {
      widgets = c;
      this.number = number;
   }
   public void run()
   {
      int value = 0;
         for (int i = 0; i < 10; i++)
         {
            value = widgets.get();
            System.out.println("Consumer #" + this.number + " got: " + value);
           
            try
            {
             sleep(1500);
             } 
           catch (InterruptedException e)
             { }
            
         }
     }
}

class Producer extends Thread
{
private Widgets widgets;
private int number;

public Producer(Widgets c, int number) {
widgets = c;
this.number = number;
}

public void run()
{
for (int i = 0; i < 10; i++)
 {
   widgets.put(i);
   System.out.println("Producer #" + this.number+ " put: " + i);

   try
    {
     sleep(1000);
     } 
   catch (InterruptedException e)
     { }
   }
  }
}