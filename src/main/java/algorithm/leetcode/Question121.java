package algorithm.leetcode;

import java.util.Stack;

public class Question121 {

	public static void main(String[] args) {
		int[] data = new int[] {1,2,3,4,5,6};
		maxProfit(data);
	}
	public static int maxProfit(int[] prices) {
        int max = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < prices.length; i++) {
            if (stack.isEmpty() || stack.peek() < prices[i]) {
                stack.push(prices[i]);
            } else {
                while (!stack.isEmpty()) {
                    int top = stack.peek();
                    if (top > prices[i]) {
                        stack.pop();
                        if (!stack.isEmpty()) {
                            int bottom = stack.firstElement();
                            int temp = top - bottom;
                            max = max > temp ? max : temp;
                        }
                    } else {
                        break;
                    }
                }
                stack.push(prices[i]);
            }
        }
        if (!stack.isEmpty()) {
        	int top = stack.peek();
            if (!stack.isEmpty()) {
                int bottom = stack.firstElement();
                int temp = top - bottom;
                max = max > temp ? max : temp;
            }
        }
        
        return max;
    }
}
