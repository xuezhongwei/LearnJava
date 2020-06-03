package algorithm.leetcode;

public class Question85 {

	public static void main(String[] args) {
		char[][] matrix = new char[][] {
			{'1','0','1','0','0'},
			{'1','0','1','1','1'},
			{'1','1','1','1','1'},
			{'1','0','0','1','0'}
		};
		maximalRectangle(matrix);
	}
	
	public static int maximalRectangle(char[][] matrix) {
		int height = matrix.length;
		if (height == 0)
			return 0;
		int width = matrix[0].length;
        
        int[][] data = new int[height][width];
        
        for (int j = 0; j < width; j++) {
            for (int i = 0; i < height; i++) {
                int cur = Integer.parseInt(matrix[i][j] + "");
                if (cur == 1) {
                    int up = i - 1;
                    int upVal = 0;
                    if (up >= 0) {
                        upVal = data[up][j];
                    } 
                    data[i][j] = upVal + 1;
                } else {
                    data[i][j] = 0;
                }
            }
        }

        int max = 0;
        for (int i = 0; i < height; i++) {
            int area = largestRectangleArea(data[i]);
            max = max > area? max : area;
        }
        return max;
    }

    public static int largestRectangleArea(int[] heights) {
        int length = heights.length;
        if (length == 0) {
            return 0;
        }
        if (length == 1) {
            return heights[0];
        }
        int max = 0;
        for (int i = 0; i < length; i++) {
            int left = i - 1;
            while (left >= 0) {
                if (heights[left] < heights[i]) {
                    break;
                }
                left--;
            }
            int right = i + 1;
            while (right < length) {
                if (heights[right] < heights[i]) {
                    break;
                }
                right++;
            }
            int width = right - left - 1;
            int area = width * heights[i];
            max = max > area ? max : area;
        }
        return max;
    }
}
