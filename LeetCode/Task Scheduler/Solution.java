import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Solution {
    static final int N = 26;

    public int leastInterval(char[] tasks, int n) {
        int[] map = new int[N];
        for (char c : tasks) {
            map[c - 'A']++;
        }

        PriorityQueue<Integer> queue = new PriorityQueue<>(26, Collections.reverseOrder());
        for (int f : map) {
            if (f > 0) {
                queue.add(f);
            }
        }

        int times = 0;
        while (!queue.isEmpty()) {

            int i = 0;
            List<Integer> tmp = new ArrayList<>();
            while (i <= n) {
                if (!queue.isEmpty()) {
                    if (queue.peek() > 1) {
                        tmp.add(queue.poll() - 1);
                    } else {
                        queue.poll();
                    }
                }

                times++;
                if (queue.isEmpty() && tmp.size() == 0) {
                    break;
                }
                i++;
            }

            for (int v : tmp) {
                queue.add(v);
            }
        }

        return times;
    }
}