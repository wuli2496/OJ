import java.util.HashMap;
import java.util.Map;

public class Solution
{
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int a : A) {
            for (int b : B) {
                int sum = a + b;
                if (map.containsKey(sum)) {
                    map.put(sum, map.get(sum) + 1);
                } else {
                    map.put(sum, 1);
                }
            }
        }

        int count  = 0;
        for (int c : C) {
            for (int d : D) {
                int sum = c + d;
                if (map.containsKey(-sum)) {
                    count += map.get(-sum);
                }
            }
        }

        return count;
    }
}
