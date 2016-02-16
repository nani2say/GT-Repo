
public class Check<T extends Comparable<T>>
{
  T data;
  
 private class Test<T>
  {
    T var1;
	T var2;
  }

  void test(T x1, T x2)
    {
	  if(x1 > x2) System.out.println("True");
	}
  
   public static void main(String args[])
    {
	  Check<Integer> ck = new Check<Integer>();
	   ck.test(12,13);
	   
	}
	
	public int compareTo(T a, T b)
	{
	 return (a-b);
	}

}