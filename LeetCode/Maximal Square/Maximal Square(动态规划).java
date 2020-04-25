
/**
Given a 2D binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.

Example:

Input: 

1 0 1 0 0
1 0 1 1 1
1 1 1 1 1
1 0 0 1 0

Output: 4
 */


class Solution 
{
	public int maximalSquare(char[][] matrix)
	{
		int row = matrix.length;
		int col = 0;
		if (row > 0)
		{
			col = matrix[0].length;
		}
		
		int maxLen = 0;
		int[][] dp = new int[row + 1][col + 1];
		for (int i = 1; i <= row; ++i)
		{
			for (int j = 1; j <= col; ++j)
			{
				if (matrix[i - 1][j - 1] == '1')
				{
					dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i][j - 1], dp[i - 1][j])) + 1;
					maxLen = Math.max(dp[i][j], maxLen);
				}
			}
		}
		
		return maxLen * maxLen;
    }
}