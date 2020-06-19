package algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

public class Question78 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ret = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            List<Integer> list = new ArrayList<>();
            list.add(nums[i]);
            ret.add(new ArrayList<>(list));
            for (int j = i + 1; j < nums.length; j++) {
                list.add(nums[j]);
                ret.add(new ArrayList<>(list));
            }
        }
        return ret;
    }
}
