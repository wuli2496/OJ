import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class TrieNode
{
    Map<Character, TrieNode> childs = new HashMap<>();
    TrieNode()
    {

    }
}

class Solution {
    public int findMaximumXOR(int[] nums) {
        int max = Arrays.stream(nums).max().getAsInt();

        int maxLen = Integer.toBinaryString(max).length();
        int bitMask = 1 << maxLen;
        String[] strNums = new String[nums.length];
        for (int i = 0; i < nums.length; ++i) {
            strNums[i] = Integer.toBinaryString(nums[i] | bitMask).substring(1);
        }

        TrieNode root = new TrieNode();
        int maxXor = 0;
        for (String strNum : strNums) {
            TrieNode insertNode = root;
            TrieNode xorNode = root;
            int curXor = 0;
            for (Character ch : strNum.toCharArray()) {
                if (!insertNode.childs.containsKey(ch)) {
                    insertNode.childs.put(ch, new TrieNode());
                }

                insertNode = insertNode.childs.get(ch);

                Character rev = (ch == '0') ? '1' : '0';
                if (xorNode.childs.containsKey(rev)) {
                    curXor = (curXor << 1) | 1;
                    xorNode = xorNode.childs.get(rev);
                } else {
                    curXor = curXor << 1;
                    xorNode = xorNode.childs.get(ch);
                }
            }
            maxXor = Math.max(maxXor, curXor);
        }

        return maxXor;
    }
}

