class Node 
{
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}
    
    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};

class Solution 
{
	public Node connect(Node root) 
	{
        connect(root, null);
        return root;
    }
	
	private Node connect(Node node, Node next)
	{
		if (node == null)
		{
			return next;
		}
		
		if (next != null)
		{
			node.next = next;
			next = next.left;
		}
		
		Node rightNode = connect(node.right, next);
		connect(node.left, rightNode);
		
		return node;
	}
}