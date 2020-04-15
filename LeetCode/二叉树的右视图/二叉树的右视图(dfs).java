import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

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
    	Stack<TreeNode> nodeStack = new Stack<>();
    	Stack<Integer> depthStack = new Stack<>();
    	
    	nodeStack.add(root);
    	depthStack.add(0);
    	
    	while (!nodeStack.isEmpty())
    	{
    		TreeNode node = nodeStack.pop();
    		Integer depth = depthStack.pop();
    		
    		if (node != null)
    		{
    			maxDepth = Math.max(maxDepth, depth);
    			
    			if (!depthValue.containsKey(maxDepth))
    			{
    				depthValue.put(maxDepth, node.val);
    			}
    			
    			nodeStack.add(node.left);
    			nodeStack.add(node.right);
    			depthStack.add(depth + 1);
    			depthStack.add(depth + 1);
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