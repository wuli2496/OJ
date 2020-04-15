/**
 * Number of Islands
 * Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

Example 1:

Input:
11110
11010
11000
00000

Output: 1
Example 2:

Input:
11000
11000
00100
00011

Output: 3
 */

class Solution 
{
    public int numIslands(char[][] grid) 
    {
    	int row = grid.length;
    	int col = 0;
    	if (row > 0)
    	{
    		col = grid[0].length;
    	}
    	
    	int nums = 0;
    	for (int i = 0; i < row; ++i)
    	{
    		for (int j = 0; j < col; ++j)
    		{
    			if (grid[i][j] == '1')
    			{
    				++nums;
    				dfs(grid, i, j, row, col);
    			}
    		}
    	}
    	
    	return nums;
    }
    
    private void dfs(char[][] grid, int r, int c, int row, int col)
    {
    	grid[r][c] = '0';
    	
    	if (r + 1 < row && grid[r + 1][c] == '1')
    	{
    		dfs(grid, r + 1, c, row, col);
    	}
    	
    	if (r - 1 >= 0 && grid[r - 1][c] == '1')
    	{
    		dfs(grid, r - 1, c, row, col);
    	}
    	
    	if (c + 1 < col && grid[r][c + 1] == '1')
    	{
    		dfs(grid, r, c + 1, row, col);
    	}
    	
    	if (c - 1 >= 0 && grid[r][c - 1] == '1')
    	{
    		dfs(grid, r, c - 1, row, col);
    	}
    	
    	return;
    }
}