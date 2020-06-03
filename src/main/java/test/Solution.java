package test;

import java.util.ArrayList;
import java.util.List;

public class Solution {

	public static void main(String[] args) {
		int[][] matrix = new int[][] {
			{1,3,1},
			{1,5,1},
			{4,2,1}
		};
		int min = minPathSum(matrix);
		System.out.println("min == " + min);
		
	}
	
	public static int minPathSum(int[][] grid) {
        int height = grid.length;
        int width = grid[0].length;
		for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int up = -1;
                if (i - 1 >= 0) {
                    up = grid[i-1][j];
                }
                int left = -1;
                if (j - 1 >= 0) {
                    left = grid[i][j - 1];
                }
                int cur = grid[i][j];

                if (up != -1 && left != -1) {
                	if (up > left) {
                		cur += left;
                	} else {
                		cur += up;
                	}
                } else {
                	if (up != -1) {
                		cur += up;
                	}
                	if (left != -1) {
                		cur += left;
                	}
                }

                grid[i][j] = cur;
            }
        }
        return grid[height - 1][width - 1];
    }
}
