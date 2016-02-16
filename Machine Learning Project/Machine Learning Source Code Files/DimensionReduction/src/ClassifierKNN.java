import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;
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


public class ClassifierKNN
{
	int NUM_OF_COLUMNS = 4434;  //3571 , 2
	int NUM_OF_ROWS = 50;  //62  , 10
	int ORG_ROWS =44;
	int NUM_LABELS =4; 
	int START =44; //starting row index of testdata
	int label[]= {1,2,3,4};
	int Tlabels[] = new int[ORG_ROWS];
	
	int dimensions =46; // set 1 -25;
	int k=7;  // this is defined as root of 72 - num of rows
	
	Reduction red;
	
	    ArrayList<RowData> myData = new ArrayList<RowData>();
		ArrayList<RowData> orData = new ArrayList<RowData>();
		
		class RowData implements Serializable
		{
		 
		  ArrayList<Double> cellData = new ArrayList<Double> ();	
		  double avg; 
		  Double dist[] = new Double[NUM_OF_ROWS];
		  Double sorteddist[] = new Double[NUM_OF_ROWS];
		  int topk[] = new int[k];
				
		}	
	
	void readSerObjects() //reading the ORIGINAL values into serData
		{		
		   red = new Reduction();
			try
		      {
		         FileInputStream fileIn = new FileInputStream("eigenVal.ser");
		         ObjectInputStream in = new ObjectInputStream(fileIn);
		         red.eigenValues =  (double[])in.readObject();
		      
		         fileIn = new FileInputStream("eigenVec.ser");
		         in = new ObjectInputStream(fileIn);
		         red.eigenData =  (double[][])in.readObject();
		         
		           fileIn = new FileInputStream("zeromeandata.ser");
		           in = new ObjectInputStream(fileIn);
		           red.data =  (double[][])in.readObject();
		         		       		         
		         in.close();
		         fileIn.close();
		         
		      }
			 catch(Exception i)
		       {
		         i.printStackTrace();	         
		       }			
		 }
	
	// this fucntion is to fill the orData, myData components of the Reduction obj 
	void getintoRowDatas(Reduction rd)
	{ 
		int cols = dimensions;
		
		for(int i=0;i<NUM_OF_ROWS; i++)
		{
			RowData rowData = new RowData();
			RowData row2Data = new RowData();
			
			for (int j = 0; j < cols; j++)
			{
			  rowData.cellData.add(rd.prinComp[i][j]);
			  row2Data.cellData.add(rd.prinComp[i][j]);
			}
			
			myData.add(rowData); //These are the Classifier variables *******
		    orData.add(row2Data); //These are the Classifier variables *******
			
		}
		
	}
	
