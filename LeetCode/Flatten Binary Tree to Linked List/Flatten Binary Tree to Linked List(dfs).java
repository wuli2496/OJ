class TreeNode 
{
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode(int x) { val = x; }
}

class Solution 
{
    public void flatten(TreeNode root) 
    {
        if (root == null)
        {
        	return;
        }
        
        TreeNode node = root.left;
        if (node != null)
        {
        	while (node.right != null)
        	{
        		node = node.right;
        	}
        	
        	node.right = root.right;
        	root.right = root.left;
        	root.left = null;
        }
        
        flatten(root.right);
    }
}