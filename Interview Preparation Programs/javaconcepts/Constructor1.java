
class Constructor2 
{
  Constructor2()
	{
      	System.out.println("Hello World!");
     }
}

public class Constructor1 
{

 Constructor1(String s)
	{
      	System.out.println(s);
     }


	public static void main(String[] args) 
	{
		// Constructor1 c1 = new Constructor1();
        Constructor1 c2 = new Constructor1("Hello from Argumented Contructor");
	}
}
