import java.awt.Taskbar.State;

class Solution 
{
	 public int getMoneyAmount(int n) 
	 {
		 int[][] dp = new int[n + 1][n + 1];
		 
		 for (int len = 2; len <= n; ++len)
		 {
			 for (int start = 1; start <= n - len + 1; ++start)
			 {
				 int res = Integer.MAX_VALUE;
				 for (int pivot = start; pivot < start + len - 1; pivot++)
				 {
					 int tmp = pivot + Math.max(dp[start][pivot - 1], dp[pivot + 1][start + len - 1]);
					 res = Math.min(res, tmp);
				 }
				 dp[start][start + len - 1] = res;
			 }
		 }
		 
		 return dp[1][n];
	 }
	 
	 
}
