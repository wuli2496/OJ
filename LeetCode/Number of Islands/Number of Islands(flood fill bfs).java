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

import java.util.LinkedList;
import java.util.Queue;

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
    				bfs(grid, i, j, row, col);
    			}
    		}
    	}
    	
    	return nums;
    }
    
    private void bfs(char[][] grid, int r, int c, int row, int col)
    {
    	grid[r][c] = '0';
    	Queue<Integer> queue = new LinkedList<>();
    	queue.add(r * col + c);
    	
    	while (!queue.isEmpty())
    	{
    		int pos = queue.poll();
    		int curRow = pos / col;
    		int curCol = pos % col;
    		
    		for (int i = 0; i < dx.length; ++i)
    		{
    			int x = curRow + dx[i];
    			int y = curCol + dy[i];
    			
    			if (x < 0 || x >= row || y < 0 || y >= col || grid[x][y] == '0')
    			{
    				continue;
    			}
    			
    			grid[x][y] = '0';
    			queue.add(x * col + y);
    		}
    	}
    	
    	return;
    }
    
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};
}