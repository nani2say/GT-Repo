
class Constructor2 
{
 /*   Constructor2()
	{
      	System.out.println(" Base Class ");
     }
	  */
Constructor2(String s)
	{
      	System.out.println("Base "+s);
     }
	  
}

public class Constructor3 extends Constructor2 
{
  
 /*  Constructor3()
	{
      	System.out.println(" Child Class ");
     }
  */

 Constructor3(String s)
	{
      	System.out.println(s);
     }


	public static void main(String[] args) 
	{
		Constructor3 c = new Constructor3("Hello from Clild Arg Constructor");
        // Constructor1 c2 = new Constructor1("Hello from Argumented Contructor");
	}
}
