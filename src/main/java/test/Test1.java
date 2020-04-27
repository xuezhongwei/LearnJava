package test;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;

public class Test1 {

	public static void main(String[] args) throws IOException {
		//boolean flag = isPalindrome(121);
		System.out.println(reverse1(-2147483648));
		//test();
	}
	public static boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        } else {
            int sum = 0;
            int temp = x;
            while (x != 0) {
                int mod = x % 10;
                x = x / 10;
                sum = sum * 10 + mod;
            }
            System.out.println(sum);
            if (sum == temp) {
                return true;
            } else {
                return false;
            }
        }
    }
	
	public static void test() {
		System.out.println(-11 / 10);
	}
	
	public static int reverse(int x) {
		int sum = 0;
	       
        while (x != 0) {
        	if(Integer.MAX_VALUE/10 < sum || (Integer.MAX_VALUE/10 == sum && Integer.MAX_VALUE % 10 < x % 10)) {
        		return 0;
        	}
        	if(Integer.MIN_VALUE/10 > sum || (Integer.MIN_VALUE/10 == sum && Integer.MIN_VALUE % 10 > x % 10)) {
        		return 0;
        	}
            sum = sum * 10 + (x % 10);
            x /= 10;
        }
        return sum;
    }
	
	public static int reverse1(int x) {
		BigDecimal int_max = new BigDecimal(Integer.MAX_VALUE);
		BigDecimal int_min = new BigDecimal(Integer.MIN_VALUE);
		
		if (x < 0) {
			String str = x + "";
			StringBuffer sb = new StringBuffer(str);
			str = "-" + sb.reverse().toString().substring(0, sb.indexOf("-"));
			BigDecimal reverseVal = new BigDecimal(str);
			if (int_min.compareTo(reverseVal) > 0) {
				return 0;
			}
			return Integer.parseInt(str);
		} else {
			String str = x + "";
			StringBuffer sb = new StringBuffer(str);
			str = sb.reverse().toString();
			BigDecimal reverseVal = new BigDecimal(str);
			if (int_max.compareTo(reverseVal) < 0) {
				return 0;
			}
			return Integer.parseInt(str);
		}
    }
	
	// 递归，动态规划解决斐波拉契数列
	// 将中间结果保存起来
	public static int fib1(int n, Map<Integer, Integer> midResult) {
		if (n == 1) {
			midResult.put(1, 1);
			return 1;
		}
		if (n == 0) {
			midResult.put(1, 0);
			return 0;
		}
		if (!midResult.containsKey(n)) {
			midResult.put(n, fib1(n-1, midResult) + fib1(n-2, midResult));
		}
		return midResult.get(n);
	}
	// 递归，非动态规划解决斐波拉契数列
	public static int fib2(int n) {
		if (n == 1) {
			return 1;
		}
		if (n == 0) {
			return 0;
		}
		return fib2(n-1) + fib2(n-1);
	}
}
