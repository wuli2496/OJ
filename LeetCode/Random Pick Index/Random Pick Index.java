import java.util.Random;

class Solution {
    public Solution(int[] nums) {
        this.nums = nums;
        random = new Random();
    }

    public int pick(int target) {
        int cnt = 0;
        int which = 0;

        for (int i = 0; i < nums.length; ++i) {
            if (nums[i] == target) {
                if (cnt == 0) {
                    ++cnt;
                    which = i;
                } else {
                    ++cnt;
                    if (random.nextInt(cnt) == 0) {
                        which = i;
                    }
                }
            }
        }

        return which;
    }

    private int[] nums;
    private Random random;
}

