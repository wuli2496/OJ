import java.util.Arrays;

class Solution {
	public int[] findRightInterval(int[][] intervals) {
		int[][] pair = new int[intervals.length][2];
		for (int i = 0; i < intervals.length; ++i) {
			pair[i][0] = intervals[i][0];
			pair[i][1] = i;
		}
		
		int[] res = new int[intervals.length];
		
		Arrays.sort(pair, (a, b) -> a[0] - b[0]);
		for (int i = 0; i < intervals.length; ++i) {
			res[i] = binarySearch(pair, intervals[i][1]); 
		}
		
		return res;
    }
	
	private int binarySearch(int[][] pair, int num) {
		int left = 0;
		int right = pair.length;
		while (left < right) {
			int mid = (left + right) >> 1;
			if (pair[mid][0] >= num) {
				right = mid;
			} else {
				left = mid + 1;
			}
		}
		
		if (left == pair.length) {
			return -1;
		}
		
		return pair[left][1];
	}
}
