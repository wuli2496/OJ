/**
Given an array of integers, find out whether there are two distinct indices i and j in the array such that the absolute difference between nums[i] and nums[j] is at most t and the absolute difference between i and j is at most k.

Example 1:

Input: nums = [1,2,3,1], k = 3, t = 0
Output: true
Example 2:

Input: nums = [1,0,1,1], k = 1, t = 2
Output: true
Example 3:

Input: nums = [1,5,9,1,5,9], k = 2, t = 3
Output: false
 */

import java.util.Set;
import java.util.TreeSet;

class Solution 
{
	 class Solution {
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) 
	 {
		 if (t < 0)
		 {
			 return false;
		 }
		 
		 Map<Long, Long> map = new HashMap<>();
		 long w = (long)t + 1;
		 for (int i = 0; i < nums.length; ++i)
		 {
			 long id = getId(nums[i], w);
			 
			 if (map.containsKey(id))
			 {
				 return true;
			 }
			 
			 if (map.containsKey(id - 1) && Math.abs(nums[i] - map.get(id - 1)) <= t)
			 {
				 return true;
			 }
			 
			 if (map.containsKey(id + 1) && Math.abs(nums[i] - map.get(id + 1)) <= t)
			 {
				 return true;
			 }
			 
			 map.put(id, (long)nums[i]);
			 
			 if (i >= k)
			 {
				 map.remove(getId(nums[i - k], w));
			 }
		 }
		 
		 return false;
	 }
	 
	 private long getId(long x, long w)
	 {
		 if (x < 0)
		 {
			 return (x + 1) / w - 1;
		 }
		 else 
		 {
			 return x / w;
		 }
	 }
}
}