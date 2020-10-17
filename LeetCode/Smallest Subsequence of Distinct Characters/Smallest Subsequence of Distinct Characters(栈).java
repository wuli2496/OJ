class Solution {
    public String smallestSubsequence(String s) {
        Map<Character, Integer> lastOccurence = new HashMap<>();
        for (int i = 0; i < s.length(); ++i) {
            lastOccurence.put(s.charAt(i), i);
        }

        Set<Character> seen = new HashSet<>();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); ++i) {
            if (!seen.contains(s.charAt(i))) {
                while (!stack.isEmpty() && s.charAt(i) < stack.peek() && i < lastOccurence.get(stack.peek())) {
                    seen.remove(stack.pop());
                }

                stack.add(s.charAt(i));
                seen.add(s.charAt(i));
            }
        }

        StringBuilder sb = new StringBuilder(stack.size());
        for (Character ch : stack) {
            sb.append(ch);
        }

        return sb.toString();
    }
}
