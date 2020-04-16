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
    	return bfsScan(grid);
    }
    
    private int bfsScan(char[][] grid)
    {
    	int row = grid.length;
    	int col = 0;
    	if (row > 0)
    	{
    		col = grid[0].length;
    	}
    	
    	for (int i = 0; i < row; ++i)
    	{
    		for (int j = 0; j < col; ++j)
    		{
    			if (grid[i][j] == '1')
    			{
    				grid[i][j] = (char)-1;
    			}
    		}
    	}
    	
    	int componentNum = 0;
    	for (int i = 0; i < row; ++i)
    	{
    		for (int j = 0; j < col; ++j)
    		{
    			if (grid[i][j] == (char)-1)
    			{
    				componentNum = componentNum + 1;
    				grid[i][j] = (char)-2;
    				floodFill(row, col, grid, componentNum);
    			}
    		}
    	}
    	
    	return componentNum;
    }
    
    private void floodFill(int row, int col, char[][] grid, int compoentNum)
    {
    	int[] dx = {0, -1, 0, 1};
    	int[] dy = {-1, 0, 1, 0};
    	
    	int nums = 0;
    	do {
    		nums = 0;
    		for (int i = 0; i < row; ++i)
    		{
    			for (int j = 0; j < col; ++j)
    			{
    				if (grid[i][j] == (char)-2)
    				{
    					nums = nums + 1;
    					grid[i][j] = (char)compoentNum;
    					for (int k = 0; k < dx.length; ++k)
    					{
    						int x = i + dx[k];
    						int y = j + dy[k];
    						if (x < 0 || x >= row || y <0 || y >= col || grid[x][y] != (char)-1)
    						{
    							continue;
    						}
    						grid[x][y] = (char)-2;
    					}
    				}
    			}
    		}
    	} while (nums != 0);
    }
    
}