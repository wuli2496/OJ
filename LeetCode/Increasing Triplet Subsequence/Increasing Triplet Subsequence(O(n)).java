
class Solution 
{
	public boolean increasingTriplet(int[] nums) 
	{
		if (nums == null || nums.length == 0)
		{
			return false;
		}
		
		int small = Integer.MAX_VALUE;
		int mid = Integer.MAX_VALUE;
		
		for (int num : nums)
		{
			if (num <= small)
			{
				small = num;
			}
			else if (num <= mid)
			{
				mid = num;
			}
			else 
			{
				return true;
			}
		}
		
		return false;
    }
}
