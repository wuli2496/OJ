import java.util.HashMap;
import java.util.Map;

class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
 }
 
public class Solution {
	public int pathSum(TreeNode root, int sum) {
		Map<Integer, Integer> prefixSumMap = new HashMap<>();
		prefixSumMap.put(0, 1);
		
        return dfs(root, prefixSumMap, sum, 0);
    }
	
	private int dfs(TreeNode node, Map<Integer, Integer> prefixMap, int target, int sum) {
		if (node == null) {
			return 0;
		}
		
		int curSum = sum + node.val;
		
		int res = prefixMap.getOrDefault(curSum - target, 0);
		prefixMap.put(curSum, prefixMap.getOrDefault(curSum, 0) + 1);
		
		if (node.left != null) {
			res += dfs(node.left, prefixMap, target, curSum);
		}
		
		if (node.right != null) {
			res += dfs(node.right, prefixMap, target, curSum);
		}
		
		prefixMap.put(curSum, prefixMap.get(curSum) - 1);
		return res;
	}
}
