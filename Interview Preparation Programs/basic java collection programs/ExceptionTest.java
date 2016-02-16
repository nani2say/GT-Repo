

public class ExceptionTest
{
 
   public void method() throws NullPointerException
    {
      throw new NullPointerException();	
	  
	  //System.out.println("Hi");
 	}
	
 public static void main(String args[])
	 {
	  ExceptionTest ex = new ExceptionTest();
	  
	  try
	  {
	    ex.method();
	  }
	  catch(  NullPointerException npe)
	  { System.out.println("Exceptionnn");}
	  
	 }

}