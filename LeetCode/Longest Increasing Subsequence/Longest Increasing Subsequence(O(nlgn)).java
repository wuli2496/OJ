import java.util.Arrays;

class Solution 
{	
	public int lengthOfLIS(int[] nums) 
	{
		if (nums.length == 0)
		{
			return 0;
		}
		
		int[] d = new int[nums.length + 1];
		final int INF = (int) 1e+9;
		Arrays.fill(d,  INF);
		d[0] = -INF;
		
		for (int num : nums)
		{
			int index = Arrays.binarySearch(d, num);
			if (index < 0)
			{
				index = -index - 1;
			}
			d[index] = num; 
		}
		
		int ans = 0;
		for (int i = 1; i < d.length; ++i)
		{
			if (d[i] != INF)
			{
				ans = i;
			}
		}
		
		return ans;
    }
}

