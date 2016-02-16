
class Constructor2 
{
    Constructor2()
	{
      	System.out.println(" Base Class ");
     }
	  
}

public class Constructor4 extends Constructor2 
{
  
   Constructor4()
	{
      	System.out.println(" Child Class ");
     }
  

	public static void main(String[] args) 
	{
		Constructor4 c = new Constructor4();
        // Constructor1 c2 = new Constructor1("Hello from Argumented Contructor");
	}
}
