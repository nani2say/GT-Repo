
import java.util.*;
import java.math.BigDecimal;


 public class Machine
 {
    public static void main(String args[])
	 {
	   int sigma = 1;
	   Float u1=2;
	   Float u2=6;
	   
	   Float den1;
	   Float res2;
	   
	   Float x1 = Float.parseFloat(args[0]);
	   
	   
	   
	   
	   Float A =-(x1-u1)/2.0;
	   Float num= Math.exp(A);//Math.E
	   
	   //String str=  BigDecimal.valueOf(num).toPlainString();	   
	  // num = Float.parseFloat(str);	   
	   
	   Float B =-(x1-u2)/2.0;
	   Float den= Math.exp(B);  
	   
	 //  System.out.println("\n A ="+A+" B ="+B+" Math.e="+Math.E);
	   
	//    System.out.println("\n num = "+num+" den="+den);
	   
	    den1 = den+num;
	   
	//   System.out.println("\n num = "+num+" den="+den);
	   
	    Float res1= num/den1;
	    System.out.println("\n Result 1 Ez,i = "+res1);	   
	    System.out.println("\n Result 1 E(z,i)*xi = "+res1*x1);
		 System.out.println("\n ---------");
		
		 res2=den/den1; //opp
	//	 System.out.println("Result 2 Ez,i = "+res2);
		// System.out.println("\n Result 1 E(z,i)*xi = "+res2*x1);
			   
          	 
	 }
 }