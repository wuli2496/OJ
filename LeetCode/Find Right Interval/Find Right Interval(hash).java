import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution {
	public int[] findRightInterval(int[][] intervals) {
		Map<int[], Integer> intervalMap = new HashMap<>();
		for (int i = 0; i < intervals.length; ++i) {
			intervalMap.put(intervals[i], i);
		}
		int[] res = new int[intervals.length];
		
		Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
		for (int i = 0; i < intervals.length; ++i) {
			int minIndex = -1;
			int min = Integer.MAX_VALUE;
			
			for (int j = i + 1; j < intervals.length; ++j) {
				if (intervals[j][0] >= intervals[i][1] && intervals[j][0] < min) {
					min = intervals[j][0];
					minIndex = intervalMap.get(intervals[j]);
				}
			}
			
			res[intervalMap.get(intervals[i])] = minIndex; 
		}
		
		return res;
    }
}
