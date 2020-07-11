import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
}

class Solution {
    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        List<Integer> integers = null;

        while (!queue.isEmpty()) {
            int size = queue.size();
            integers = new ArrayList<>();
            while (size-- > 0) {
                Node curNode = queue.poll();
                integers.add(curNode.val);
                for (Node child : curNode.children) {
                    queue.add(child);
                }
            }

            result.add(integers);
        }

        return result;
    }
}
