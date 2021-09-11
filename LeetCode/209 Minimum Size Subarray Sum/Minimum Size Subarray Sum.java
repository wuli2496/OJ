class Solution {
	 public int minSubArrayLen(int target, int[] nums) {
		 int[] sum = new int[nums.length];
		 sum[0] = nums[0];
		 for (int i = 1; i < nums.length; ++i) {
			 sum[i] = sum[i - 1] + nums[i];
		 }
		 
		 int left = 0, right = nums.length;
		 int ans = Integer.MAX_VALUE;
		 while (left <= right) {
			 int mid = (left + right) >>> 1;
		 	 if (check(sum, target, mid)) {
		 		 ans = Math.min(ans, mid);
		 		 right = mid - 1;
		 	 } else {
		 		 left = mid + 1;
		 	 }
		 }
		 return ans == Integer.MAX_VALUE ? 0 : ans;
	 }
	 
	 private boolean check(int[] sum, int target, int mid) {
		 if (mid <= 0) {
			 return false;
		 }
		 if (sum[mid - 1] >= target) return true;
		 
		 for (int i = 1; i + mid - 1 < sum.length; ++i) {
			 if (sum[i + mid - 1] - sum[i - 1] >= target) return true;
		 }
		 
		 return false;
	 }
}