
//replace every space in a string with %20

public class StringReplace
{

 public static void main(String args[])
 {
  String key = "How are you?";
  String result ="";
  char [] chars = key.toCharArray();
  
  for(char ch: chars)
     { 
	  if(ch==' ')
	     {
	       result = result+"%20";
	     }
	    
		 else result = result+ch;
	 }
	 System.out.println(result);
  }

}