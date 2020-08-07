import java.util.Arrays;
import java.util.Comparator;

public class Solution
{
    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, new IntervalComparator());
        return dfs(-1, 0, intervals);
    }

    private class IntervalComparator implements Comparator<int[]> {

        @Override
        public int compare(int[] o1, int[] o2) {
            return o1[0] - o2[0];
        }
    }

    private int dfs(int prev, int cur, int[][] intervals) {
        if (cur == intervals.length) {
            return 0;
        }

        int taken = Integer.MAX_VALUE;
        int noTaken;

        if (prev == -1 || intervals[prev][1] <= intervals[cur][0]) {
            taken = dfs(cur, cur + 1, intervals);
        }

        noTaken = dfs(prev, cur + 1, intervals) + 1;

        return Math.min(taken, noTaken);
    }
}
