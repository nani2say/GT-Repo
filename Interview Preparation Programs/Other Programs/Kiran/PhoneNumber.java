package careercup;

public class PhoneNumber {
	// if there is 4 it should start with 4
	// 4 digit phone number
	// invalid digits 2, 7, 9
	// no two consecutive same numbers

	public boolean checkValidity(int num, int size) {
		int[] digits = new int[4];
		
		// check for 4 condition
		if (!(num % 1000 == 4)) {
			if (num % 10 == 4 || (num / 10) % 10 == 4 || (num / 100) % 10 == 4) {
				return false;
			}
		}

		// check for two consecutive same digits
		if (num % 10 == (num / 10) % 10 || (num / 100) % 10 == (num / 10) % 10
				|| (num / 100) % 10 == (num / 1000) % 10) {
			return false;
		}		

		return true;
	}

	public void printnumbers() {
		for (int i = 1000; i <= 9999; i++) {
			if (checkValidity(i,4)) {
				System.out.println(i);
			} else {

			}
		}
	}

	public static void main(String[] args) {
		String s = new String();
		CharSequence cs = new String();
		//cs = "abc";
		StringBuffer sb = new StringBuffer();
		
	}

}
