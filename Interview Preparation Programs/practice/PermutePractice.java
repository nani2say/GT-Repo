

public class PermutePractice
{
  static String   input = "abcd";
  static StringBuilder output = new StringBuilder();
  static boolean[] used = new boolean[input.length()];  
  
  public static void permute()
  { 
    if(output.length() == input.length())
	  {
	     System.out.println(output);
         return;		 
	  }
	  
	 for(int i=0; i< input.length(); i++)
      {
	     if(used[i] == true) continue;
		 
		 used[i] =true;
		 output.append(input.charAt(i));
	     
		 permute();
		 used[i] = false;
		 
		 output.setLength(output.length()-1);
	  }	 
  }

 public static void main(String args[])
 { 
  permute();
 
 }

}