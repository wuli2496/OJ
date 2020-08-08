
class Solution {
	public int[] findRightInterval(int[][] intervals) {
		int[] res = new int[intervals.length];
		
		for (int i = 0; i < intervals.length; ++i) {
			int minIndex = -1;
			int min = Integer.MAX_VALUE;
			
			for (int j = 0; j < intervals.length; ++j) {
				if (intervals[j][0] >= intervals[i][1] && intervals[j][0] < min) {
					min = intervals[j][0];
					minIndex = j;
				}
			}
			
			res[i] = minIndex; 
		}
		
		return res;
    }
}
