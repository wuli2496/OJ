class Node 
{
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() 
    {
    	
    }
    
    public Node(int _val) 
    {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) 
    {
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
	
	private void connect(Node node, Node parent)
	{
		if (node == null)
		{
			return;
		}
		
		if (parent != null)
		{
			node.next = getNextNode(node, parent);
		}
		
		connect(node.right, node);
		connect(node.left, node);
	}
	
	private Node getNextNode(Node curNode, Node parent)
	{
		if (curNode == parent.left && parent.right != null)
		{
			return parent.right;
		}
		
		parent = parent.next;
		
		while (parent != null)
		{
			if (parent.left != null)
			{
				return parent.left;
			}
			
			if (parent.right != null)
			{
				return parent.right;
			}
			
			parent = parent.next;
		}
		
		return null;
	}
}