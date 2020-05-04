import java.util.Arrays;

class Solution 
{
	public void wiggleSort(int[] nums) 
	{
		Arrays.sort(nums);
		
		int n = nums.length;
		int[] smaller = new int[n % 2 == 0 ? n / 2 : n / 2 + 1];
		int[] bigger = new int[n / 2];
		
		System.arraycopy(nums, 0, smaller, 0, smaller.length);
		System.arraycopy(nums, smaller.length, bigger, 0, bigger.length);
		
		int i = 0;
		for (; i < n / 2; ++i)
		{
			nums[2 * i] = smaller[smaller.length - 1 - i];
			nums[2 * i + 1] = bigger[bigger.length - 1 - i];
		}
		
		if (n % 2 != 0)
		{
			nums[2 * i] = smaller[smaller.length - 1 - i];
		}
    }
}