import java.util.Arrays;

/**
You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed. All houses at this place are arranged in a circle. That means the first house is the neighbor of the last one. Meanwhile, adjacent houses have security system connected and it will automatically contact the police if two adjacent houses were broken into on the same night.

Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount of money you can rob tonight without alerting the police.

Example 1:

Input: [2,3,2]
Output: 3
Explanation: You cannot rob house 1 (money = 2) and then rob house 3 (money = 2),
             because they are adjacent houses.
Example 2:

Input: [1,2,3,1]
Output: 4
Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
             Total amount you can rob = 1 + 3 = 4.
 */

class Solution 
{
    public int rob(int[] nums) 
    {
    	if (nums.length == 0)
    	{
    		return 0;
    	}
    	
    	if (nums.length == 1)
    	{
    		return nums[0];
    	}
    	
    	return Math.max(myRob(Arrays.copyOfRange(nums, 0, nums.length - 1)), myRob(Arrays.copyOfRange(nums, 1, nums.length)));
    }
    
    private int myRob(int [] nums)
    {
    	int pre = 0;
    	int cur = 0;
    	
    	for (int num : nums)
    	{
    		int tmp = cur;
    		cur = Math.max(pre + num, cur);
    		pre = tmp;
    	}
    	
    	return cur;
    }
}
