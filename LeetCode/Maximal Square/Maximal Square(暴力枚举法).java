
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
		int ans = 0;
		int row = matrix.length;
		int col = 0;
		if (row > 0)
		{
			col = matrix[0].length;
		}
		
		for (int i = 0; i < row; ++i)
		{
			for (int j = 0; j < col; ++j)
			{
				if (matrix[i][j] == '1')
				{
					int len = 1;
					boolean flag = true;
					while (i + len < row && j + len < col && flag)
					{
						for (int k = j; k <= j + len; ++k)
						{
							if (matrix[i + len][k] == '0')
							{
								flag = false;
								break;
							}
						}
						
						for (int k = i; k <= i + len; ++k)
						{
							if (matrix[k][j + len] == '0')
							{
								flag = false;
								break;
							}
						}
						
						if (flag) 
						{
							++len;
						}
					}
					
					if (ans < len)
					{
						ans = len;
					}
				}
			}
		}
		
		return ans * ans;
    }
}