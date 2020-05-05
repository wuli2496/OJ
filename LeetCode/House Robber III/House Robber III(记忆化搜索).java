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
	public int rob(TreeNode root) 
	{
		Map<TreeNode, Integer> memo = new HashMap<>();
		
		return robInternal(root, memo);
    }
	
	private int robInternal(TreeNode node, Map<TreeNode, Integer> memo)
	{
		if (node == null)
		{
			return 0;
		}
		
		if (memo.containsKey(node))
		{
			return memo.get(node);
		}
		
		int money = node.val;
		if (node.left != null)
		{
			money += robInternal(node.left.left, memo) + robInternal(node.left.right, memo);
		}
		
		if (node.right != null)
		{
			money += robInternal(node.right.left, memo) + robInternal(node.right.right, memo);
		}
		
		int result = Math.max(money, robInternal(node.left, memo) + robInternal(node.right, memo));
		memo.put(node, result);
		
		return result;
	}
}
