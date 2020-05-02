class Solution {
    public int[] singleNumber(int[] nums) {
        int mask = 0;
		for (int num : nums)
		{
			mask ^= num;
		}
		
		int diff = mask & (-mask);
		
		int x = 0;
		for (int num : nums)
		{
			if ((diff & num) != 0)
			{
				x ^= num;
			}
		}
		
		return new int[] {x, mask ^ x};
    }
}