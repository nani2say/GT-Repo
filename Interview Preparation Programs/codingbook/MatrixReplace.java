

public class MatrixReplace
{
 int Mat[][];
 
  MatrixReplace()
 {
   Mat = new int[10][10];
   
   for(int i =0; i<Mat.length; i++)
    {
      for(int j=0; j<Mat[i].length ; j++)
	    {
		  Mat[i][j] = (int)(Math.random()*100%55); 
		}
    }   
  }
 
 void print()
 {
   System.out.println("Printing Matrix \n");
   
   for(int i =0; i<Mat.length; i++)
   {
      for(int j=0; j<Mat[i].length ; j++)
	    {
	 
             System.out.print(" "+Mat[i][j]);
          }
		  System.out.println();
   }
 }
 
 void replace()
  {
    int rows[] = new int[100];
	int cols[] = new int[100];
	
	int r=0; int c =0;
	
	for(int i =0; i<Mat.length; i++)
    {
      for(int j=0; j<Mat[i].length ; j++)
	     {
	       if(Mat[i][j] ==0)
		     {
			   rows[r++] = i;
			   cols[c++] = j;
			 }
		 }	
     }
	 
     for(int i =0; i<r; i++)
      {
	     int index = rows[i];
		  
         for(int t =0; t< Mat[index].length; t++)
		    {
			  Mat[index][t] = 0;
			}
	  }

     for(int i =0; i<c; i++)
      {
	     int index = cols[i];
		  
         for(int t =0; t< Mat.length; t++)
		    {
			  Mat[t][index] = 0;
			}
	  }	  
	 
  }

 public static void main(String args[])
 {
  MatrixReplace mr = new MatrixReplace();
   mr.print();
   
   mr.replace(); //replace all the 
 
   mr.print();
 
 }
}