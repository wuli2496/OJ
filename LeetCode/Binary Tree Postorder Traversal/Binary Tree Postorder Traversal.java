import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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

 class Solution {
     public List<Integer> postorderTraversal(TreeNode root) {
         List<Integer> result = new ArrayList<>();
         if (root == null) {
             return result;
         }

         Stack<TreeNode> stack = new Stack<>();
         TreeNode prev = null;
         stack.add(root);
         while (!stack.isEmpty()) {
             TreeNode curNode = stack.peek();

             if ((curNode.left == null && curNode.right == null) || (prev != null && prev == curNode.right)
             || (prev != null && curNode.right == null && curNode.left == prev)) {
                 curNode = stack.pop();
                 result.add(curNode.val);
                 prev = curNode;
             } else {

                 if (curNode.right != null) {
                     stack.add(curNode.right);
                 }

                 if (curNode.left != null) {
                     stack.add(curNode.left);
                 }
             }
         }

         return result;
     }
 }
