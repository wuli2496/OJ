
class Solution {
    public int[] countBits(int num) {
        int[] cnt = new int[num + 1];
		
		for (int i = 0; i < cnt.length; ++i)
		{
			cnt[i] = cnt[i / 2] + i % 2; 
		}
		return cnt;
    }
}
