import java.util.Arrays;
import java.util.Comparator;

class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
    	if (intervals.length == 0) {
    		return 0;
    	}
    	
    	Arrays.sort(intervals, new IntervalComparator());
    	int count = 0;
    	int prev = 0;
    	
    	for (int i = 1; i < intervals.length; ++i) {
    		if (intervals[prev][1] <= intervals[i][0]) {
    			prev = i;
    		} else {
    			if (intervals[prev][1] > intervals[i][1]) {
    				prev = i;
    			}
    			++count;
    		}
    	}
    	
    	return count;
    }
    
    private class IntervalComparator implements Comparator<int[]> {
		@Override
		public int compare(int[] arg0, int[] arg1) {
			return arg0[0] - arg1[0];
		}
    }
}
