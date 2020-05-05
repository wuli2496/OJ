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
		int[] result = robInternal(root);
		
		return Math.max(result[0], result[1]);
    }
	
	private int[] robInternal(TreeNode node)
	{
		if (node == null)
		{
			return new int[2];
		}
		
		int[] result = new int[2];
		int[] left = robInternal(node.left);
		int[] right = robInternal(node.right);
		
		result[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
		result[1] = node.val + left[0] + right[0];
		
		return result;
	}
}
