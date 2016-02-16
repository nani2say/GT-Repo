class A
{
  static void showstatic()
  {
      System.out.println("A static Meth ");
   }

  void show ()
   {
      System.out.println("A instance Meth ");
      }

}

class B extends A
{
  static void showstatic()
  {
      System.out.println("B static Meth ");
   }

  void show (int x)
   {
      System.out.println("B instance Meth ");
     // return 3;   
   }

}

public class Ex2
{
  public static void main (String args[])
   {
       A aob = new B();
       aob.showstatic();
    
       aob.show();          

   }


} 