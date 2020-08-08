import java.util.Arrays;
import java.util.Comparator;

class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
    	if (intervals.length == 0) {
    		return 0;
    	}
    	
    	Arrays.sort(intervals, new IntervalComparator());
    	int count = 1;
    	int end = intervals[0][1];
    	
    	for (int i = 1; i < intervals.length; ++i) {
    		if (intervals[i][0] >= end) {
    			++count;
    			end = intervals[i][1];
    		}
    	}
    	
    	return intervals.length - count;
    }
    
    private class IntervalComparator implements Comparator<int[]> {
		@Override
		public int compare(int[] arg0, int[] arg1) {
			return arg0[1] - arg1[1];
		}
    }
}
