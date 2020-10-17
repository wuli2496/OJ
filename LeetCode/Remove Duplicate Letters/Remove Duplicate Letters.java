class Solution {
    public String removeDuplicateLetters(String s) {
        char[] alphas = new char[26];
        for (int i = 0; i < s.length(); ++i) {
            ++alphas[s.charAt(i) - 'a'];
        }

        int pos = 0;
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) < s.charAt(pos)) {
                pos = i;
            }

            if (--alphas[s.charAt(i) - 'a'] == 0) {
                break;
            }
        }

        if (s.length() == 0) {
            return "";
        }

        return s.charAt(pos) + removeDuplicateLetters(s.substring(pos + 1).replaceAll("" + s.charAt(pos), ""));
    }
}
