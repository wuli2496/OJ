
 class TreeNode 
 {
	  int val;
	  TreeNode left;
	  TreeNode right;
	  TreeNode(int x) { val = x; }
 }
 
class Solution 
{
    public int sumNumbers(TreeNode root) 
    {
    	if (root == null)
    	{
    		return 0;
    	}
    	
        return sum(root, 0);
    }
    
    private int sum(TreeNode root, int v)
    {
    	if (root.left == null && root.right == null)
    	{
    		return v * 10 + root.val;
    	}
    	
    	int ans = 0;
    	if (root.left != null)
    	{
    		int leftSum = sum(root.left, v * 10 + root.val);
    		ans += leftSum;
    	}
    	
    	if (root.right != null)
    	{
    		int rightSum = sum(root.right, v * 10 + root.val);
    		ans += rightSum;
    	}
    	
    	return ans;
    }
}