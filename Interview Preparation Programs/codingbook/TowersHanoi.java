import java.util.*;

public class TowersHanoi
{
  int n =5;
  
  Stack<Integer> stk = new Stack<Integer> ();

  public void moveTop(TowersHanoi dest)
     {
	   int x = stk.pop();
	   dest.stk.push(x); 	  	   
	 }  
 
   public void movePlates(int n,TowersHanoi buff, TowersHanoi dest)
    { 
	  if(n>0)
	  {
	   movePlates(n-1,dest, buff);
       moveTop(dest);    
       buff.movePlates(n-1 ,this, dest);	 
	  }
	}
	
 public static void main(String args[])
   {
     TowersHanoi[] th = new TowersHanoi[3];
     
	 for(int i =0;i<3 ;i++)
	 th[i] = new TowersHanoi();
	 
	 ///initialize tower0
	 th[0].stk.push(4); th[0].stk.push(3); th[0].stk.push(2); th[0].stk.push(1);   th[0].stk.push(0);
	  
	  System.out.println(th[0].stk);
	  
	  //Main procedure call
      th[0].movePlates(5,th[1],th[2]);	 
   
      System.out.println(th[2].stk);
	  System.out.println(th[2].stk.peek());
	  
	  //trash
	  Double d = 3.14;
	  int x = (int)d;
	  
   }
  
}