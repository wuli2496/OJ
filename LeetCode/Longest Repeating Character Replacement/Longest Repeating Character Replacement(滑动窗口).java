class Solution 
{
	public int characterReplacement(String s, int k) {
		final int N = 26;
		
		int[] cnt = new int[N];
		
		int left = 0;
		int right;
		int charMax = 0;
		for (right = 0; right < s.length(); ++right) {
			int index = s.charAt(right) - 'A';
			++cnt[index];
			charMax = Math.max(charMax, cnt[index]);
			if (right - left + 1 > charMax + k) {
				--cnt[s.charAt(left) - 'A'];
				++left;
			}
		}
		
		return s.length() - left;
    }
}
