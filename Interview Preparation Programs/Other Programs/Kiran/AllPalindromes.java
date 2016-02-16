package careercup;

public class AllPalindromes {
	public void printPalindromes(String str) {
		int len = str.length();
		StringBuffer sb = new StringBuffer();
		sb.append(str);
		for (int i = 0, j = len - 1; i < len;) {
			if (i > j) {
				break;
			} else if (i == j) {
				i = i + 1;
				j = len - 1;
			} else {
				String sub = sb.substring(i, j);
				if (sub.length() >= 3
						&& sub.equalsIgnoreCase(new StringBuffer(sub).reverse()
								.toString())) {

					System.out.println(sub);

				}
				j--;
			}
		}
	}

	public static void main(String[] args) {
		AllPalindromes ap = new AllPalindromes();
		ap.printPalindromes("abaaababaa");

	}

}
