import java.util.Map;
import java.util.TreeMap;

class Solution {
	public int[] findRightInterval(int[][] intervals) {
		TreeMap<Integer, Integer> intervalMap = new TreeMap<>(); 
		for (int i = 0; i < intervals.length; ++i) {
			intervalMap.put(intervals[i][0], i);
		}
		
		int[] res = new int[intervals.length];
		for (int i = 0; i < intervals.length; ++i) {
			Map.Entry<Integer, Integer> entry = intervalMap.ceilingEntry(intervals[i][1]);
			if (entry == null) {
				res[i] = -1; 
			} else {
				res[i]= entry.getValue(); 
			}
		}
		
		return res;
    }
}
