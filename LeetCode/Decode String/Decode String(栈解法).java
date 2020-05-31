import java.util.Stack;

public class Solution
{
    public String decodeString(String s) {
        StringBuilder sb = new StringBuilder();
        Stack<StringBuilder> charStack = new Stack<>();
        Stack<Integer> numStack = new Stack<>();
        int nums = 0;

        for (char ch : s.toCharArray()) {
            if (Character.isDigit(ch)) {
                nums = nums * 10 + (ch - '0');
            } else if (Character.isAlphabetic(ch)) {
                sb.append(ch);
            } else if (ch == '[') {
                charStack.push(sb);
                numStack.push(nums);
                sb = new StringBuilder();
                nums = 0;
            } else {
                int num = numStack.pop();
                StringBuilder tmp = charStack.pop();
                for (int i = 0; i < num; ++i) {
                    tmp.append(sb);
                }
                sb = tmp;
            }
        }

        return sb.toString();
    }
}