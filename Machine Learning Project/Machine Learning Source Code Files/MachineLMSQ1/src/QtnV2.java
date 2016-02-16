/* this is the better algorithm with an extra constant in 
http://www.csee.umbc.edu/~squire/cs455_l4.html
only 1st row is done for testing
*/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import java.io.FileInputStream;
import java.math.BigDecimal;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


class ArrayIndexComparator implements Comparator<Integer>
{
    private final Double[] array;

    public ArrayIndexComparator(Double[] array)
    {
        this.array = array;
    }

    public Integer[] createIndexArray()
    {
        Integer[] indexes = new Integer[array.length];
        for (int i = 0; i < array.length; i++)
        {
            indexes[i] = i; // Autoboxing
        }
        return indexes;
    }

    @Override
    public int compare(Integer index1, Integer index2)
    {
         // Autounbox from Integer to int to use as array indexes      	
    	return array[index1].compareTo(array[index2]);
    }
}

public class QtnV2 implements Serializable
{
	int NUM_OF_COLUMNS =14;  //14 , 50
	int NUM_OF_ROWS = 242;  //242 . 758
	int k;
	int trsize;
	int tcsize;
	int newsize ; //used for the extra contsant
	 
	double coeff[][] ;// new double[NUM_OF_COLUMNS][NUM_OF_ROWS]; //temp array for calculations
	double actual[] ; //new double[NUM_OF_COLUMNS];
	double coeff2[][];
	double actual2[] ;
	double sol[];
	
	ArrayList<RowData> myData = new ArrayList<RowData>();
	ArrayList<RowData> orData = new ArrayList<RowData>();
	ArrayList<RowData> serData = new ArrayList<RowData>();
	ArrayList<RowData> testData = new ArrayList<RowData>();
	
	static ArrayList<Double> errList = new ArrayList<Double>();
	static ArrayList<Double> temperrList = new ArrayList<Double>();

	HSSFWorkbook workbook;
				
	class RowData implements Serializable
	{
	 
	  ArrayList<Double> cellData = new ArrayList<Double> ();	
	  double avg; 
	  Double dist[] = new Double[NUM_OF_ROWS];
	  Double sorteddist[] = new Double[NUM_OF_ROWS];
	  int topk[] = new int[k];
	
	  boolean hasmissing()
		{
		  
		  for(double d: cellData)
			{
				
			  if(d==1.0E99)
			  {
				return true;  
			  }
			}
		
		  return false;
		}		
	}

	void write()
	{
		 try
	      {
	         FileOutputStream fileOut =new FileOutputStream("orObj.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject( myData);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in orObj.ser");
	      }
		 catch(IOException i)
	      {
	          i.printStackTrace();
	      }
		
	}
	
