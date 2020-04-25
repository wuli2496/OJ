
/**
Given a complete binary tree, count the number of nodes.

Note:

Definition of a complete binary tree from Wikipedia:
In a complete binary tree every level, except possibly the last, is completely filled, and all nodes in the last level are as far left as possible. It can have between 1 and 2h nodes inclusive at the last level h.

Example:

Input: 
    1
   / \
  2   3
 / \  /
4  5 6

Output: 6
 */



class TreeNode 
{
	  int val;
	  TreeNode left;
	  TreeNode right;
	  TreeNode(int x) { val = x; }
}

class Solution 
{
	public int countNodes(TreeNode root) 
	{
		if (root == null)
		{
			return 0;
		}
		
		int depth = getDepth(root);
		if (depth == 0)
		{
			return 1;
		}
		
		int left = 0;
		int right = (int)Math.pow(2, depth) - 1;
		int pivot;
		
		while (left <= right)
		{
			pivot = left + (right - left) / 2;
			if (exist(pivot, depth, root))
			{
				left = pivot + 1;
			}
			else 
			{
				right = pivot - 1;
			}
		}
		
		return (int)Math.pow(2, depth) - 1 + left;
    }
	
	
	private boolean exist(int index, int depth, TreeNode node)
	{
		int left = 0;
		int right = (int)Math.pow(2, depth) - 1;
		int pivot;
		
		for (int i = 0; i < depth; ++i)
		{
			pivot = (left + right) / 2;
			if (index <= pivot)
			{
				right = pivot;
				node = node.left;
			}
			else
			{
				left = pivot + 1;
				node = node.right;
			}
		}
		
		return node != null;
	}
	
	private int getDepth(TreeNode root)
	{
		int d = 0;
		while (root.left != null)
		{
			root = root.left;
			++d;
		}
		
		return d;
	}
}