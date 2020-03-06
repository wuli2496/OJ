import java.util.ArrayList;
import java.util.List;

class TreeNode 
 {
	  int val;
	  TreeNode left;
	  TreeNode right;
	  TreeNode(int x) { val = x; }
 }
 
class Solution 
{
	 public List<Integer> preorderTraversal(TreeNode root) 
	 {
		 List<Integer> ans = new ArrayList<Integer>();
		 preOrder(root, ans);
		 
		 return ans;
	 }
	 
	 private void preOrder(TreeNode node, List<Integer> ans)
	 {
		 if (node == null)
		 {
			 return;
		 }
		 
		 ans.add(node.val);
		 preOrder(node.left, ans);
		 preOrder(node.right, ans);
	 }
}