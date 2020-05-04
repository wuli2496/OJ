
class Solution 
{
	public void wiggleSort(int[] nums) 
	{
		int n = nums.length;
		int left = 0;
		int right = n - 1;
		int mid = (left + right) >>> 1;
		
		while (left < right)
		{
			int pivot = partition(nums, left, right);
			if (pivot == mid)
			{
				break;
			}
			else if (pivot > mid)
			{
				right = pivot - 1;
			}
			else 
			{
				left = pivot + 1;
			}
		}
		
		int midNum = nums[mid];
		left = 0;
		right = n - 1;
		int cur = 0;
		while (cur < right)
		{
			if (nums[cur] < midNum)
			{
				swap(nums, left, cur);
				cur += 1;
				left += 1;
			}
			else if (nums[cur] > midNum)
			{
				swap(nums, cur, right);
				right -= 1;
			}
			else 
			{
				cur += 1;
			}
		}
		
		int[] tmp = new int[n];
		System.arraycopy(nums, 0, tmp, 0, n);
		int small = mid; 
		int big = n - 1;
		for (int i = 0; i < n; ++i)
		{
			if (i % 2 == 0)
			{
				nums[i] = tmp[small];
				--small;
			}
			else 
			{
				nums[i] = tmp[big];
				--big;
			}
		}
    }
	
	private int partition(int[] nums, int left, int right)
	{
		while (left < right)
		{
			while (left < right && nums[left] < nums[right])
			{
				--right;
			}
			
			if (left < right)
			{
				swap(nums, left, right);
				++left;
			}
			
			while (left < right && nums[left] < nums[right])
			{
				++left;
			}
			
			if (left < right)
			{
				swap(nums, left, right);
				--right;
			}
		}
		
		return left;
	}
	
	private void swap(int[] nums, int a, int b)
	{
		int tmp = nums[a];
		nums[a] = nums[b];
		nums[b] = tmp;
		
		return;
	}
}