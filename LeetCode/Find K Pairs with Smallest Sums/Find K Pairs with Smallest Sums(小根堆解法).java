import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class Solution 
{
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) 
    {
    	if (nums2.length == 0 || nums1.length == 0)
    	{
    		return new ArrayList<>();
    	}
    	PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2)-> {return nums1[o1[0]] + nums2[o1[1]] - nums1[o2[0]] - nums2[o2[1]];});
    	
    	for (int i = 0; i < nums1.length; ++i)
    	{
    		pq.add(new int[] {i, 0});
    	}
    	
    	List<List<Integer>> ansList = new ArrayList<>();
    	while (k > 0 && !pq.isEmpty())
    	{
    		int[] cur = pq.poll();
    		List<Integer> pairIntegers = new ArrayList<>();
    		pairIntegers.add(nums1[cur[0]]);
    		pairIntegers.add(nums2[cur[1]]);
    		ansList.add(pairIntegers);
    		
    		if (cur[1] < nums2.length - 1)
    		{
    			pq.add(new int[] {cur[0], cur[1] + 1});
    		}
    		--k;
    	}
    	
    	return ansList;
    }
}
