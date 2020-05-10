class Solution {
    public int[] countBits(int num) {
        int[] cnt = new int[num + 1];
		int i = 0;
		int b = 1;
		
		while (b <= num)
		{
			while (i < b && i + b <= num)
			{
				cnt[i + b] = cnt[i] + 1;  
				++i;
			}
			i = 0;
			b <<= 1;
		}
		
		return cnt;
    }
}
