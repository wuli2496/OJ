import java.util.Arrays;
import java.util.Comparator;

class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
    	if (intervals.length == 0) {
    		return 0;
    	}
    	
    	Arrays.sort(intervals, new IntervalComparator());
    	int[] dp = new int[intervals.length];
    	dp[0] = 1;
    	int ans = 1;
    	for (int i = 1; i < intervals.length; ++i) {
    		int max = 0;
    		for (int j = i - 1; j >= 0; --j) {
    			if (!isOverlapping(intervals[j], intervals[i])) {
    				max = Math.max(max, dp[j]);
    				break;
    			}
    		}
    		
    		dp[i] = Math.max(max + 1, dp[i - 1]);
    		ans = Math.max(dp[i], ans);
    	}
    	
    	return intervals.length - ans;
    }
    
    private boolean isOverlapping(int[] interval1, int[] interval2) {
    	return interval1[1] > interval2[0];
    }
    
    private class IntervalComparator implements Comparator<int[]> {

		@Override
		public int compare(int[] arg0, int[] arg1) {
			return arg0[1] - arg1[1];
		}
    	
    }
}
