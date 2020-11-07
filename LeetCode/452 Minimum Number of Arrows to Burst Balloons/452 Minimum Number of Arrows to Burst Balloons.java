import java.util.Arrays;
import java.util.Comparator;

public class Solution {
	public int findMinArrowShots(int[][] points) {
		if (points.length == 0) {
			return 0;
		}
		
		Arrays.sort(points, new Comparator<int[]>(){
			@Override
			public int compare(int[] o1, int[] o2) {
				return Integer.compare(o1[1], o2[1]);
			}
		});
		
		int ans = 1;
		int firstEnd = points[0][1];
		for (int[] point : points) {
			int left = point[0];
			int right = point[1];
			if (left > firstEnd) {
				firstEnd = right;
				++ans;
			}
		}
		
		return ans;
    }
}
