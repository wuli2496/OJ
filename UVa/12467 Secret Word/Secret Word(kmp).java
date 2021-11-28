import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * spoj/uva
 * @author wl
 *
 */
public class Main {
	private static final boolean DEBUG = false;
    private String s;
    
    public Main(String s) {
    	this.s = s;
    }
    
    public String solve() {
    	StringBuilder sb = new StringBuilder();
    	for (int i = s.length() - 1; i >= 0; i--) {
    		sb.append(s.charAt(i));
    	}
    	
    	String t = sb.toString();
    	
    	int[] pi = prefixFunction(s); 
    	int pos = kmpSearch(s, t, pi);
    	StringBuilder ans = new StringBuilder();
    	for (int i = pos - 1; i >= 0; i--) {
    		ans.append(s.charAt(i));
    	}
    	
    	return ans.toString();
    }
    
    
    private int kmpSearch(String pattern, String text, int[] pi) {
    	int textLen = text.length();
    	int patternLen = pattern.length();
    	
    	int ans = 0;
    	int j = 0;
    	for (int i = 0; i < textLen; i++) {
    		while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
    			j = pi[j - 1];
    		}
    		
    		if (pattern.charAt(j) == text.charAt(i)) {
    			j++;
    		}
    		
    		ans = Math.max(ans, j);
    		if (j == patternLen) {
    			j = pi[patternLen - 1];
    		}
    	}
    	
    	return ans;
    }
    
    private int[] prefixFunction(String text) {
    	int len = text.length();
    	int[] pi = new int[len];
    	for (int i = 1; i < len; i++) {
    		int j = pi[i - 1]; 
    		while (j > 0 && text.charAt(i) != text.charAt(j)) {
    			j = pi[j - 1];
    		}
    		
    		if (text.charAt(i) == text.charAt(j)) {
    			j++;
    		}
    		pi[i] = j;
    	}
    	
    	return pi;
    }
    
    @SuppressWarnings("resource")
    public static void main(String[] args) throws IOException {

        Scanner scanner;

        PrintWriter printWriter = new PrintWriter(new BufferedOutputStream(System.out));

        if (DEBUG) {
            scanner = new Scanner(new BufferedInputStream(new FileInputStream("f:\\OJ\\uva_in.txt")));
        } else {
            scanner = new Scanner(new BufferedInputStream(System.in));
        }

        int t = scanner.nextInt();
        while (t-- > 0) {
        	String s = scanner.next();
        	Main solution = new Main(s);
	        String ans = solution.solve();
	        printWriter.println(ans);
	        printWriter.flush();
        }
    }
}
