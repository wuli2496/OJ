import java.util.Arrays;

public class Solution
{
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);

        int best = 10000000;
        for (int i = 0; i < nums.length; ++i) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int j = i + 1;
            int k = nums.length - 1;
            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                if (sum == target) {
                    return target;
                }

                if (Math.abs(sum - target) < Math.abs(best - target)) {
                    best = sum;
                }

                if (sum > target) {
                    int k0 = k - 1;
                    while (j < k0 && nums[k] == nums[k0]) {
                        --k0;
                    }
                    k = k0;
                } else if (sum < target) {
                    int j0 = j + 1;
                    while (j0 < k && nums[j] == nums[j0]) {
                        ++j0;
                    }

                    j = j0;
                }
            }
        }

        return best;
    }
}
