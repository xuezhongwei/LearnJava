package algorithm.leetcode;

public class Question231 {

	public static void main(String[] args) {
		isPowerOfTwo(8);

	}
	public static boolean isPowerOfTwo(int n) {
        int sum = 1;
        while (sum <= n) {
            if (sum == n) {
                return true;
            }
            sum = 1 << sum;
        }
        return false;
    }
}
