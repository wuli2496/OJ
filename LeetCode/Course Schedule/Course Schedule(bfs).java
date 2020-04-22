/**
here are a total of numCourses courses you have to take, labeled from 0 to numCourses-1.

Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]

Given the total number of courses and a list of prerequisite pairs, is it possible for you to finish all courses?

 

Example 1:

Input: numCourses = 2, prerequisites = [[1,0]]
Output: true
Explanation: There are a total of 2 courses to take. 
             To take course 1 you should have finished course 0. So it is possible.
Example 2:

Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
Output: false
Explanation: There are a total of 2 courses to take. 
             To take course 1 you should have finished course 0, and to take course 0 you should
             also have finished course 1. So it is impossible.
 

Constraints:

The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how a graph is represented.
You may assume that there are no duplicate edges in the input prerequisites.
1 <= numCourses <= 10^5
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution 
{
    public boolean canFinish(int numCourses, int[][] prerequisites) 
    {
    	int[] inorders = new int[numCourses];
    	
    	List<List<Integer>> adjList = new ArrayList<>();
    	for (int i = 0; i < numCourses; ++i)
    	{
    		adjList.add(new ArrayList<>());
    	}
    	
    	for (int[] prerequisite : prerequisites)
    	{
    		int u = prerequisite[0];
    		int v = prerequisite[1];
    		adjList.get(u).add(v);
    		++inorders[v];
    	}
    	
    	int ans = numCourses;
    	Queue<Integer> queue = new LinkedList<>();
    	for (int i = 0; i < numCourses; ++i)
    	{
    		if (inorders[i] == 0)
    		{
    			queue.add(i);
    		}
    	}
    	
    	while (!queue.isEmpty())
    	{
    		int u = queue.poll();
    		--ans;
    		for (int v : adjList.get(u))
    		{
    			--inorders[v];
    			if (inorders[v] == 0)
    			{
    				queue.add(v);
    			}
    		}
    	}
    	
    	return ans == 0;
    }
}