public class ArrayInSpiralOrder {

	public static void main(String[] args) {
		int array[][] = {
		      { 0,  1,  2,  3, 4},
		      {15, 16, 17, 18, 5},
		      {14, 23, 24, 19, 6},
		      {13, 22, 21, 20, 7},
		      {12, 11, 10, 9, 8}};
		
		printMatrixNonRecursive(array, 5, 5);
	}	
	
	public static void printMatrixNonRecursive(int array[][], int rows, int cols){
	    int nCurrentLevel = 0;
	    int min = (rows < cols) ? rows:cols;
	    
	    while(nCurrentLevel <= min/2){
	        for(int j = nCurrentLevel; j < cols - nCurrentLevel - 1; j++){
	            System.out.print(array[nCurrentLevel][j] + " ");
	        }
	        for(int i = nCurrentLevel; i < rows - nCurrentLevel - 1; i++) {
	            System.out.print(array[i][cols - nCurrentLevel - 1] + " ");
	        }
	        for(int j = cols - nCurrentLevel - 1; j > nCurrentLevel; j--){
	            System.out.print(array[rows - nCurrentLevel - 1][j] + " ");
	        }
	        for(int i = rows - nCurrentLevel - 1; i > nCurrentLevel; i-- ){
	            System.out.print(array[i][nCurrentLevel] + " ");
	        }
	        nCurrentLevel++;
	    }
	}

}