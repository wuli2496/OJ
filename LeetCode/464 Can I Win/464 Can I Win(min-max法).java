import java.util.HashMap;
import java.util.Map;

class Solution {
    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        int sum = (1 + maxChoosableInteger) * maxChoosableInteger / 2;
        if (sum < desiredTotal) {
            return false;
        }

        Map<Integer, Boolean> dp = new HashMap<>();
        return backtrack(maxChoosableInteger, 0, 0, desiredTotal, dp);
    }

    private boolean backtrack(int maxChoosableInteger, int sum, int s, int target, Map<Integer, Boolean> dp) {
        if (dp.containsKey(s)) {
            return dp.get(s);
        }

        if (sum >= target) {
            dp.put(s, true);
            return true;
        }

        if (s == (1 << maxChoosableInteger) - 1) {
            dp.put(s, false);
            return false;
        }

        for (int i = 1; i <= maxChoosableInteger; ++i) {
            if ((s & (1 << (i - 1))) != 0) {
                continue;
            }
            int newState = s | (1 << (i - 1));
            if (sum + i >= target) {
                dp.put(newState, true);
                return true;
            }

            boolean min = backtrack(maxChoosableInteger, sum + i, newState, target, dp);
            if (!min) {
                dp.put(s, true);
                return true;
            }
        }

        dp.put(s, false);
        return false;
    }
}
