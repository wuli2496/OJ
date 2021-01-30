import java.util.Arrays;
import java.util.Stack;

/**
 * 503. Next Greater Element II
 */
public class Solution {
    public int[] nextGreaterElements(int[] nums) {
        int[] res = new int[nums.length];
        Arrays.fill(res, -1);
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < 2 * nums.length; ++i) {
            while (!stack.empty() && nums[stack.peek()] < nums[i % nums.length]) {
                res[stack.pop()] = nums[i % nums.length];
            }

            stack.push(i % nums.length);
        }
        return res;
    }
}
