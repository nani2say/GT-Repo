import java.util.*;

// replace a,e,i,o,u with A,E,I,O,U 
// at most four eligible letters from the rear of the string are replaced 
// The first three eligible letters in the string are always exempted from replacement.

public class ReplaceVowels
{
 public static void main(String args[])
 {
   Map<Character,Character> hm = new HashMap<Character,Character>();
   hm.put('a','A'); hm.put('e','E'); hm.put('i','I'); hm.put('o','O'); hm.put('u','U');
 
   String str = "abicodaeiopusmoaIUe";
   
   char [] carr = str.toCharArray();
     
   int end = -1;
   int count =0;
   
   int i;
   
   for(i=0; i<carr.length; i++)
    {
	  char c = carr[i];
	  
	  if(c=='a' ||c=='e' || c=='i' ||c=='o' ||c=='u')  count++;
	  
	  if(count ==3) break;	  
	}   
   
   if( i==carr.length )
    { 
	  System.out.println(" not sufficient vowels found to replace the string ");
      System.exit(0);
	 }
   else
   {
    end = i;
   }   
  
  count =0;
  
  for( int start = carr.length-1; start>=end; start --)
  {
     char c = carr[start];
     
	 if(c=='a' ||c=='e' || c=='i' ||c=='o' ||c=='u')
	 {
	   count ++;
	   
	   char rep = hm.get(c);
	   carr[start] = rep;
	 }
	 
	 if(count>=4) break;
  }
    
  System.out.println(new String(carr));
  
 }
}