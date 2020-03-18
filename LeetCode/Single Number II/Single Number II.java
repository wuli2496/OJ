/*
 * Single Number II
 * Given a non-empty array of integers, every element appears three times except for one, which appears exactly once. Find that single one.

Note:

Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?

Example 1:

Input: [2,2,3,2]
Output: 3
Example 2:

Input: [0,1,0,1,0,1,99]
Output: 99
 */

class Solution 
{
    public int singleNumber(int[] nums) 
    {
    	final int n = 32;
    	int[] count = new int[n];
    	
    	for (int i = 0; i < nums.length; ++i)
    	{
    		for (int j = 0; j < n; ++j)
    		{
    			count[j] += (nums[i] >> j) & 0x1;
    		}
    	}
    	
    	int result = 0;
    	for (int i = 0; i < n; ++i)
    	{
    		result |= (count[i] % 3) << i;
    	}
    	
        return result;
    }
}
