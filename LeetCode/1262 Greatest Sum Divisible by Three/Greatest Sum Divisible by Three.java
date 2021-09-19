
class Solution {
	 public int maxSumDivThree(int[] nums) {
		 int[][] dp = new int[nums.length][3];
		 
		 dp[0][nums[0] % 3] = nums[0];
		 
		 for (int i = 1; i < nums.length; ++i) {
			 int curRemainder = nums[i] % 3;
			 if (curRemainder == 0) {
				 dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][0] + nums[i]);
				 dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][1] + nums[i]);
				 dp[i][2] = Math.max(dp[i - 1][2], dp[i - 1][2] + nums[i]);
			 } else if (curRemainder == 1) {
				 if (dp[i - 1][2] % 3 == 2) {
					 dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][2] + nums[i]);
				 } else {
					 dp[i][0] = dp[i - 1][0];
				 }
				 
				 dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] + nums[i]);
				 
				 if (dp[i - 1][1] % 3 == 1) {
					 dp[i][2] = Math.max(dp[i - 1][2], dp[i - 1][1] + nums[i]);
				 } else {
					 dp[i][2] = dp[i - 1][2];
				 }
			 } else {
				 if (dp[i - 1][1] % 3 == 1) {
					 dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + nums[i]);
				 } else {
					 dp[i][0] = dp[i - 1][0];
				 }
				 
				 if (dp[i - 1][2] % 3 == 2) {
					 dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][2] + nums[i]);
				 } else {
					 dp[i][1] = dp[i - 1][1];
				 }
				 
				 dp[i][2] = Math.max(dp[i - 1][2], dp[i - 1][0] + nums[i]);
			 }
		 }
		 
		 return dp[nums.length - 1][0];
	 }
}