/**
There are a total of n courses you have to take, labeled from 0 to n-1.

Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]

Given the total number of courses and a list of prerequisite pairs, return the ordering of courses you should take to finish all courses.

There may be multiple correct orders, you just need to return one of them. If it is impossible to finish all courses, return an empty array.

Example 1:

Input: 2, [[1,0]] 
Output: [0,1]
Explanation: There are a total of 2 courses to take. To take course 1 you should have finished   
             course 0. So the correct course order is [0,1] .
Example 2:

Input: 4, [[1,0],[2,0],[3,1],[3,2]]
Output: [0,1,2,3] or [0,2,1,3]
Explanation: There are a total of 4 courses to take. To take course 3 you should have finished both     
             courses 1 and 2. Both courses 1 and 2 should be taken after you finished course 0. 
             So one correct course order is [0,1,2,3]. Another correct ordering is [0,2,1,3] .
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class Solution 
{
	enum Color
	{
		WHITE_COLOR,
		GRAY_COLOR,
		BLACK_COLOR
	}
	
	public int[] findOrder(int numCourses, int[][] prerequisites) 
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
    		adjList.get(prerequisite[1]).add(prerequisite[0]);
    	}
    	
    	List<Integer> ans = new LinkedList<>();
    	boolean hasCycle = false;
    	for (int i = 0; i < numCourses; ++i)
    	{
    		if (flags[i] == Color.WHITE_COLOR)
    		{
    			hasCycle = hasCycleDfs(adjList, flags, i, ans);
    			if (hasCycle)
    			{
    				break;
    			}
    		}
    	}
    	
    	if (hasCycle)
    	{
    		return new int[] {};
    	}
    	
    	int[] result = new int[numCourses];
    	for (int i = 0; i < numCourses; ++i)
    	{
    		result[i] = ans.get(i);
    	}
    	
    	return result;
    }
    
    private boolean hasCycleDfs(List<List<Integer>> adjList, Color[] flags, int pos, List<Integer> ans)
    {
    	flags[pos] = Color.GRAY_COLOR;
    	
    	for (int v : adjList.get(pos))
    	{
    		if (flags[v] == Color.WHITE_COLOR)
    		{
    			if (hasCycleDfs(adjList, flags, v, ans))
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
    	ans.add(0, pos);
    	return false;
    }
}