	//orData variables are fileld with nearestVales(topk variable)
	void calcDistances3() //This has been modifies to deal with Classifier Class Variables
	{
		for(RowData ob1:orData) //OrData
		{  
	       int idx = orData.indexOf(ob1);
			
			for(RowData ob2:myData) //MyData
			{  
				int index = myData.indexOf(ob2);							
								
				if(idx == index)
					{
					  ob1.dist[index] =Double.POSITIVE_INFINITY; //we dont consider the distance to itself
					  continue;
					}				
				
				double sum =0;
				
				for(int i =0;i<dimensions;i++) //Num_OF_Coluns is replaced by dimensions
				 {					
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
			int p =0;
			for(int j=0;j<k;j++)  
			{
				while(indexes[p]>ORG_ROWS-1) p++; //This is a hack to ignore the distances to the un classified samples.. i.e the last test samples
				
				ob1.topk[j] = indexes[p++];  
		//		System.out.print("= "+ob1.dist[ob1.topk[j-1]]);
			}
			
		//	System.out.println();		           
		}
        					
	//	 printing the sample row
//		RowData ob3 = orData.get(62);
//		
//		int i=0;
//		
//		for(int tp :ob3.topk)
//		{
//		 	System.out.println("top  "+(i++)+" element index is "+tp);
//		}		 
//		
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
		catch(Exception e){
			//System.out.println("Hello");
			e.printStackTrace();}

		int rows=0; 
		for (Row tempRow : sheet)
		{
		    for (int column = 0; column < 1; column++)
		    {
		        Cell tempCell = tempRow.getCell(column);
		 
		        if (tempCell.getCellType() == Cell.CELL_TYPE_NUMERIC)
		        {
		          Tlabels[rows]=(int) tempCell.getNumericCellValue();
		        }
		    }

		   rows++;
		  
		   if(rows==ORG_ROWS) break;
		
		  // System.out.println("Hello");
		}
	}
	
	void classify()
	{
		
	  for(int i=START; i<NUM_OF_ROWS; i++)
	  {	 
		  int anslabel =-1;
		  int r=0;
		  double results[] = new double[NUM_LABELS];
		  
		  RowData ob = orData.get(i); //will get the current row under calc
		  				  
		  for(int tlabel :label ) //for each label
		  {
			  double sum=0; 
		
			for(int m =0;m<k ;m++) //for each k value
			{
				int index = ob.topk[m];
				double dist = ob.dist[index];
				int orlabel = Tlabels[index];
				
			//	System.out.println(" index:" +index+"  distance"+dist);
						
				if(tlabel == orlabel)
				{
					sum+=1/dist;
				}
								
			  }//each neighbour
			
			//store the aggregate for this label into the results var
			 results[r++] =sum;
             			
		   }	//each label	
		  
		  //iterate though the results and print the lable with the MAX results value 
		  double max =0;
		  
		  for(int p=0;p<results.length; p++)
		  {
			//  System.out.println("result[p] ="+results[p]);
			  
			  if(max < results[p])
			  {
				  max = results[p];
				  anslabel = label[p];				  
				 
			  }
			  
		  }
		  
		  System.out.println("Ans Label :"+anslabel);
	  }
		
	}
	
	public static void main(String[] args)
	{
		 ClassifierKNN ck = new  ClassifierKNN();
		 ck.readSerObjects();
		 Reduction red1 = ck.red; //storing the reduction class separately
		
		// Calculations based on Reduction Class: Completing the remainibg steps of PCA 
         red1.pushEigenToColData(); //push to a arraylist of objects and sort it
         
        //---------------------------

       //---------------------------- 
       
        red1.getColEigenFromObj(ck.dimensions); //get topmost n eigen vectors i.e 25 as of now
         
     //    System.out.println("\n Printing top n eigen vectors ");
      //   rd.printArray(rd.topEigens);
         
        red1.data= red1.transpose(red1.data);
         
        // System.out.println("\n Printing transposed data matrix \n");
       //  rd.printArray(rd.data);
        
        red1.multiply();
         
         
        red1.prinComp= red1.transpose(red1.prinComp); //getting back the from the previous transpose values
       

         System.out.println(" Principal Components calc completed\n");
       //  red1.printArray(red1.prinComp);
         
          ck.getintoRowDatas(red1);
         ck.calcDistances3();
         
          ck.readLabel();
          ck.classify();
   
          
	}
	
}

//--
//int count =0;		 
//for(double i: red1.eigenValues)
// {
// 	 if(i >1) //returned 26 rows = so consider the top 25 principal components
// 	 { count++;
// 	 System.out.println("\n Eigen value "+i);
//	 }
// }
   //----

//un used
//	 DecimalFormat df = new DecimalFormat("#.#####");
//	    System.out.println (df.format( 1.7166810486953518E-19));
	 
//   int val = new BigDecimal("2.4472961947096834E-16").intValue();
//		   System.out.println(val); 

// 
/* extra stuff may be unnecesary 
RealMatrix covarianceMatrix = new Covariance(ck.red.data).getCovarianceMatrix();

//covarianceMatrix= covarianceMatrix.transpose();

    System.out.println("Completed Cov Calculation ");        
ck.red.getCovMatix(covarianceMatrix); //data will be extracted to covData

//ck.red.printArray(ck.red.covdata); 

System.out.println(" "+ck.red.covdata[3570][3121]);    
System.out.println(" "+ck.red.covdata[3121][3570]);    
*/
//-----------------------------------    

//-----
//int count =0;		 
//for(double i: red1.eigenValues)
//{
//	 if(i >10) //returned 26 rows = so consider the top 25 principal components
//	 { count++;
//	 System.out.println("\n Eigen value "+i);
//	 }
//}
//
//System.out.println("Total positive count = "+count);