import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution {
	public int[] findRightInterval(int[][] intervals) {
		int[] res = new int[intervals.length];
		Map<Integer, Integer> intervalMap = new HashMap<>(); 
		for (int i = 0; i < intervals.length; ++i) {
			intervalMap.put(intervals[i][0], i);
		}
		
		
		int[][] endIntervals = new int[intervals.length][2];
		System.arraycopy(intervals, 0, endIntervals, 0, intervals.length);
		
		Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
		Arrays.sort(endIntervals, (a, b) -> a[1] - b[1]);
		int j = 0;
		for (int i = 0; i < endIntervals.length; ++i) {
			int k = j;
			int minIndex = -1;
			for (; k < intervals.length; ++k) {
				if (intervals[k][0] >= endIntervals[i][1]) {
					minIndex = intervalMap.get(intervals[k][0]);
					j = k;
					break;
				}
			}
			
			res[intervalMap.get(endIntervals[i][0])] = minIndex; 
		}
		
		return res;
    }
}
