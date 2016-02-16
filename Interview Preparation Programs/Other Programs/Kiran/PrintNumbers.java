package careercup;
public class PrintNumbers {

	public void printNum(int num) {
		int start, end;
		start = (int) Math.pow(10, num - 1);
		end = (int) (Math.pow(10, num) - 1);
		System.out.println(start);
		System.out.println(end);
		System.out.println();
		for (int i = start; i <= end; i++) {
			if (checkValidity(i)) {
				System.out.println(i);
			} else {
				continue;
			}
		}
	}

	public boolean checkValidity(int number) {
		int prev = 10;
		int curr;

		while (number > 0) {
			curr = number % 10;
			if (curr >= prev) {
				return false;
			}
			prev = number % 10;
			number /= 10;
		}
		return true;
	}

	public void printNumbers(int n, int op) {
		int lastDigitUsed = (op / (int) Math.pow(10, n)) % 10;
		for (int i = lastDigitUsed + 1; i < 10; i++) {
			int temp = op + i * ((int) Math.pow(10, n - 1));
			if (n == 1)
				System.out.println(temp);
			else
				printNumbers(n - 1, temp);
		}
	}

	public void printNumbersKKS(int number, int base){
		int lastused = 0;
	}
	public static void main(String[] args) {
		PrintNumbers pn = new PrintNumbers();
		//pn.printNum(4);
		pn.printNumbers(3,0);
		// System.out.println("R: "+pn.checkValidity(1001));
	}

}
