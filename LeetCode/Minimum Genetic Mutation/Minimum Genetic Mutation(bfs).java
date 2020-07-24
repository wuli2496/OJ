import java.util.*;

class Solution {
    public int minMutation(String start, String end, String[] bank) {
        if (bank != null) {
            boolean exist = Arrays.stream(bank).anyMatch(s -> s.equals(end));
            if (!exist) {
                return -1;
            }
        }

        return bfs(start, end, bank);
    }

    private int bfs(String start, String end, String[] bank) {
        Queue<String> queue = new LinkedList<>();
        queue.add(start);
        Set<String> set = new HashSet<>();
        set.add(start);
        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                String cur = queue.poll();
                if (cur == end) {
                    return step;
                }

                for (String s : bank) {
                    if (isReachable(cur, s) && !set.contains(s)) {
                        queue.add(s);
                        set.add(s);
                    }
                }
            }
            ++step;
        }

        return -1;
    }

    private boolean isReachable(String a, String b) {
        if (a.length() != b.length()) {
            return false;
        }

        int cnt = 0;
        for (int i = 0; i < a.length(); ++i) {
            if (a.charAt(i) != b.charAt(i)) {
                ++cnt;
                if (cnt > 1) {
                    break;
                }
            }
        }

        return cnt == 1;
    }
}
