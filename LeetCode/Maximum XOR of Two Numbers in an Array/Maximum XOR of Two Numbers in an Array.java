import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class Solution {
    public int findMaximumXOR(int[] nums) {
        int max = Arrays.stream(nums).max().getAsInt();

        int maxLen = Integer.toBinaryString(max).length();

        int maxXor = 0;
        int curXor = 0;
        Set<Integer> prefies = new HashSet<>();
        for (int i = maxLen - 1; i >= 0; --i) {
            maxXor <<= 1;
            curXor = maxXor | 1;

            prefies.clear();
            for (int num : nums) {
                prefies.add(num >> i);
            }

            for (int p : prefies) {
                if (prefies.contains(curXor ^ p)) {
                    maxXor = curXor;
                    break;
                }
            }
        }

        return maxXor;
    }
}

