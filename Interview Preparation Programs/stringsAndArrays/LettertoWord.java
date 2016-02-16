//replace 'a' with 'the'


public class LettertoWord
{  
 public static void main(String args[])
 {
   String sentance = "abdaca";   
   StringBuilder sb = new StringBuilder();
   
   char [] carr = sentance.toCharArray();
   
   for(char c:carr)
    {
	 if(c=='a') sb.append("the");
	 else sb.append(c);
	}
	
  System.out.println(sb);
  
 }

}
