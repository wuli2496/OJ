/*
 * dp(x) = dp(x &(x -1)) + 1
 * */

class Solution 
{
	public int[] countBits(int num) 
	{
		int[] cnt = new int[num + 1];
		
		for (int i = 1; i < cnt.length; ++i)
		{
			cnt[i] = cnt[i & (i - 1)] + 1; 
		}
		return cnt;
    }
}
