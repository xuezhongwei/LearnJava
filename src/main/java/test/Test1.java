package test;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.sun.xml.internal.ws.api.pipe.NextAction;

public class Test1 {
	static char[] chs = new char[]{'(', ')'};
	public static void main(String[] args) throws IOException {
		
		
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
	
	public static String longestPalindrome(String s) {
        if (null == s || s.length() == 0) {
        	return "";
        }
        
        int maxLength = 0;
        int start = 0;
        int length = s.length();
        for (int index = 0; index < length; index++) {
        	int left = index;
        	int right = index;
        	int next_right = index + 1;
        	
        	int tempMax = 0;
        	int tempStart = 0;
        	
        	int max1 = 0;
        	int start1 = 0;
        	int max2 = 0;
        	int start2 = 0;
        	
        	while (left >= 0 && right < length && s.charAt(left) == s.charAt(right)) {
        		max1 = right - left + 1;
        		start1 = left;
        		
        		left--;
        		right++;
        	}
        	
        	left = index;
        	while (left >= 0 && next_right < length && s.charAt(left) == s.charAt(next_right)) {
        		max2 = next_right - left + 1;
        		start2 = left;
        		
        		left--;
        		next_right++;
        	}
        	if (max1 > max2) {
        		tempMax = max1;
        		tempStart = start1;
        	} else {
        		tempMax = max2;
        		tempStart = start2;
        	}
        	
        	if (tempMax > maxLength) {
        		maxLength = tempMax;
        		start = tempStart;
        	}
        }
        return s.substring(start, start + maxLength);
    }
	
	public static ListNode swapPairs(ListNode head) {
        if (head.next == null) {
            return head;
        } 

        ListNode newHead = new ListNode(-1);
        newHead.next = head;
        
        ListNode left = head;
        ListNode right = left.next;
        ListNode mark = newHead;
        
        while (right != null && left != null) {
        	ListNode temp = right.next;
        	right.next = left;
        	left.next = temp;
        	
        	mark.next = right;
        	mark = left;
        	
        	left = left.next;
        	if (left != null)
        		right = left.next;
        }
        
        return newHead.next;
    }
	
	public static ListNode getNodeList() {
		int[] val = new int[] {1,2,3,4};
		ListNode head = new ListNode(val[0]);
		ListNode tail = head;
		for (int i=1; i<val.length; i++) {
			ListNode newNode = new ListNode(val[i]);
			newNode.next = null;
			
			tail.next = newNode;
			tail = newNode;
		}
		return head;
	}
	
	public static long divide(int dividend, int divisor) {
		if (dividend == 0) {
            return 0;
        }
		if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }
        int result = 0;
        if (dividend > 0 && divisor < 0
        		|| dividend < 0 && divisor > 0) {
            int temp = dividend;
            while (temp >= 0 && dividend >= 0 ||
            		temp <= 0 && dividend <= 0) {
                temp = temp + divisor;
                result--;
            }
            return result + 1;
        } else {
            int temp = dividend;
            while (temp >= 0 && dividend >= 0 ||
            		temp <= 0 && dividend <= 0) {
                temp = temp - divisor;
                result++;
            }
            return result - 1;
        }
    }
	
	public static int strStr(String haystack, String needle) {
        int length = haystack.length();
        int length1 = needle.length();
        if (length1 == 0) {
            return 0;
        }
        if (length == 0 || length < length1) {
            return -1;
        }
        int i = 0;
        int j = 0;
        while (i < length ) {
            int point = i;
            while (point < length && j < length1) {
                if (haystack.charAt(point) == needle.charAt(j)) {
                    point++;
                    j++;
                } else {
                    j = 0;
                }
                if (j == length1 || j == 0) {
                    break;
                }
            }
            if (j == length1) {
                break;
            }
            i++;
        }
        if (j == length1)
            return i;
        return -1;
    }
	
	
    public List<String> generateParenthesis(int n) {
        List<String> ret = new ArrayList<>();
        String temp = "";

        return ret;
    }

    public static void backtrack(int n, List<String> ret, String temp) {
        if (temp.length() == n *2) {
            if (check(temp)) {
                ret.add(temp);
            }
            return;
        }
        for (char ch : chs) {
            String temp1 = temp;
            temp += ch + "";
            backtrack(n, ret, temp);
            temp = temp1;
        }
    }

    public static boolean check(String str) {
        Stack<Character> stack = new Stack<>();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if ('(' == str.charAt(i)) {
                stack.push('(');
            } else if (')' == str.charAt(i)) {
                if (!stack.isEmpty()) {
                    char top = stack.peek();
                    if (top == '(') {
                        stack.pop();
                    } 
                } else {
                    return false;
                }
            }
        }
        if (!stack.isEmpty()) {
            return false;
        }
        return true;
    }
}

class ListNode {
int val;
ListNode next;
ListNode(int x) { val = x; }

}
