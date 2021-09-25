import java.util.HashSet;
import java.util.Set;

class Solution {
	public int repeatedStringMatch(String a, String b) {
		Set<Character> chSet = new HashSet<>();
		for (int i = 0; i < a.length(); ++i) {
			chSet.add(a.charAt(i));
		}
		
		for (int i = 0; i < b.length(); ++i) {
			if (!chSet.contains(b.charAt(i))) {
				return -1;
			}
		}
		
	
		int q = (b.length() + (a.length() - 1)) / a.length();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < q; ++i) {
			sb.append(a);
		}
		
		String aString = sb.toString();
		if (aString.indexOf(b) != -1) {
			return q;
		} else {
			sb.append(a);
			aString = sb.toString();
			
			if (aString.indexOf(b) != -1) {
				return q + 1;
			} else {
				return -1;
			}
		}
    }
}