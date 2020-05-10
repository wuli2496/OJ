import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class Solution 
{
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) 
    {
    	PriorityQueue<List<Integer>> pq = new PriorityQueue<>(k, (o1, o2)->{return o2.get(0) + o2.get(1) - (o1.get(0) + o1.get(1));});
    	
    	for (int i = 0; i < nums1.length; ++i)
    	{
    		for (int j = 0; j < nums2.length; ++j)
    		{
    			if (pq.size() == k && nums1[i] + nums2[j] >= pq.peek().get(0) + pq.peek().get(1))
    			{
    				break;
    			}
    			
    			if (pq.size() == k)
    			{
    				pq.poll();
    			}
    			
    			List<Integer> list = new ArrayList<>();
        		list.add(nums1[i]);
        		list.add(nums2[j]);
        		pq.add(list);
    		}
    	}
    	
    	List<List<Integer>> ans = new ArrayList<>();
    	while (!pq.isEmpty())
    	{
    		ans.add(pq.poll());
    	}
    	
    	return ans;
    }
}