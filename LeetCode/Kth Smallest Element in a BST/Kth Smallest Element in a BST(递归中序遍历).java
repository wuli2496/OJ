/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public int kthSmallest(TreeNode root, int k) 
	{
		List<Integer> ans = new ArrayList<>();
		inOrder(root, ans);
		
		return ans.get(k - 1);
    }
	
	private void inOrder(TreeNode node, List<Integer> ans)
	{
		if (node == null)
		{
			return;
		}
		
		inOrder(node.left, ans);
		ans.add(node.val);
		inOrder(node.right, ans);
	}
}