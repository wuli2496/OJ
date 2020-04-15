import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

class TreeNode 
{
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

class Solution 
{
    public List<Integer> rightSideView(TreeNode root) 
    {
    	Map<Integer, Integer> depthValue = new HashMap<>();
    	int maxDepth = -1;
    	Queue<TreeNode> nodeQueue = new LinkedList<>();
    	Queue<Integer> depthQueue = new LinkedList<>();
    	nodeQueue.add(root);
    	depthQueue.add(0);
    	
    	while (!nodeQueue.isEmpty())
    	{
    		TreeNode node = nodeQueue.remove();
    		Integer depth = depthQueue.remove();
    		
    		if (node != null)
    		{
    			maxDepth = Math.max(maxDepth, depth);
    			
    			
    			depthValue.put(depth, node.val);
    			nodeQueue.add(node.left);
    			nodeQueue.add(node.right);
    			depthQueue.add(depth + 1);
    			depthQueue.add(depth + 1);
    		}
    	}
    	
    	List<Integer> ans = new ArrayList<Integer>();
    	for (int i = 0; i <= maxDepth; ++i)
    	{
    		ans.add(depthValue.get(i));
    	}
    	
    	return ans;
    }
    
}