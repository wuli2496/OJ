
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
		int robVal = dfs(root, false);
		int norobVal = dfs(root, true);
		
		return Math.max(robVal, norobVal);
    }
	
	private int dfs(TreeNode node, boolean parentRob)
	{
		if (node == null)
		{
			return 0;
		}
		
		if (parentRob)
		{
			return dfs(node.left, !parentRob) + dfs(node.right, !parentRob);
		}
		else 
		{
			int robVal = node.val + dfs(node.left, !parentRob) + dfs(node.right, !parentRob);
			int noRobVal = dfs(node.left, parentRob) + dfs(node.right, parentRob);
			return Math.max(robVal, noRobVal);
		}
	}
}
