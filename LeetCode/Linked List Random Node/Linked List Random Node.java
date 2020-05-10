/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {

    /** @param head The linked list's head.
    Note that the head is guaranteed to be not null, so it contains at least one node. */
	public Solution(ListNode head) 
	{
		this.head = head;
	}

	/** Returns a random node's value. */
	public int getRandom() 
	{
		ListNode cur = head;
        int val = head.val;
        Random random = new Random();
        int count = 0;
        while(cur != null) {
            count++;
            if(random.nextInt(count) == 0) {
                val = cur.val;
            }
            cur = cur.next;
        }
        return val;
	}
	
	private ListNode head;
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(head);
 * int param_1 = obj.getRandom();
 */
