/**
Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.

According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”

Given the following binary tree:  root = [3,5,1,6,2,0,8,null,null,7,4]


 

Example 1:

Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
Output: 3
Explanation: The LCA of nodes 5 and 1 is 3.
Example 2:

Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
Output: 5
Explanation: The LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition.
 */
import java.util.HashMap;
import java.util.Map;

class TreeNode 
{
	  int val;
	  TreeNode left;
	  TreeNode right;
	  TreeNode(int x) { val = x; }
}

class Solution 
{
	private int index = 0;
	private Map<TreeNode, Integer> nodeToIntMap = new HashMap<>();
	private Map<Integer, TreeNode> intToNodeMap = new HashMap<>();
	private Map<Integer, Integer> parentMap = new HashMap<>();
	private Map<Integer, Integer> levelMap = new HashMap<>();
	private int[][] dp;
	
	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) 
	{
		preProcess(root, 0, -1);
		init();
		
		int u = nodeToIntMap.get(p);
		int v = nodeToIntMap.get(q);
		int ans = query(u, v);
		
		return intToNodeMap.get(ans);
    }
	
	private int preProcess(TreeNode root, int depth, int p)
	{
		if (root == null)
		{
			return 0;
		}
		nodeToIntMap.put(root, index);
		intToNodeMap.put(index, root);
		levelMap.put(index, depth);
		parentMap.put(index, p);
		
		int leftDepth = 0, rightDepth = 0;
		if (root.left != null)
		{
			++index;
			leftDepth = preProcess(root.left, depth + 1, nodeToIntMap.get(root));
		}
		
		if (root.right != null)
		{
			++index;
			rightDepth = preProcess(root.right, depth + 1, nodeToIntMap.get(root));
		}
		
		return 1 + Math.max(leftDepth, rightDepth);
	}
	
	private void init()
	{
		int n = index + 1;
		int m = 0;
		while ((1 << m) <= n)
		{
			++m;
		}
		dp = new int[n][m];
		for (int i = 0; i < n; ++i)
		{
			for (int j = 0; (1 << j) < n; ++j)
			{
				dp[i][j] = -1;
			}
		}
		
		for (int i = 0; i < n; ++i)
		{
			dp[i][0] = parentMap.get(i);
		}
		
		for (int j = 1; (1 << j) < n; ++j)
		{
			for (int i = 0; i < n; ++i)
			{
				if (dp[i][j - 1] != -1)
				{
					dp[i][j] = dp[dp[i][j - 1]][j - 1];
				}
			}
		}
	}
	
	private int query(int u, int v)
	{
		int tmp = 0;
		if (levelMap.get(u) < levelMap.get(v))
		{
			tmp = u; u = v; v = tmp;
		}
		
		int log = 0;
		for (log = 1; (1 << log) <= levelMap.get(u); ++log);
		--log;
		
		for (int i = log; i >= 0; --i)
		{
			if (levelMap.get(u) - (1 << i) >= levelMap.get(v))
			{
				
				u = dp[u][i];
			}
		}
		
		if (u == v)
		{
			return u;
		}
		
		for (int i = log; i >= 0; --i)
		{
			if (dp[u][i] != -1 && dp[u][i] != dp[v][i])
			{
				u = dp[u][i];
				v = dp[v][i];
			}
		}
		
		return parentMap.get(u);
	}
}