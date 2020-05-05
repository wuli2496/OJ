
class Solution 
{
	public boolean increasingTriplet(int[] nums) 
	{
		if (nums == null || nums.length == 0)
		{
			return false;
		}
		
		int n = nums.length;
		int[] left = new int[n];
		int[] right = new int[n];
		
		left[0] = nums[0];
		right[n - 1] = nums[n - 1];
		for (int i = 1; i < n; ++i)
		{
			left[i] = Math.min(left[i - 1], nums[i]);
			right[n - 1 - i] = Math.max(right[n - i], nums[n - 1 - i]); 
		}
		
		for (int i = 0; i < n; ++i)
		{
			if (left[i] < nums[i] && nums[i] < right[i])
			{
				return true;
			}
		}
		
		return false;
    }
}
