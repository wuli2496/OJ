import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution 
{
	public List<Integer> findMinHeightTrees(int n, int[][] edges) 
	{
		List<Integer> ans = new ArrayList<>();
		if (n == 1)
		{
			ans.add(0);
			return ans;
		}
		else if (n == 1)
		{
			ans.add(0); ans.add(1);
			return ans;
		}
		
		int[] degree = new int[n];
		List<List<Integer>> adjList = new ArrayList<>();
		for (int i = 0; i < n; ++i)
		{
			adjList.add(new ArrayList<>());
		}
		
		for (int[] edge : edges)
		{
			int u = edge[0], v = edge[1];
			++degree[u]; ++degree[v];
			adjList.get(u).add(v);
			adjList.get(v).add(u);
		}
		
		Queue<Integer> queue = new LinkedList<>();
		for (int i = 0; i < n; ++i)
		{
			if (degree[i] == 1)
			{
				queue.add(i);
			}
		}
		
		int cnt = queue.size();
		while (n > 2)
		{
			n -= cnt;
			while (cnt-- > 0)
			{
				int u = queue.poll();
				degree[u] = 0;
				
				for (int v : adjList.get(u))
				{
					if (degree[v] != 0)
					{
						--degree[v];
						if (degree[v] == 1)
						{
							queue.add(v);
						}
					}
				}
			}
			cnt = queue.size();
		}
		
		while (!queue.isEmpty())
		{
			ans.add(queue.poll());
		}
		
		return ans;
    }
}