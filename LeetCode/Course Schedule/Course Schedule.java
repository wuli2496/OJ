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
import java.util.List;

class Solution 
{
	enum Color
	{
		WHITE_COLOR,
		GRAY_COLOR,
		BLACK_COLOR
	}
	
    public boolean canFinish(int numCourses, int[][] prerequisites) 
    {
    	List<List<Integer>> adjList = new ArrayList<>();
    	for (int i = 0; i < numCourses; ++i)
    	{
    		adjList.add(new ArrayList<>());
    	}
    	
    	Color[] flags = new Color[numCourses];
    	for (int i = 0; i < numCourses; ++i)
    	{
    		flags[i] = Color.WHITE_COLOR;
    	}
    	
    	for (int[] prerequisite: prerequisites)
    	{
    		adjList.get(prerequisite[0]).add(prerequisite[1]);
    	}
    	
    	for (int i = 0; i < numCourses; ++i)
    	{
    		if (flags[i] == Color.WHITE_COLOR)
    		{
    			if (hasCycleDfs(adjList, flags, i))
    			{
    				return false;
    			}
    		}
    	}
    	
    	return true;
    }
    
    private boolean hasCycleDfs(List<List<Integer>> adjList, Color[] flags, int pos)
    {
    	flags[pos] = Color.GRAY_COLOR;
    	
    	for (int v : adjList.get(pos))
    	{
    		if (flags[v] == Color.WHITE_COLOR)
    		{
    			if (hasCycleDfs(adjList, flags, v))
    			{
    				return true;
    			}
    		}
    		else if (flags[v] == Color.GRAY_COLOR)
    		{
    			return true;
    		}
    	}
    	
    	flags[pos] = Color.BLACK_COLOR;
    	return false;
    }
}