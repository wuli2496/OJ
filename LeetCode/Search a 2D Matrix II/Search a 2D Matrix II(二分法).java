/**
Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:

Integers in each row are sorted in ascending from left to right.
Integers in each column are sorted in ascending from top to bottom.
Example:

Consider the following matrix:

[
  [1,   4,  7, 11, 15],
  [2,   5,  8, 12, 19],
  [3,   6,  9, 16, 22],
  [10, 13, 14, 17, 24],
  [18, 21, 23, 26, 30]
]
Given target = 5, return true.

Given target = 20, return false.
 */
class Solution 
{
    public boolean searchMatrix(int[][] matrix, int target) 
    {
    	if (matrix == null || matrix.length == 0)
    	{
    		return false;
    	}
    	
        int row = matrix.length;
        int col = 0;
        if (row > 0)
        {
        	col = matrix[0].length;
        }
        
        int dimension = Math.min(row, col);
        for (int i = 0; i < dimension; ++i)
        {
        	boolean horizontal = binarysearch(matrix, target, i, false);
        	boolean vertical = binarysearch(matrix, target, i, true);
        	
        	if (horizontal || vertical)
        	{
        		return true;
        	}
        }
        
        return false;
    }
    
    private boolean binarysearch(int[][] matrix, int target, int start, boolean vertical)
    {
    	int low = start;
    	int high = vertical ? matrix.length - 1 : matrix[0].length - 1;
    	
    	while (low <= high)
    	{
    		int mid = (low + high) >> 1;
    		if (vertical)
    		{
    			if (matrix[mid][start] < target)
    			{
    				low = mid + 1;
    			}
    			else if (matrix[mid][start] > target)
    			{
    				high = mid - 1;
    			}
    			else 
    			{
    				return true;
    			}
    		}
    		else 
    		{
    			if (matrix[start][mid] < target)
    			{
    				low = mid + 1;
    			}
    			else if (matrix[start][mid] > target)
    			{
    				high = mid - 1;
    			}
    			else 
    			{
    				return true;
    			}
    		}
    	}
    	
    	return false;
    }
}