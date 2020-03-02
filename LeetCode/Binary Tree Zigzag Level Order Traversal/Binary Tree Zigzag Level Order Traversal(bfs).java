import java.util.*;

class TreeNode
{
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x)
    {
        val = x;
    }
}

public class Solution
{
    public List<List<Integer>> zigzagLevelOrder(TreeNode root)
    {
        if (root == null)
        {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();

        boolean leftToRight = true;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty())
        {
            List<Integer> row = new ArrayList<>(Collections.nCopies(queue.size(), new Integer(0)));
            int size = queue.size();
            for (int i = 0; i < size; ++i)
            {
                TreeNode curNode = queue.poll();
                int index = (leftToRight ? i : size - 1 - i);
                row.set(index, curNode.val);

                if (curNode.left != null)
                {
                    queue.add(curNode.left);
                }

                if (curNode.right != null)
                {
                    queue.add(curNode.right);
                }
            }

            leftToRight = !leftToRight;
            result.add(row);
        }

        return result;
    }
}