	void readSerObj() //reading the ORIGINAL values into serData
	{
		
		try
	      {
	         FileInputStream fileIn = new FileInputStream("orObj.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         serData = (ArrayList<RowData>) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(Exception i)
	      {
	         i.printStackTrace();
	         return;
	      }
	}
	
	void read()
	{
		int sheetIndex = 0;
		XSSFSheet sheet =null;
	
		try
		{
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream( new File("Q1S1.xlsx")));
		
		sheet= workbook.getSheetAt(sheetIndex);
		
		}
		catch(Exception e){ e.printStackTrace();}

		int rows=0; 
		for (Row tempRow : sheet)
		{
		 
			RowData rowData = new RowData();
			RowData row2Data = new RowData();
		    // print out the first three columns
			
		    for (int column = 0; column < NUM_OF_COLUMNS ; column++) {
		        Cell tempCell = tempRow.getCell(column);
		 
		        if (tempCell.getCellType() == Cell.CELL_TYPE_NUMERIC)
		        {
		        	rowData.cellData.add(tempCell.getNumericCellValue());
		        	row2Data.cellData.add(tempCell.getNumericCellValue());
		        	// System.out.print(tempCell.getNumericCellValue() + "  ");
		        }
		    }
		    myData.add(rowData);
		    orData.add(row2Data);
		   // System.out.println();
		   rows++;
		  if(rows==NUM_OF_ROWS) break;
		
		}
		
	//	 System.out.println("\n Number of Row ="+rows);
	 }
	
	void print()
	{
		System.out.println("Printing the data \n");
		
		for(RowData ob:myData)
		{
			for(Double var: ob.cellData)
			 System.out.print(var+"\t");
				
				System.out.println();	
		}
		
	}
	
	void printOr()
	{
	//	System.out.println("Printing the Original data \n");
		
		for(RowData ob:orData)
		{
			for(Double var: ob.cellData)
			 System.out.print(var+"\t");
				
				System.out.println();	
		}
		
	}
	
	void doAverage()
	{
		for(RowData ob:myData)
		{  
		    double sum=0.0;	
			double count =0;
			
		    for(Double var: ob.cellData)
		    {  
		    	if(var!=1.0E99)
			     {
		    		sum+=var; 
		    		//System.out.print(" "+sum);
			        count++;
			      }
		    //	else 
		    	 // System.out.println("Missing Value");	
		    }	
		    
		    ob.avg =(double)sum/count;
		    
		//    System.out.println(" sum ="+sum+" Non Missing values Count ="+count+" Avg= "+ob.avg);
		//	System.out.println(" sum ="+sum+" Non Missing values Count ="+count+" Avg= "+(new BigDecimal(Double.toString(ob.avg))).toPlainString());	
		    		    
		}
		
		//Put the avg into the missing locations
		for(RowData ob:myData)
		{  

		    for(Double var: ob.cellData)
		    {  		    
		    	if(var==1.0E99)
			      {
		    		int index = ob.cellData.indexOf(var);
		    		ob.cellData.set(index, ob.avg);
			      }
		    	
		    }			    
		    
		}
				
	}
	
	
	//carefully ignore the missing values in the distance computation
	void calcDistances3()  
	{		
		for(RowData ob1:orData) //OrData
		{  
	       int idx = orData.indexOf(ob1);
			
			for(RowData ob2:myData) //MyData
			{  
				int index = myData.indexOf(ob2);							
				
			//	if(ob2.hasmissing() == true)
				//{
			//		ob1.dist[index] = Double.POSITIVE_INFINITY;
				//    continue;
			//	}
				
				if(idx == index)
					{
					  ob1.dist[index] =Double.POSITIVE_INFINITY; //we dont consider the distance to itself
					  continue;
					}				
				
				double sum =0;
				
				for(int i =0;i<NUM_OF_COLUMNS;i++)
				 {
					if(ob1.cellData.get(i)==1.0E99) continue; //ignore the missing value columns in the dist calc
					
				 	double temp = ob1.cellData.get(i) - ob2.cellData.get(i);
				    temp = temp*temp;
				    
				    sum+=temp;
				 }
				
				sum = Math.sqrt(sum); //Extra CODE
				
				//System.out.print(" index="+index);
				ob1.dist[index] =sum;

			}
			
			ArrayIndexComparator comparator = new ArrayIndexComparator(ob1.dist);	
			Integer[] indexes = comparator.createIndexArray();
			Arrays.sort(indexes, comparator); //indexes will have the indexes of the elements which have been sorted
			
		//	System.out.print(" Top most elements for row "+idx);
			for(int j=0;j<k;j++)  //j=0 will have distance for itself
			{
				ob1.topk[j] = indexes[j];   //topk[] will have the nearest element INDICES and 0th position is ignored as it will be a zero distace(dist to itself)
		//		System.out.print("= "+ob1.dist[ob1.topk[j-1]]);
			}
			
		//	System.out.println();		           
		}
        		
		
		/* printing the sample row
		RowData ob3 = myData.get(2);
		
		int i=0;
		
		for(double dist:ob3.dist)
		{
		 	System.out.println("distance to "+(i++)+"is "+dist);
		}
		 */
		
  }
		
		
		
  double KNN(int row, int col)
	{
		RowData ob = orData.get(row); //OrData
		
		double num=0; 
		double den=0;
		
	 	for(int i=0;i<k;i++)
	 	{
	 	  int top = ob.topk[i];
	 	  
	 	  double temp = myData.get(top).cellData.get(col); //myData
	 	  double weight = (double)1/ob.dist[top];
	 	  
	 	  num+= weight*temp;
	 	  
	 	  den+=weight;
	 		
	 	}
	 
	  double result =(double)num/den;	
	  
	  return result;
	 	
	}	
	
	void calcKNN()
	{
		for(RowData ob:orData)
		{
			for(Double var: ob.cellData)
		    {  		    
		    	if(var==1.0E99)
			      {
			         int cindex = ob.cellData.indexOf(var);
			         int rindex = orData.indexOf(ob);
			         
			      //   System.out.println("Calc KNN for Row "+rindex+" Col"+cindex);
			         double res= KNN(rindex, cindex);
			         
			         ob.cellData.set(cindex,res);
			        
			      }
		    }
		}
		
	}
	
	
	
 void printTop(int row, int col)
  {
	 RowData ob=  orData.get(row); 
	 
	 System.out.println("Nearest Sets are .. \n");
	 for(int i =0;i<k;i++)
	  {		 
	 	  int top = ob.topk[i];
	 	  
	 	  RowData ob2 = myData.get(top);

	 	 System.out.print("["); 
         for(int j=0;j<NUM_OF_COLUMNS;j++ )
         {
        	 System.out.print(" "+ob2.cellData.get(j)); 
        	 
         }
         System.out.print(" dist ="+ob.dist[top]+"] \n");
	 	  
	  }	 	 
  }
	
 
 void LMS_Store(int id)
  {
	 RowData ob = orData.get(id);
	
	 coeff = new double[NUM_OF_COLUMNS][NUM_OF_ROWS]; //temp array for calculations
	 actual = new double[NUM_OF_COLUMNS];
		
	   //filling the coeff matrix
		 for(int i =0;i<k;i++)
		  {		 
		 	  int top = ob.topk[i];
		 	  
		 	  RowData ob2 = myData.get(top);
	         
		 	 int m=0;
		 	 for(int j=0;j<NUM_OF_COLUMNS;j++ )
	         {
	        	
		 		 if(ob.cellData.get(j)==1.0E99 ) continue;
	        	 
		 		 coeff[m++][i] = ob2.cellData.get(j); 
	        	 //System.out.print(" "+ob2.cellData.get(j)); 
	        	 
	          }	//for j        
		 	  
		  }	//for i	  		
		 		
		 int m=0;
		 
		//filling the actual data matrix
		 for(Double var: ob.cellData)
		 {
			 if(var==1.0E99 ) continue; 
			 
			 actual[m++] = var;
		 }
	 
		 trsize = m; //Very imp
		 tcsize =k;
  }
 
 
  void printMat()  //Double [][] m1 , Double[] m2
   {
		
	System.out.println("Printing Coeff Matrix \n"); 
	for(int i=0; i < trsize ;i++)
	{
		//System.out.print("coeff[i] len: "+coeff[i].length);		
		 
		for(int j=0;j<tcsize ;j++)
		 System.out.print(" "+coeff[i][j]);
		   
		System.out.println();
	}
	
	System.out.println("Printing Actual Data Matrix \n");
	
	for(int j=0;j<trsize ;j++)
		  System.out.println(" "+actual[j]);
	  
   }
  
  
  void printStep()  //Double [][] m1 , Double[] m2
   {
		
	System.out.println("Printing Coeff2 Matrix \n"); 
	
	for(int i=0; i <newsize ;i++)
	{
		//System.out.print("coeff[i] len: "+coeff[i].length);		
		 
		for(int j=0;j<newsize ;j++)
		  System.out.print(" "+coeff2[i][j]);
		   
		System.out.println();
	}
	
	//printing actual 2
	System.out.println("Printing actual2 array \n"); 
	for(int j=0;j<newsize ;j++)
		  System.out.println(" "+actual2[j]);
	  
   }
  
  
  double col_Mul(int col1 , int col2)
  {
	double sum =0;
	
	for(int i=0;i<trsize ;i++)
	{
		sum+= coeff[i][col1]*coeff[i][col2]; //multiply one column with another
	}
	
	return sum;	  
  }
  
  double aggregate(int col)
  {
	  double sum=0;
	  
	  for(int i=0; i<trsize; i++)
		  sum+=coeff[i][col];
	  
	  return sum;
  }
  
  void LMS_step2() //This will compute the elements of k*k  matrix
  {
	  newsize = tcsize+1; //to accomodate the extra constant in the matrix Set
	                          //http://www.csee.umbc.edu/~squire/cs455_l4.html
	  coeff2 = new double[newsize][newsize]; 
		
	  coeff2[0][0] = 1; // trsize*1;   //trsize *1 is not working so i think its just 1
	  
	for(int p=1; p< newsize ;p++)   //calc of the first row in the target matrix
		coeff2[0][p] = aggregate(p-1); 
	
	for(int p=1; p< newsize ;p++)  
		coeff2[p][0] = coeff2[0][p];	 
	
		  
	  for(int i=1; i <newsize;i++)
		{			 
			for(int j=1;j<newsize ;j++)
			{				  
				coeff2[i][j] = col_Mul(i-1,j-1);
						
			}		
		}  
  }
 
double  mul_Y(int col)
 {
	 double sum=0;
	 
	 for(int i=0;i<trsize ;i++)
		{
		  sum += coeff[i][col]*actual[i];
		}
	 
	 return sum;
 }
 
double getActualSum()
{
 double sum=0;
 
 for(int i=0;i<trsize ;i++) sum+=actual[i];
 
 return sum;
}

  void LMS_step3() //This will compute the elements of actual2 matrix
  {
	 actual2 = new double[newsize]; 
	  
	 actual2[0] = getActualSum();
	 
	  for(int i=1; i < newsize ;i++) //we need only k elements
		{
		  actual2[i] = mul_Y(i-1);
		}
 
  }
  
 
  double finalSol(int row, int col)
  {
	 double sum = sol[0]; //sol[0] holds the constant 
	  
		RowData ob = orData.get(row); //OrData
	 
		for(int i=0;i<k;i++)
	 	{
	 	  int top = ob.topk[i];
	 	  
	 	  double temp = myData.get(top).cellData.get(col); //myData
	 	
	 	   sum+=sol[i+1]*temp;
	 	  	
	 	}
		
	 return sum; 
  }
  
  void fillMissing(int row)
  {
	  RowData ob=  orData.get(row); 
	  
	  for(Double var: ob.cellData)
	    {  		    
	    	if(var==1.0E99)
		      {
	    		 int cindex = ob.cellData.indexOf(var);
		         int rindex = orData.indexOf(ob);
		         
		      //System.out.println("Calc LMS for Row "+rindex+" Col"+cindex);
		        		         
		         double res= finalSol(rindex, cindex);
		         
		         ob.cellData.set(cindex,res);
	    		
		      }
	    	
	    }
  }
  
  // This is very similiar to testfill to calculate LMS values for comparion on diff k values
  
  void fillResults(int row)
  {
   RowData ob=  orData.get(row); 
	  
	  for(Double var: ob.cellData)
	    {  		    
		  		  
		   if(var!=1.0E99)
		      {				   
	    	    //int cindex = ob.cellData.indexOf(var);
		        //int rindex = orData.indexOf(ob);		        
		        //System.out.println("Calc LMS for Row "+rindex+" Col"+cindex);		        		         
		        //double res= finalSol(rindex, cindex);		         
		        //System.out.println("Actual Data "+var+" Approx Data "+res);
		        
		     }
		   else 
		    {
			     int cindex = ob.cellData.indexOf(var);
		         int rindex = orData.indexOf(ob);
		         
		         //System.out.println("Calc LMS for Row "+rindex+" Col"+cindex);
		        		         
		         double res= finalSol(rindex, cindex);
		         ob.cellData.set(cindex,res);
		         //System.out.println("Approx Data "+res);
			     
		     }	    	
	    } 
  }
  
  void testFill(int row)
  {
	  RowData ob=  orData.get(row); 
	  
	  for(Double var: ob.cellData)
	    {  		    
		  		  
		   if(var!=1.0E99)
		      {	
			   
	    		 int cindex = ob.cellData.indexOf(var);
		         int rindex = orData.indexOf(ob);		        
		         //System.out.println("Calc LMS for Row "+rindex+" Col"+cindex);		        		         
		         double res= finalSol(rindex, cindex);		         
		         System.out.println("Actual Data "+var+" Approx Data "+res);
		        
		     }
		   else 
		    {
			     int cindex = ob.cellData.indexOf(var);
		         int rindex = orData.indexOf(ob);
		         
		         //System.out.println("Calc LMS for Row "+rindex+" Col"+cindex);
		        		         
		         double res= finalSol(rindex, cindex);
		         
		         System.out.println("Approx Data "+res);
			   
		     }	    	
	    }
	  
  }
  
  void copyRead() //copy to testData variable
  {
	  for(RowData ob:orData)
		{
		  RowData rowData = new RowData();
		  
			for(Double var: ob.cellData)
		    {  	
			    rowData.cellData.add(var);			    
		    }
		 
		  testData.add(rowData);	
		}
	  
  }
  
  static double doAverageErr()
  {
   double sum =0;
   int count =0;
   
   for(double d :QtnV2.temperrList)
   {
	   count++;
	   sum+=d;
   }
   
  // System.out.println("Count ="+count);
   
   double avg =(double)sum/QtnV2.temperrList.size();
   
   QtnV2.errList.add(avg); 
   
    return avg;
  }
	
  
  void calcError()
  {
	 double err=0; //denotes LMS error
	 
	  for(RowData ob:testData)
		{
			for(Double var: ob.cellData)
		    {  		    
		    	if(var==1.0E99)
			      {
			         int cindex = ob.cellData.indexOf(var);
			         int rindex = testData.indexOf(ob);
			         
			         double original =  serData.get(rindex).cellData.get(cindex);
			         
			         //double test =testData.get(rindex).cellData.get(cindex);
			         if(original!=1.0E99)
			         {
			         // System.out.println("Calc KNN for Row "+rindex+" Col"+cindex);
			         
			           double observed =  orData.get(rindex).cellData.get(cindex); //since after final steps values in OrData are replaced it is treated as observed data
			             
			           double temp = original - observed;
			       //    System.out.println("Original: "+original+" Observed: "+observed+" diff ="+temp);
			           
			           err+= temp*temp;
			         }
			      }
		    }
		}
	  
	  err= Math.sqrt(err);
	 // temperrList.add(err);
	//  System.out.println("\n k= "+k+" Mean Square Error: "+err);
   
  }
  
  void fileWrite() 
  {
	  try
	  {
	  FileWriter f1 = new FileWriter("dataresult.txt");
	  BufferedWriter b1 = new BufferedWriter (f1);
	  
	  for(RowData ob:orData)
		{
		 		  
			for(Double var: ob.cellData)
		    {  	
			    b1.write(String.valueOf(var));			    
			    b1.write("\t");
			    
		    }
		 
			b1.write("\n");
			b1.flush();
		}
	 	  
	  }
	  catch(Exception e) {}
	  
  }
  
	public static void main(String args[])
	 {	
		estimateK(); //calls fillResults
		
		
	     //testcase1();
		// testcase2();	//latest testcase
	}
	
	static void estimateK()
	{
		/* Run the commented code to generate new Serialized original file */
		//QtnV2 qt2  = new QtnV2();
		// qt2.read(); //read the original data
		//qt2.write(); //write it into a file		 		
    	//// now modify the input file to have "known missing values"
				
		//-------------------------------Standard Procedure
		
	//	for(int iter=15; iter<46;iter++)
	//	{
		QtnV2 qt  = new QtnV2();
		//qt.readSerObj(); //read the serialized object   //reading the ORIGINAL values into serData
		qt.k=11;		//11 for 1st , 38 is the limit for 2nd dataset
		qt.read();
	
		//qt.copyRead(); //copy orData to a testData
		qt.doAverage();
		qt.calcDistances3();
		
		// System.out.println("orsize ="+qt.orData.size());
		//qt.printOr();
		
		for(int row=0; row< qt.NUM_OF_ROWS ;row++)
		{	    	 		  
		    qt.LMS_Store(row);
		    //qt.printMat();
		    qt.LMS_step2();
		    
		    qt.LMS_step3();
		    
		   // qt.printStep(); 
		   
		    RealMatrix coefficients = new Array2DRowRealMatrix(qt.coeff2); 	 
		   
		    DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();
		    
		    RealVector constants = new ArrayRealVector(qt.actual2, false);
		    RealVector solution = solver.solve(constants);
		    
		    qt.sol = new double[qt.newsize];
		    
		    for(int i=0;i< qt.k+1; i++) //k+1 for the extra constant
		    {		    	
		    	qt.sol[i] = solution.getEntry(i);
		    	//System.out.println("Solution "+i+": "+solution.getEntry(i));		    	    
		    }
		    
		    qt.fillResults(row);		    		
		}
		
		// qt.printOr();
		
		// qt.calcError();
		 qt.printOr();
		//  qt.fileWrite();
		//}	
		//System.out.println(" Row 0 col x  = " +qt.serData.get(0).cellData.get(13));
		
	}
	
	static void testcase2()
	{
		QtnV2 qt  = new QtnV2();
		qt.k=10	;		//46 is the limit for 2nd dataset
		qt.read();
		qt.doAverage();
		qt.calcDistances3(); 	
		  
		    qt.LMS_Store(0);
		   // qt.printMat();
		    qt.LMS_step2();
		    
		    qt.LMS_step3();
		    
		 //   qt.printStep(); 
		   
		    RealMatrix coefficients = new Array2DRowRealMatrix(qt.coeff2); 	 
		   
		    DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();
		    
		    RealVector constants = new ArrayRealVector(qt.actual2, false);
		    RealVector solution = solver.solve(constants);
		    
		    qt.sol = new double[qt.newsize];
		    
		    for(int i=0;i< qt.k+1; i++) //k+1 for the extra constant
		    {		    	
		    	qt.sol[i] = solution.getEntry(i);
		    	System.out.println("Solution "+i+": "+solution.getEntry(i));		    	    
		    }
		    
		    qt.testFill(0); //danger
		    
	}
	
	static void testcase1()
	{
		QtnV2 qt  = new QtnV2();
        qt.k=3; //max 12 // matching 4, 2
		  qt.read();
		   
		  // double y =qt.orData.get(0).cellData.get(1);
		   //System.out.println("Original value: "+y);
		   
		  		
	     //qt.print();
		
		    qt.doAverage();
		
		   //qt.print();					    
	    
		  //  System.out.println("my data len ="+ qt.myData.size());
		    
		  for(int row=0; row<242 ;row++ )
		  {
		    qt.calcDistances3(); 	
		  
		    qt.LMS_Store(row);
		    qt.printMat();
		    qt.LMS_step2();
		    
		    qt.LMS_step3();
		    
		    qt.printStep(); 
		   
		    RealMatrix coefficients = new Array2DRowRealMatrix(qt.coeff2); 	 
		   
		    DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();
		    
		    RealVector constants = new ArrayRealVector(qt.actual2, false);
		    RealVector solution = solver.solve(constants);
		    
		    qt.sol = new double[qt.newsize];
		    
		    for(int i=0;i< qt.k+1; i++) //k+1 for the extra constant
		    {		    	
		    	qt.sol[i] = solution.getEntry(i);
		    	System.out.println("Solution "+i+": "+solution.getEntry(i));		    	    
		    }
		    
		    qt.fillMissing(row);
		    
		    // qt.calcKNN();
		  		  
		    //	qt.printTop(0,1); 	 
		  //  qt.printOr();  
		    
		  }   
		
	}
}

/***** Sample code 


int [][] d = new int[5][5];

d[0][0] =2; d[0][1] =3;
d[1][0]=5;
d[2][2]= 9;d[2][1]=7;
//d= {  {1,2}, {1,2,3}, {1,2,3,4}  }; 

for(int i=0;i<d.length ;i++)
{	   System.out.println("len ="+d[i].length);

  for(int var: d[i])
 	 System.out.println(" "+var);

}

*************/