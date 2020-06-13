class Solution {
    public int longestSubstring(String s, int k) {
        int len = s.length();
        if (len == 0 || k > len) {
            return 0;
        }

        if (k < 2) {
            return len;
        }

        return dfs(s, k, 0, len - 1);
    }

    private int dfs(String s, int k, int start, int end)
    {
        if (end - start + 1 < k) {
            return 0;
        }

        int[] cnt = new int[26];
        for (int i = start; i <= end; ++i) {
            ++cnt[s.charAt(i) - 'a'];
        }

        while (end - start + 1 >= k && cnt[s.charAt(start) - 'a'] < k) {
            ++start;
        }

        while (end - start + 1 >= k && cnt[s.charAt(end) - 'a'] < k) {
            --end;
        }

        if (end - start + 1 < k) {
            return 0;
        }

        for (int i = start; i <= end; ++i) {
            if (cnt[s.charAt(i) - 'a'] < k) {
                return Math.max(dfs(s, k, start, i - 1), dfs(s, k , i + 1, end));
            }
        }

        return end - start + 1;
    }
}

