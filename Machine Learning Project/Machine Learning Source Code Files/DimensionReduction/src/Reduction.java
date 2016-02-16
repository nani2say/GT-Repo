import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
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
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;


public class Reduction 
 {
	int NUM_OF_COLUMNS =  4434;  //3571 , 2
	int NUM_OF_ROWS = 50;  //50, 62  , 10
    int k=0;
    
    
    ArrayList<RowData> myData = new ArrayList<RowData>();
	ArrayList<RowData> orData = new ArrayList<RowData>();
	
	double data[][] = new double[NUM_OF_ROWS][NUM_OF_COLUMNS];
	double mean[] = new double[NUM_OF_COLUMNS]; //earlier it was num of rows
	double covdata[][]= new double[NUM_OF_COLUMNS][NUM_OF_COLUMNS];
	double eigenData[][]= new double[NUM_OF_COLUMNS][NUM_OF_COLUMNS];
	double eigenValues[]= new double[NUM_OF_COLUMNS];
	double topEigens[][];
	double prinComp[][];
	
	 double labels[] = new double[NUM_OF_ROWS];
	 
	ArrayList<ColData> eigObjData = new ArrayList<ColData>();
	
	class RowData implements Serializable
	{
	 
	  ArrayList<Double> cellData = new ArrayList<Double> ();	
	  double avg; 
	  Double dist[] = new Double[NUM_OF_ROWS];
	  Double sorteddist[] = new Double[NUM_OF_ROWS];
	  int topk[] = new int[k];
			
	}
	
	class ColData implements Comparable<ColData>
	{	 
	  ArrayList<Double> cellData = new ArrayList<Double> ();	
	  double eigenVal; 			
	
	  public int compareTo(ColData cd)
      {
    	
    	if(this.eigenVal <cd.eigenVal) return 1;
    	if(this.eigenVal >cd.eigenVal) return -1;
    	
    	return 0;    	
       }
	}
	
	
	void readLabel()
	{
		int sheetIndex = 0;
		XSSFSheet sheet =null;	
		
		try
		{
		  XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream( new File("L2.xlsx")));
		
		  sheet= workbook.getSheetAt(sheetIndex);		
		}
		catch(Exception e){ e.printStackTrace();}

		int rows=0; 
		for (Row tempRow : sheet)
		{
		 
			RowData rowData = new RowData();
			RowData row2Data = new RowData();
		    // print out the first three columns
			
		    for (int column = 0; column < 1; column++) {
		        Cell tempCell = tempRow.getCell(column);
		 
		        if (tempCell.getCellType() == Cell.CELL_TYPE_NUMERIC)
		        {
		          labels[rows]=	tempCell.getNumericCellValue();
		       
		        }
		    }

		   rows++;		  
		   if(rows==NUM_OF_ROWS) break;
		
		}
	}
	
	void read()
	{
		int sheetIndex = 0;
		XSSFSheet sheet =null;
	
		try
		{
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream( new File("T2.xlsx")));
		
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
	 }
		
	
 void write()
	{
		 try
	      {
	         FileOutputStream fileOut =new FileOutputStream("eigenVec.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(eigenData);
	         out.close();
	         fileOut.close();
	         System.out.printf("Eigen Vectors data is saved in eigenVec.ser");
	      }
		 catch(IOException i)
	      {
	          i.printStackTrace();
	      }
		 
		 try
	      {
	         FileOutputStream fileOut =new FileOutputStream("eigenVal.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(eigenValues);
	         out.close();
	         fileOut.close();
	         System.out.printf("Eigen Values is saved in eigenVal.ser");
	      }
		 catch(IOException i)
	      {
	          i.printStackTrace();
	      }
		 
		 try
	      {
	         FileOutputStream fileOut =new FileOutputStream("zeromeandata.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(data);
	         out.close();
	         fileOut.close();
	         System.out.printf("Zero Mean Data is saved in zeromeandata.ser");
	      }
		 catch(IOException i)
	      {
	          i.printStackTrace();
	      }
		
		
	}
		
  Reduction readSerObj() //reading the ORIGINAL values into serData
	{		
	 Reduction rd = null;
	 
		try
	      {
	         FileInputStream fileIn = new FileInputStream("Obj.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         rd = (Reduction) in.readObject();
	         in.close();
	         fileIn.close();
	         
	      }
		 catch(Exception i)
	       {
	         i.printStackTrace();	         
	       }
		
		 return rd;
		
	 }
	
	
   void copyToArray(double dt[][]) //copy to testData variable
	  {
	    int i=0; 
	    int j=0;
	    
		  for(RowData ob:orData)
			{		      
			  j=0;
				for(double var: ob.cellData)
			      {  					    
					 dt[i][j]= var;			    
			         j++;
			      }
			   
			    i++;		
			}
		  
	  }
	
   
    void printArray(double arr[][])
    {
    	
   //  System.out.println("Printing array ");
    	  
       for(double[] temp: arr)
       {
    	  
    	  for(double x: temp)
    	    {
    		  System.out.print("   "+x);   		  
    	      
    	     }
    	  
    	   System.out.println();
    	  
        }    	
     }
    
    
    void meanCalc()
    {
    	for(int i=0; i<NUM_OF_COLUMNS; i++ ) //process column by column 
    	{
    	  double sum= 0.0;	
    	 
    	  for(int j=0; j<NUM_OF_ROWS; j++)
    	  {  		 
    		 
    		  sum+=data[j][i]; 
    		 
    	  }
    	  
    	 mean[i] = (double)sum/NUM_OF_ROWS; //mean of column i 
    	 
    	// System.out.print("sum -"+sum+" Mean of Col "+i+" is "+mean[i]);   	
    	
    	}
    	
    	//subtracting mean 
    	
    	for(int i=0; i<NUM_OF_COLUMNS; i++ ) //process column by column 
    	{   	 
    	  for(int j=0; j<NUM_OF_ROWS; j++)
    	  {  		 
    		data[j][i] = data[j][i]- mean[i] ;     		
  
    	  }   	
    	}
    }
    
    void getCovMatix(RealMatrix cov)
    {
    	int rows = cov.getRowDimension();
    	int cols = cov.getColumnDimension();
    //	covdata = new double[rows][cols];  //not needed
    //	System.out.println("\n Get Row Dimension = "+rows+" Col Dim ="+cols);
    	
    	covdata = cov.getData();
    	
    }
    
    void getEigenMatrix(RealMatrix eigen)
    {
    	int rows = eigen.getRowDimension();
    	int cols = eigen.getColumnDimension();
    //	eigenData = new double[rows][cols];  //not needed
    //	System.out.println("\n Get Row Dimension = "+rows+" Col Dim ="+cols);
    	
    	eigenData = eigen.getData();
    	
    }
    
   /* This will add the eigenVectorsData to an ArrayList of objects and sorts them based on eigen values */ 
    //the columns(eigenvectors) will be pushed as a coldata objects
   void pushEigenToColData()
   {
	   int ix=0;
	   
	   //eigenData is a square matrix
	   int length = eigenData.length;
	   
	   for(int i=0;i<length; i++) //for every column
       {
    	  ColData cd = new ColData();
    	  
    	  for(int j=0;j<length;j++)
    	    {
    		 double x = eigenData[j][i];   		  
    	      cd.cellData.add(x);
    	     }
    	  
    	   cd.eigenVal = eigenValues[ix++];
    	   
    	   eigObjData.add(cd); //add to the  
    	  
         }
	   
	    Collections.sort(eigObjData);
	    
   }
   
   //this will extract sorted eigendata into row based eigen vectors
   //will extract only to n wigen vectors
   void getColEigenFromObj(int n)
   {
	   topEigens = new double[n][NUM_OF_COLUMNS];
	   
	   for(int i=0; i<n ;i++)
	   {
		   int j=0;
		   
		   ColData cd = eigObjData.get(i);
		   
		   for(double temp: cd.cellData)
			     topEigens[i][j++] =temp;
	   }
	   
   }
  
   //returns the transpose of the given 2d array
  double[][] transpose(double arr[][])
   {
	   //assuming that its a rectangular matrix
	   int rows = arr.length;
	   int cols = arr[0].length;
	   
	   double temp[][]  = new double[cols][rows];
	   
	   for(int i=0; i <rows; i++)
		   for(int j=0; j<cols; j++)
			    temp[j][i]= arr[i][j]; 
  
        return temp;
   }
   
  //multiply the featured vector(top eigens with zero mean transposed data 
  //order or multiplication is imp topeigens X data
  void multiply()
   {
	   //o stands for original and t stansds for topeigens
	
	   int m = topEigens.length;
	   int n = topEigens[0].length;
	   
	   int p = data.length;
	   int q = data[0].length;
	   
	   if(p!=n) 
	   {
		   System.out.println(" Multiplication of Matrices is not possible ");
		   System.exit(0);
	   }
	   
	   prinComp = new double[m][q];
	   
	   for(int i=0; i<m ; i++)
	   {
		   for(int j=0;j<q;j++)
		   {
			   double sum=0;
			   
			   for(int k=0; k<n; k++)
			   {
				  sum+= topEigens[i][k]*data[k][j];
			   }
			   
			   prinComp[i][j] =sum;
		   }
	   }
	   
   }
   
	public static void main(String[] args)
	{
		 testcase_Jama();
		
		//testcase_Apache();
         
	}
	
	static void testcase_Jama()
	{
		Reduction rd = new Reduction();
		rd.read();
		rd.copyToArray(rd.data);
	    //rd.printArray(rd.data);
       
	//	 System.out.println("\n printing data after mean subtraction ");
		rd.meanCalc();
    //    rd.printArray(rd.data);
        
		 System.out.println("Completed Mean calculation ");
        RealMatrix covarianceMatrix = new Covariance(rd.data).getCovarianceMatrix();

        //covarianceMatrix= covarianceMatrix.transpose();
        
   	     System.out.println("Completed Cov Calculation ");        
         rd.getCovMatix(covarianceMatrix); //data will be extracted to covData
		
         Matrix covmat = new Matrix(rd.covdata);
         
         EigenvalueDecomposition ed= new EigenvalueDecomposition(covmat);
         
         System.out.println("Completed Eigen Vector Calculation ");    
         
         Matrix eigenMat= ed.getV();
         
         rd.eigenData = eigenMat.getArray();
         
       //  rd.printArray(rd.eigenData);
         rd.eigenValues = ed.getRealEigenvalues();
         
       //  for(double i: rd.eigenValues)
            // System.out.println("\n Eigen value "+i);
         
        rd.pushEigenToColData(); //push to a arraylist of objects and sort it
         
          rd.write();
          ////////////////////////////////////
        
         //rd.getColEigenFromObj(2); //get topmost 2 eigen vectors
         
     //    System.out.println("\n Printing top n eigen vectors ");
      //   rd.printArray(rd.topEigens);
         
       //  rd.data= rd.transpose(rd.data);
         
        // System.out.println("\n Printing transposed data matrix \n");
       //  rd.printArray(rd.data);
        
     //    rd.multiply();
         
         
      //   rd.prinComp= rd.transpose(rd.prinComp); //getting back the from the previous transpose values
       
        // System.out.println(" Principal Components \n");
       //  rd.printArray(rd.prinComp);
	}
	
	static void testcase_Apache()
	{
		Reduction rd = new Reduction();
		rd.read();
		rd.copyToArray(rd.data);
	    //rd.printArray(rd.data);
       
	   //System.out.println("\n printing data after mean subtraction ");
		rd.meanCalc();
    //    rd.printArray(rd.data);
        
		 System.out.println("Completed Mean calculation ");
        RealMatrix covarianceMatrix = new Covariance(rd.data).getCovarianceMatrix();

        //covarianceMatrix= covarianceMatrix.transpose();
        
   	 System.out.println("Completed Cov Calculation ");
        
        rd.getCovMatix(covarianceMatrix);
                  
       //  rd.printArray(rd.covdata); //printing covariance data
         
         RealMatrix m = MatrixUtils.createRealMatrix(rd.covdata);
         EigenDecomposition eg =new EigenDecomposition(m);
         
    	 System.out.println("Completed eigen vector calc ");
    	 
         RealMatrix eigenMat = eg.getV();
         rd.getEigenMatrix(eigenMat);
        // System.out.println("\n Eigen data from Matrix V ");
       //  rd.printArray(rd.eigenData);
         
         rd.eigenValues = eg.getRealEigenvalues();
         
            for(double i: rd.eigenValues)
            System.out.println("\n Eigen value "+i);
         
         rd.pushEigenToColData(); //push to a arraylist of objects and sort it
         
         rd.getColEigenFromObj(1); //get topmost 2 eigen vectors
         
     //    System.out.println("\n Printing top n eigen vectors ");
      //   rd.printArray(rd.topEigens);
         
         rd.data= rd.transpose(rd.data);
         
        // System.out.println("\n Printing transposed data matrix \n");
       //  rd.printArray(rd.data);
        
         rd.multiply();
         
         
         rd.prinComp= rd.transpose(rd.prinComp); //getting back the from the previous transpose values
       

       //  System.out.println(" Principal Components \n");
      //   rd.printArray(rd.prinComp);
				
	}
}


