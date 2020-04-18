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
	class DisjointSet
	{
		public DisjointSet(char[][] grid)
		{
			int row = grid.length;
			int col = 0;
			if (row > 0)
			{
				col = grid[0].length;
			}
			
			parent = new int[row * col];
			rank = new int[row * col];
			
			for (int i = 0; i < row; ++i)
			{
				for (int j = 0; j < col; ++j)
				{
					if (grid[i][j] == '1')
					{
						parent[i * col + j] = i * col + j;
						++count;
					}
					
					rank[i * col + j] = 0;
				}
				
			}
		}
		
		public int find(int x)
		{
			return (parent[x] == x) ? x : (parent[x] = find(parent[x]));
		}
		
		public void union(int x, int y)
		{
			int px = find(x);
			int py = find(y);
			
			if (px != py)
			{
				if (rank[px] > rank[py])
				{
					parent[py] = px;
				}
				else if (rank[px] < rank[py])
				{
					parent[px] = py;
				}
				else 
				{
					parent[py] = px;
					++rank[px];
				}
				
				--count;
			}
		}
		
		
		public int getCount()
		{
			return count;
		}
		
		private int[] parent;
		private int[] rank;
		private int count;
	}
	
    public int numIslands(char[][] grid) 
    {
    	int row = grid.length; 
    	int col = 0;
    	if (row > 0)
    	{
    		col = grid[0].length;
    	}
    	
    	DisjointSet disjointSet = new DisjointSet(grid);
    	for (int i = 0; i < row; ++i)
    	{
    		for (int j = 0; j < col; ++j)
    		{
    			if (grid[i][j] == '1')
    			{
    				grid[i][j] = '0';
    				if (i - 1 >= 0 && grid[i - 1][j] == '1')
    				{
    					disjointSet.union(i * col + j, (i - 1) * col + j);
    				}
    				
    				if (i + 1 < row && grid[i + 1][j] == '1')
    				{
    					disjointSet.union(i * col + j, (i + 1) * col + j);
    				}
    				
    				if (j - 1 >= 0 && grid[i][j - 1] == '1')
    				{
    					disjointSet.union(i * col + j, i * col + j - 1);
    				}
    				
    				if (j + 1 < col && grid[i][j + 1] == '1')
    				{
    					disjointSet.union(i * col + j, i * col + j + 1);
    				}
    			}
    		}
    	}
    	
    	return disjointSet.getCount();
    }
}