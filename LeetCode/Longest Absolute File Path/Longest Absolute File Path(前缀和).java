import java.util.ArrayList;
import java.util.List;

public class Solution {
    public int lengthLongestPath(String input) {
        List<Integer> list = new ArrayList<>();
        list.add(0);

        int level = 0;
        int ans = 0;
        int count = 0;
        boolean foundFile = false;
        for (int i = 0; i < input.length(); ++i) {
            if (input.charAt(i) == '\n') {
                if (level > 0) {
                    if (list.size() <= level) {
                        list.add(list.get(level - 1) + count);
                    } else {
                        list.set(level, list.get(level - 1) + count);
                    }
                } else {
                    if (list.size() <= level) {
                        list.add(count);
                    } else {
                        list.set(level, count);
                    }
                }

                if (foundFile) {
                    ans = Math.max(ans, list.get(level) + level);
                    foundFile = false;
                }
                level = 0;
                count = 0;
            } else if (input.charAt(i) == '\t') {
                ++level;
            } else {
                if (i > 0 && input.charAt(i - 1) == '.' && Character.isAlphabetic(input.charAt(i))) {
                    foundFile = true;
                }
                ++count;
            }
        }

        if (level > 0) {
            if (list.size() <= level) {
                list.add(list.get(level - 1) + count);
            } else {
                list.set(level, list.get(level - 1) + count);
            }
        } else {
            if (list.size() <= level) {
                list.add(count);
            } else {
                list.set(level, count);
            }
        }

        if (foundFile) {
            ans = Math.max(ans, list.get(level) + level);
        }
        return ans;
    }
}

