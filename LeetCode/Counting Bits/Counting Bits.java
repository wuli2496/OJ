
class Solution 
{
	public int[] countBits(int num) 
	{
		int[] cnt = new int[num + 1];
		for (int i = 0; i < cnt.length; ++i)
		{
			cnt[i] = popcount(i); 
		}
		
		return cnt;
    }
	
	private int popcount(int num)
	{
		int count = 0;
		while (num != 0)
		{
			num &= num - 1;
			++count;
		}
		
		return count;
	}
}
