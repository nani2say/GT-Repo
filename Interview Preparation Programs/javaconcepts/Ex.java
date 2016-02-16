

class A
{
 void show ()
 {
  System.out.println("Hello from Base class");
 }

}

class B extends A
{
   void show ()
  {
     System.out.println("Hello from Sub class");

   }

  void special()
   {
 
   System.out.println("Special Method in SUB class");
   }


}

public class Ex
{
 public static void main(String args[])
   {
    A aob = new B();
    B bob = (B)aob;  
 
    // aob.show();
    // if(bob instanceof B)
    // bob.special();

    aob = new A();
    aob.show();
 
    }

}