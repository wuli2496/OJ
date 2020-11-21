import java.util.Arrays;

public class Solution
{
    public int minMoves2(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        Arrays.sort(nums);
        int ans = 0;
        int median = nums[nums.length / 2];
        for (int num : nums) {
            ans += Math.abs(num - median);
        }

        return ans;
    }
}
