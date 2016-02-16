
class Constructor2 
{
  /* Constructor2()
	{
      	System.out.println(" Base class Default : ");
     }
 */	 
	 Constructor2(String s)
	{
      	System.out.println(" Base class Param : "+s);
     } 
}

public class Constructor5 extends Constructor2 
{
  
  /* Constructor5()
	{
      	System.out.println(" Child Class default ");
     }
 */

 Constructor5(String s)
	{
	    super(s);
      	System.out.println("child parameterised :"+s);
     }


	public static void main(String[] args) 
	{
		Constructor5 c = new Constructor5("Hello");
      
	}
}
