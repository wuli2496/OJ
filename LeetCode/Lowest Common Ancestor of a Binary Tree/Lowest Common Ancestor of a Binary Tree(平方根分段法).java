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
	private int index = 1;
	Map<TreeNode, Integer> nodeToIntMap = new HashMap<>();
	Map<Integer, TreeNode> intToNodeMap = new HashMap<>();
	Map<Integer, Integer> parentMap = new HashMap<>();
	Map<Integer, Integer> levelMap = new HashMap<>();
	Map<Integer, Integer> pMap = new HashMap<>();
	
	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) 
	{
		int treeDepth = preProcess(root, 0, -1);
		int sections = (int)Math.sqrt(treeDepth);
		dfs(1, sections);
		
		int x = nodeToIntMap.get(p);
		int y = nodeToIntMap.get(q);
		
		
		while (pMap.get(x) != pMap.get(y))
		{
			if (levelMap.get(x) > levelMap.get(y))
			{
				x = pMap.get(x);
			}
			else 
			{
				y = pMap.get(y);
			}
		}
		
		while (x != y)
		{
			if (levelMap.get(x) > levelMap.get(y))
			{
				x =  parentMap.get(x);
			}
			else 
			{
				y = parentMap.get(y);
			}
		}
		
		return intToNodeMap.get(x);
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
	
	private void dfs(int u, int section)
	{
		if (levelMap.get(u) < section)
		{
			pMap.put(u, 1);
		}
		else if (levelMap.get(u) % section == 0)
		{
			pMap.put(u, parentMap.get(u));
		}
		else 
		{
			pMap.put(u, pMap.get(parentMap.get(u)));
		}
		
		TreeNode node = intToNodeMap.get(u);
		if (node != null)
		{
			if (node.left != null)
			{
				dfs(nodeToIntMap.get(node.left), section);
			}
			
			if (node.right != null)
			{
				dfs(nodeToIntMap.get(node.right), section);
			}
		}
	}
}