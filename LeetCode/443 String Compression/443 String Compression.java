
class Solution {
    public int compress(char[] chars) {
        int ancher = 0;
        int read = 0, write = 0;
        for (; read < chars.length; ++read) {
            if (read + 1 == chars.length || chars[read + 1] != chars[read]) {
                chars[write++] = chars[ancher];
                if (read > ancher) {
                    for (char ch : ("" + (read - ancher + 1)).toCharArray()) {
                        chars[write++] = ch;
                    }
                }

                ancher = read + 1;
            }
        }

        return write;
    }
}
