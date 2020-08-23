import java.util.ArrayDeque;
import java.util.Deque;

public class Codec {
	public String serialize(TreeNode root) {
		StringBuilder stringBuilder = postOrder(root, new StringBuilder());
		
		return stringBuilder.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
    	if (data == null || data.isEmpty()) {
    		return null;
    	}
    	
    	Deque<Integer> deque = new ArrayDeque<>();
    	int len = data.length();
    	for (int i = 0; i < len / 4; ++i) {
    		deque.add(strToInt(data.substring(4 * i, 4 * (i + 1))));
    	}
    	
		return helper(Integer.MIN_VALUE, Integer.MAX_VALUE, deque); 
    }
    
    private StringBuilder postOrder(TreeNode root, StringBuilder stringBuilder) {
    	if (root == null) {
    		return stringBuilder;
    	}
    	
    	postOrder(root.left, stringBuilder);
    	postOrder(root.right, stringBuilder);
    	stringBuilder.append(intToStr(root.val));
    	
    	return stringBuilder;
    }
    
    private String intToStr(int val) {
    	char[] bytes = new char[4];
    	for (int i = 3; i >= 0; --i) {
    		bytes[3 - i] = (char)(val >> (i * 8) & 0xff);
    	}
    	
    	return new String(bytes);
    }
    
    private Integer strToInt(String s) {
    	int result = 0;
    	for (char ch : s.toCharArray()) {
    		result = result * 256 + (int)ch;
    	}
    	
    	return result;
    }
    
    private TreeNode helper(Integer min, Integer max, Deque<Integer> deque) {
    	if (deque.isEmpty()) {
    		return null;
    	}
    	
    	Integer val = deque.getLast();
    	if (val < min || val > max) {
    		return null;
    	}
    		
    	deque.removeLast();
    	TreeNode root = new TreeNode(val);
    	root.right = helper(val, max, deque);
    	root.left = helper(min, val, deque);
    	
		return root;
    }
}

