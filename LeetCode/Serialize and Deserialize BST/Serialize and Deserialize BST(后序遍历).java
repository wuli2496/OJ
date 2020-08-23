import java.util.ArrayDeque;
import java.util.Deque;

public class Codec {
	public String serialize(TreeNode root) {
		StringBuilder stringBuilder = postOrder(root, new StringBuilder());
		if (stringBuilder.length() > 0) {
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		}
		
		return stringBuilder.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
    	if (data == null || data.isEmpty()) {
    		return null;
    	}
    	
    	Deque<Integer> deque = new ArrayDeque<>();
    	for (String string: data.split("\\s+")) {
    		deque.add(Integer.valueOf(string));
    	}
    	
		return helper(Integer.MIN_VALUE, Integer.MAX_VALUE, deque); 
    }
    
    private StringBuilder postOrder(TreeNode root, StringBuilder stringBuilder) {
    	if (root == null) {
    		return stringBuilder;
    	}
    	
    	postOrder(root.left, stringBuilder);
    	postOrder(root.right, stringBuilder);
    	stringBuilder.append(root.val).append(' ');
    	
    	return stringBuilder;
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

