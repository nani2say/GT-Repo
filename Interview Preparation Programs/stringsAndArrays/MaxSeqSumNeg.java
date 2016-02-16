public class MaxSeqSumNeg {

	public static void main(String[] args) {

		int[] arr = new int[] { 6, -3, 3, -2, 3, -5, 16 };

		int sum = Integer.MIN_VALUE;
		int local = Integer.MIN_VALUE;
		//System.out.println(local);

		for (int i = 0; i < arr.length; i++) {
			if (local == Integer.MIN_VALUE)
				local = 0;

			local = local + arr[i];

			if (local >= sum)
			{
				sum = local;
			}

			if (local < 0)
			{
				 local = Integer.MIN_VALUE;
			}
        
		// System.out.println("cure ="+arr[i]+" global ="+sum+" local ="+local);
		}
		System.out.println("final:"+sum);
	}
}