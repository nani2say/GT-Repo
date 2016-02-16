

public class SentanceRev
{
  public static void main(String args[])
    {
	  String sentence ="This is Fox";
	  String[] words = sentence.split(" ");
	  
     for (String word : words)
	 {
       System.out.print(new StringBuilder(word).reverse() + " ");
     }
	}

}