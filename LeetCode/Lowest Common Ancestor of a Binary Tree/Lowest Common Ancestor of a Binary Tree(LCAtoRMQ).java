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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	private Map<Integer, Integer> firstVisit = new HashMap<>();
	private List<Integer> eulerSeq = new ArrayList<>();
	
	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) 
	{
		preProcess(root, 0, -1);
		
		int n = 2 * index + 1;
		int m = 0;
		while ((1 << m) <= n)
		{
			++m;
		}
		int[][] sparseTable = new int[n][m];
		for (int i = 0; i < n; ++i)
		{
			sparseTable[i][0] = eulerSeq.get(i);
		}
		
		
		for (int j = 1; (1 << j) < n; ++j)
		{
			for (int i = 0; i + (1 << j) - 1 < n; ++i)
			{
				int h1 = levelMap.get(sparseTable[i][j - 1]);
				int h2 = levelMap.get(sparseTable[i + (1 << (j - 1))][j - 1]);
				if (h1 <= h2)
				{
					sparseTable[i][j] = sparseTable[i][j - 1];
				}
				else 
				{
					sparseTable[i][j] = sparseTable[i + (1 << (j - 1))][j - 1];
				}
			}
		}
		
		
		int u = nodeToIntMap.get(p);
		int v = nodeToIntMap.get(q);
		
		int uPos = firstVisit.get(u);
		int vPos = firstVisit.get(v);
		
		if (uPos > vPos)
		{
			int tmp = uPos; uPos = vPos; vPos = tmp;
		}
		
		int k = 0;
		while ((1 << (k + 1)) <= vPos - uPos + 1)
		{
			++k;
		}
		
		int h1 = levelMap.get(sparseTable[uPos][k]);
		int h2 = levelMap.get(sparseTable[vPos - (1 << k) + 1][k]);
		int ans = 0;
		if (h1 <= h2)
		{
			ans = sparseTable[uPos][k];
		}
		else 
		{
			ans = sparseTable[vPos - (1 << k) + 1][k];
		}
		
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
		firstVisit.put(index, eulerSeq.size());
		int tmp = index;
		eulerSeq.add(tmp);
		
		int leftDepth = 0, rightDepth = 0;
		if (root.left != null)
		{
			++index;
			leftDepth = preProcess(root.left, depth + 1, nodeToIntMap.get(root));
			eulerSeq.add(tmp);
		}
		
		if (root.right != null)
		{
			++index;
			rightDepth = preProcess(root.right, depth + 1, nodeToIntMap.get(root));
			eulerSeq.add(tmp);
		}
		
		return 1 + Math.max(leftDepth, rightDepth);
	}
}