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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class TreeNode 
{
	  int val;
	  TreeNode left;
	  TreeNode right;
	  TreeNode(int x) { val = x; }
}

class UnionFindSet
{	
	Map<TreeNode, Integer> parent;
	Map<Integer, TreeNode> intNodeMap;
	Map<TreeNode, Integer> nodeIntMap;
	
	public UnionFindSet()
	{
		parent = new HashMap<>();
		intNodeMap = new HashMap<>();
		nodeIntMap = new HashMap<>();
	}
	
	public void makeSet(TreeNode node, int cnt)
	{
		parent.put(node, cnt);
		intNodeMap.put(cnt, node);
		nodeIntMap.put(node, cnt);
	}
	
	public void union(TreeNode u, TreeNode v)
	{
		Integer pu = find(u);
		Integer pv = find(v);
		
		if (pu < pv)
		{
			parent.put(v, pu);
		}
		else
		{
			parent.put(u, pv);
		}

	}
	
	public Integer find(TreeNode node)
	{
		Integer val = parent.get(node);
		if (intNodeMap.get(val) == node)
		{
			return val;
		}
		else
		{
			Integer father = find(intNodeMap.get(val));
			parent.put(node, father);
			
			return father;
		}
	}
	
	public Integer getInt(TreeNode node)
	{
		return nodeIntMap.get(node);
	}
	
	public TreeNode getNode(int index)
	{
		return intNodeMap.get(index);
	}
}

class Solution 
{
	private int index = 0;
	
	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) 
	{
		if (root == null)
		{
			return null;
		}
		
		UnionFindSet unionFindSet = new UnionFindSet();
		Map<TreeNode, TreeNode> query = new HashMap<>();
		query.put(p, q);
		query.put(q, p);
		Map<Integer, Integer> ans = new HashMap<>();
		Map<Integer, Boolean> vis = new HashMap<>();
		index = 0;
		Set<Integer> queryAns = new HashSet<>();
		
		tarjan(root, query, vis, ans, unionFindSet, queryAns);
		
		int cnt = queryAns.iterator().next();
		
		return unionFindSet.getNode(cnt);
    }
	
	private void tarjan(TreeNode root, Map<TreeNode, TreeNode> query, Map<Integer, Boolean> vis, Map<Integer, Integer> ans, UnionFindSet unionFindSet, Set<Integer> queryAns)
	{
		if (root == null)
		{
			return;
		}
		
		int tmp = index;
		unionFindSet.makeSet(root, tmp);
		int set = unionFindSet.find(root);
		ans.put(set, tmp);
		if (root.left != null)
		{
			++index;
			tarjan(root.left, query, vis, ans, unionFindSet, queryAns);
			unionFindSet.union(root, root.left);
			ans.put(unionFindSet.find(root), tmp);
		}
		
		if (root.right != null)
		{
			++index;
			tarjan(root.right, query, vis, ans, unionFindSet, queryAns);
			unionFindSet.union(root, root.right);
			ans.put(unionFindSet.find(root), tmp);
		}
		
		vis.put(tmp, true);
		if (query.containsKey(root))
		{
			Integer v = unionFindSet.getInt(query.get(root));
			if (v != null && vis.get(v) != null && vis.get(v) == true)
			{
				//System.out.println("u:" + root.val + " v: " + v);
				//System.out.println("node:" + unionFindSet.getNode(v).val);
				int p = unionFindSet.find(unionFindSet.getNode(v));
				queryAns.add(ans.get(p));
			}
		}
	}
}