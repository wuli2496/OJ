import java.util.Arrays;

class Solution {
    public boolean canPartition(int[] nums) {
        int sum = Arrays.stream(nums).sum();

        if (sum % 2 != 0) {
            return false;
        }

        int len = nums.length;
        int target = sum / 2;
        boolean[] dp = new boolean[target + 1];

        dp[0] = true;
        if (nums[0] <= target) {
            dp[nums[0]] = true;
        }

        for (int i = 1; i < len; ++i) {
            for (int j = target; j >= nums[i]; --j) {
                if (dp[target]) {
                    return true;
                }

                dp[j] = dp[j] || dp[j - nums[i]];
            }
        }

        return dp[target];
    }
}

