
class Solution {
	public int repeatedStringMatch(String a, String b) {
		int n = a.length();
		int m = b.length();
		int[] pi = prefixFunction(b);
		
		int i = 0, j = 0;
		while (i - j < n) {
			while (j > 0 && a.charAt(i % n) != b.charAt(j)) {
				j = pi[j - 1];
			}
			if (a.charAt(i % n) == b.charAt(j)) {
				++j;
			}
			++i;
		
			if (j == m) {
				break;
			}
		}
		
		if (j == m) {
			return i % n == 0 ? i / n : i / n + 1;
		}
		
		return -1;
    }
	
	private int[] prefixFunction(String a) {
		int[] pi = new int[a.length()];
		
		int len = a.length();
		for (int i = 1; i < len; ++i) {
			int j = pi[i - 1];
			while (j > 0 && a.charAt(j) != a.charAt(i)) {
				j = pi[j - 1];
			}
			
			if (a.charAt(j) == a.charAt(i)) j++;
			pi[i] = j; 
		}
		
		return pi;
	}
}