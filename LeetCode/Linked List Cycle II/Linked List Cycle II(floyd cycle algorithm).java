class ListNode {
	int val;
	ListNode next;
	
	public ListNode(int x) {
		val = x;
		next = null;
	}
}
 
public class Solution {
	public ListNode detectCycle(ListNode head) {
		if (head == null) {
			return null;
		}
		
        ListNode hare = head;
        ListNode tortoise = head;
        
        while (hare != null) {
        	tortoise = tortoise.next;
        	if (hare.next != null) {
        		hare = hare.next.next;
        	} else {
        		return null;
        	}
        	
        	if (tortoise == hare) {
        		ListNode ptr = head;
        		while (ptr != tortoise) {
        			ptr = ptr.next;
        			tortoise = tortoise.next;
        		}
        		
        		return ptr;
        	}
        }
        
        return null;
    }
}
