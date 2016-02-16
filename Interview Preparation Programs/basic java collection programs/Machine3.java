
import java.util.*;
import java.math.BigDecimal;


 public class Machine3
 {
    public static void main(String args[])
	 {
	   int sigma = 1;
	   double u1=12.5188;
	   double u2=12.5190;
	   
	   double den1;
	   double res2;
	   
	   double Asum1=0;  double Asum2=0; double Bsum1 =0; double Bsum2 =0;
	   
       ArrayList<Double> arrx = new ArrayList<Double>();
	   
	   arrx.add(9.3843);  arrx.add(15.835); arrx.add(15.291); arrx.add(8.5977); arrx.add(16.419); 
	    arrx.add(9.8075); arrx.add(15.215); arrx.add(16.587); arrx.add(10.488); arrx.add(15.696);
		
		 arrx.add(10.748); arrx.add(14.756); arrx.add(15.197); arrx.add(8.5776); arrx.add(9.2351);
		  arrx.add(10.888); arrx.add(13.834); arrx.add(14.195); arrx.add(9.8226); arrx.add(9.8039);
	   
	   for( double x1:arrx)
	  { 
	   	   
	   Double A =-(x1-u1)/2.0;
	   Double num= Math.exp(A);//Math.E
	   
	   //String str=  BigDecimal.valueOf(num).toPlainString();	   
	  // num = Double.parseDouble(str);	   
	   
	   Double B =-(x1-u2)/2.0;
	   Double den= Math.exp(B);  
	   
	 //  System.out.println("\n A ="+A+" B ="+B+" Math.e="+Math.E);
	   
	//    System.out.println("\n num = "+num+" den="+den);
	   
	    den1 = den+num;
	   
	//   System.out.println("\n num = "+num+" den="+den);
	   
	     Double res1= num/den1;
	   //  System.out.println("\n Result 1 Ez,i = "+res1);	   
	    // System.out.println("\n Result 1 E(z,i)*xi = "+res1*x1);
		 //System.out.println("\n ---------");
		
		 res2=den/den1; //opp
	     
		 //	System.out.println("Result 2 Ez,i = "+res2);
		// System.out.println("\n Result 1 E(z,i)*xi = "+res2*x1);
		
		Asum1+=res1; 
		Asum2+=res1*x1;
		
		Bsum1+=res2; 
		Bsum2+=res2*x1;
			   
        }	
   
       System.out.println(" u1 sum  Ez,i= "+Asum1);
	   
       System.out.println(" u1 sum  Ez,i *xi= "+Asum2);
	   
       System.out.println(" u2 sum  Ez,i= "+Bsum1);
	   
       System.out.println(" u2 sum  Ez,i * xi= "+Bsum2);
		
	 }
 }