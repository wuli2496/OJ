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

class Solution 
{	
	public Node flatten(Node head) {
		if (head == null) {
			return head;
		}
		
		Node pseduoHead = new Node(0, null, head, null);
		flatternDfs(pseduoHead, head);
		pseduoHead.next.prev = null;
        return pseduoHead.next;
    }
	
	private Node flatternDfs(Node prev, Node cur) {
		if (cur == null) {
			return prev;
		}
		
		prev.next = cur;
		cur.prev = prev;
		
		Node tmpNode = cur.next;
		Node tailNode = flatternDfs(cur, cur.child);
		cur.child = null;
		
		return flatternDfs(tailNode, tmpNode);
	}
}
