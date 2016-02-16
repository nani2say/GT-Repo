package careercup;

public class RedBlackWinner {
	int[][] matrix;
	int matrixSize;
	int winner = 0;
	public RedBlackWinner(int n) {
		matrix = new int[n][n];
		this.matrixSize = n;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] = generate0Or1();
			}
		}
	}

	public int generate0Or1() {
		if ((int) (Math.random() * 1000) % 2 == 0) {
			return 0;
		} else {
			return 1;
		}
	}

	public int findWinner() {
//		int winner = 0;
		int winnerCount = 0;
		int temp = 0, prevSymbol = 0;
		for (int k = 0; k < matrixSize; k++) {
			temp = 0;
			for (int i = 0; i < matrixSize; i++) {
				if (i == 0) {
					prevSymbol = matrix[k][i];
					temp += 1;
				} else {
					if (prevSymbol == matrix[k][i]) {
						temp += 1;
						//winner = prevSymbol;
						//winnerCount = temp;
					} else {
						temp = 1;
						prevSymbol = matrix[k][i];
					}
					if (temp > winnerCount) {
						winnerCount = temp;
					}
				}
			}
			if (temp > winnerCount) {
				winnerCount = temp;
				winner=prevSymbol;
			}
//			for (int i = 0; i < matrixSize; i++) {
//				if (i == 0) {
//					prevSymbol = matrix[i][k];
//					temp += 1;
//				} else {
//					if (prevSymbol == matrix[i][k]) {
//						temp += 1;
//						winner = prevSymbol;
//					} else {
//						if (temp > winnerCount) {
//							winnerCount = temp;
//						}
//						temp = 1;
//						prevSymbol = matrix[i][k];
//					}
//				}
//			}
//			if (temp > winnerCount) {
//				winnerCount = temp;
//			}

		}
		System.out.println("Count: " + winnerCount);
		return winner;
	}
	public int getMaxCountInRow(int[] array){
		
		return 1;
	}
	public static void main(String[] args) {
		int n = 5;
		RedBlackWinner rb = new RedBlackWinner(n);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(rb.matrix[i][j] + " ");
			}
			System.out.println();
		}

		System.out.println("Winner: " + rb.findWinner());

	}

}
