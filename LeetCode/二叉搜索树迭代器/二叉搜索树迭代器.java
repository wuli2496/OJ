/*先中序遍历*/
import java.util.ArrayList;
import java.util.List;

class TreeNode 
{
	int val;
	TreeNode left;
	TreeNode right;
	TreeNode(int x) { val = x; }
}

class BSTIterator 
{
    public BSTIterator(TreeNode root)
    {
    	ans = new ArrayList<Integer>();
    	init(root);
    	index = 0;
    }
    
    /** @return the next smallest number */
    public int next() 
    {
    	int result = ans.get(index);
    	++index;
    	
    	return result;
    }
    
    /** @return whether we have a next smallest number */
    public boolean hasNext() 
    {
    	return index < ans.size();
    }
    
    private void init(TreeNode root)
    {
    	if (root == null)
    	{
    		return;
    	}
    	
    	init(root.left);
    	ans.add(root.val);
    	init(root.right);
    	
    	return;
    }
    
    private List<Integer> ans;
    private int index;
}