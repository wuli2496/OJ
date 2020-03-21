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

public class Solution 
{
    public int singleNumber(int[] nums) 
    {    	
    	int result = 0;
    	final int n = 32;
    	for (int i = 0; i < n; ++i)
    	{
    		int sum = 0;
    		for (int j = 0; j < nums.length; ++j)
    		{
    			sum += (nums[j] >> i) & 0x1;
    		}
    		
    		result |= (sum % 3) << i;
    	}
    	
        return result;
    }
}

