import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution
{
    public int longestSubstring(String s, int k) {
        Map<Character, Integer> cntMap = new HashMap<>();
        for (char ch : s.toCharArray()) {
            if (!cntMap.containsKey(ch)) {
                cntMap.put(ch, 1);
            } else {
                cntMap.put(ch, cntMap.get(ch) + 1);
            }
        }

        List<Integer> splits = new ArrayList<>();
        for (int i = 0; i < s.length(); ++i) {
            if (cntMap.get(s.charAt(i)) < k) {
                splits.add(i);
            }
        }

        if (splits.size() == 0) {
            return s.length();
        }

        splits.add(s.length());
        int ans = 0, left = 0;
        for (int i = 0; i < splits.size(); ++i) {
            int len = splits.get(i) - left;
            if (len > ans) {
                ans = Math.max(ans, longestSubstring(s.substring(left, left + len), k));
            }

            left = splits.get(i) + 1;
        }

        return ans;
    }
}