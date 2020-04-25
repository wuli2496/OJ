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
	 public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) 
	 {
		 TreeSet<Integer> set = new TreeSet<>();
		 for (int i = 0; i < nums.length; ++i)
		 {
			 Integer next = set.ceiling(nums[i]);
			 if (next != null && next <= nums[i] + t)
			 {
				 return true;
			 }
			 
			 Integer prev = set.floor(nums[i]);
			 if (prev != null && prev + t >= nums[i])
			 {
				 return true;
			 }
			 
			 set.add(nums[i]);
			 if (set.size() > k)
			 {
				 set.remove(nums[i - k]);
			 }
		 }
		 
		 return false;
	 }
}