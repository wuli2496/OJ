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

class Node
{
    TreeNode treeNode;
    int sum;
    List<Integer> row;
}

class Solution
{
    public List<List<Integer>> pathSum(TreeNode root, int sum)
    {
        if (root == null)
        {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        Node node = new Node();
        node.treeNode = root;
        node.sum = root.val;
        node.row = new ArrayList<>();
        node.row.add(root.val);
        queue.add(node);

        while (!queue.isEmpty())
        {
            Node curNode = queue.poll();
            if (curNode.treeNode.left == null && curNode.treeNode.right == null)
            {
                if (curNode.sum == sum)
                {
                    result.add(curNode.row);
                }
            }

            if (curNode.treeNode.left != null)
            {
                Node newNode = new Node();
                newNode.treeNode = curNode.treeNode.left;
                newNode.sum = curNode.sum + curNode.treeNode.left.val;
                newNode.row = new ArrayList<>();
                newNode.row.addAll(curNode.row);
                newNode.row.add(curNode.treeNode.left.val);

                queue.add(newNode);
            }

            if (curNode.treeNode.right != null)
            {
                Node newNode = new Node();
                newNode.treeNode = curNode.treeNode.right;
                newNode.sum = curNode.sum + curNode.treeNode.right.val;
                newNode.row = new ArrayList<>();
                newNode.row.addAll(curNode.row);
                newNode.row.add(curNode.treeNode.right.val);

                queue.add(newNode);
            }
        }

        return result;
    }
}