import java.util.Stack;

class Node {
    public int val;
    public Node prev;
    public Node next;
    public Node child;

    public Node() {}

    public Node(int _val, Node _prev, Node _next, Node _child) {
        val = _val;
        prev = _prev;
        next = _next;
        child = _child;
    }
}

class Solution {
    public Node flatten(Node head) {
        if (head == null) {
            return head;
        }

        Node pseduoNode = new Node(0, null, head, null);
        Stack<Node> stack = new Stack<>();
        Node prev = pseduoNode;
        Node cur = null;
        stack.push(head);

        while (!stack.isEmpty()) {
            cur = stack.pop();
            prev.next = cur;
            cur.prev = prev;

            if (cur.next != null) {
                stack.push(cur.next);
            }

            if (cur.child != null) {
                stack.push(cur.child);
                cur.child = null;
            }

            prev = cur;
        }

        pseduoNode.next.prev = null;

        return pseduoNode.next;
    }
}
