import java.util.Stack;

class Solution {
    class Node {
        int depth;
        int totalLen;
        boolean isFile;
    }

    public int lengthLongestPath(String input) {
        Stack<Node> stack = new Stack<>();

        int prev = -1;
        Node node;
        int depth = 0;
        boolean foundFile = false;
        for (int i = 0; i < input.length(); ++i) {
            if (input.charAt(i) == '\n' || i + 1 == input.length()) {
                node = new Node();
                node.depth = depth;
                node.isFile = foundFile;
                if (i + 1 == input.length()) {
                	if (i > 0 && input.charAt(i - 1) == '.' && Character.isAlphabetic(input.charAt(i))) {
                		node.isFile = true;
                	}
                }
                
                int len = 0;
                if (prev == -1) {
                	if (i + 1 == input.length()) {
                		len = i + 1;
                	} else {
                		len = i;
                	}
                } else {
                	if (i + 1 == input.length()) {
                		len = i - prev + 1;
                	} else {
                		len = i - prev;
                	}
                }
                
                node.totalLen = len;
                if (prev == -1) {
                    stack.push(node);
                } else {
                    if (!stack.isEmpty()) {
                        Node curNode = stack.peek();
                        int curDepth = curNode.depth;
                        if (depth < curDepth) {
                            while (!stack.isEmpty() && depth < stack.peek().depth) {
                                curNode = stack.pop();
                                if (!curNode.isFile) {
                                	continue;
                                }
                                int maxLen = curNode.totalLen;
                                if (curNode.depth == stack.peek().depth) {
                                    Node tmpNode = stack.pop();
                                    if (tmpNode.isFile) {
	                                    maxLen = Math.max(maxLen, tmpNode.totalLen);
	                                    curNode.totalLen = maxLen;
	                                    curNode.isFile = true;
                                    }
                                    stack.push(curNode);
                                } else if (curNode.depth > stack.peek().depth) {
                                    Node tmpNode = stack.pop();
                                    tmpNode.totalLen += curNode.totalLen + 1;
                                    tmpNode.isFile = true;
                                    stack.push(tmpNode);
                                }
                            }

                           stack.push(node);
                        } else {
                            stack.push(node);
                        }
                    }
                }

                ++i;
                depth = 0;
                while ((i < input.length()) && (input.charAt(i) == '\t')) {
                    ++i;
                    ++depth;
                }

                if (i < input.length()) {
                    prev = i;
                }
                
                foundFile = false;
            } else {
            	if (i > 0 && input.charAt(i - 1) == '.' && Character.isAlphabetic(input.charAt(i))) {
            		foundFile = true;
            	}
            }
        }
        
        int maxLen = 0;
        if (!stack.isEmpty()) {
            while (!stack.isEmpty()) {
                Node curNode = stack.pop();
                if (!curNode.isFile) {
                	continue;
                }
                
                maxLen = curNode.totalLen;
                if (stack.isEmpty()) {
                	break;
                }
                
                Node preNode = stack.pop();
                if (preNode.depth == curNode.depth) {
                	curNode.totalLen = Math.max(maxLen, preNode.totalLen);
                	stack.push(curNode);
                } else if (preNode.depth < curNode.depth){
                	preNode.totalLen += curNode.totalLen + 1;
                	preNode.isFile = true;
                	stack.push(preNode);
                }
            }
        }

        return maxLen;
    }
}