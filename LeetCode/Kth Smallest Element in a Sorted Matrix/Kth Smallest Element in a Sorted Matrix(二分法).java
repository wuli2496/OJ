
class Solution 
{
	public int kthSmallest(int[][] matrix, int k) 
	{
		int n = matrix.length;
		
		int low = matrix[0][0];
		int high = matrix[n - 1][n - 1];
		while (low < high)
		{
			int mid = (low + high) / 2;
			int tmp = lessThenCount(matrix, mid);
			if (tmp < k)
			{
				low = mid + 1;
			}
			else 
			{
				high = mid;
			}
		}
		
		return low;
    }
	
	private int lessThenCount(int[][] matrix, int mid)
	{
		int n = matrix.length;
		int row = n - 1; 
		int col = 0;
		int count = 0;
		while (row > -1 && col < n)
		{
			if (matrix[row][col]  > mid)
			{
				--row;
			}
			else 
			{
				count += row + 1;
				++col;
			}
		
		}
		return count;
	}
}
