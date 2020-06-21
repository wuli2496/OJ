class Solution {
    public String removeKdigits(String num, int k) {
        LinkedList<Character> stack = new LinkedList<>();

        for (char ch : num.toCharArray()) {
            while (k > 0 && !stack.isEmpty() && stack.peekLast() > ch) {
                --k;
                stack.removeLast();
            }

            stack.addLast(ch);
        }

        while (k > 0) {
            stack.removeLast();
            --k;
        }

        StringBuilder sb = new StringBuilder();
        boolean precedingZero = true;
        for (char ch : stack) {
            if (precedingZero && ch == '0') {
                continue;
            }

            precedingZero = false;
            sb.append(ch);
        }

        if (sb.length() == 0) {
            return "0";
        }

        return sb.toString();
    }
}
