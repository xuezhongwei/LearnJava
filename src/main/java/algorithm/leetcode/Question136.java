package algorithm.leetcode;

public class Question136 {
	// 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
	// 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
	public static void main(String[] args) {
		int[] nums = new int[] {4,1,2,1,2};
	}
	// 位运算
	// 按位与 &  1 & 1 = 1
	// 按位异或  相同为0，相异为1
	public static int singleNumber(int[] nums) {
        int result = nums[0];
        for (int i = 1; i < nums.length; i++) {
            result ^= nums[i];
        }
        return result;
    }
}
