import java.util.HashMap;
import java.util.Map;

class Solution 
{
	public int combinationSum4(int[] nums, int target) 
	{
		memo = new HashMap<>();
		return backtrack(nums, target);
    }
	
	private int backtrack(int[] nums, int target)
	{
		if (target < 0)
		{
			return 0;
		}
		else if (target == 0)
		{
			return 1;
		}
		
		if (memo.containsKey(target))
		{
			return memo.get(target);
		}
		int ans = 0;
		for (int i = 0; i < nums.length; ++i)
		{
			ans += backtrack(nums, target - nums[i]);
		}
		
		memo.put(target, ans);
		return ans;
	}
	
	private Map<Integer, Integer> memo;
}
