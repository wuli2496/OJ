import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
	private static final boolean DEBUG = false;
    private static final long P = 131;
    private static final long MOD = 1000000009;
    private String text, pattern;
    private long xp;
   
	public Main(String text, String pattern) {
        this.text = text;
        this.pattern = pattern;
        int n = pattern.length();
        xp = 1;
        
        for (int i = 1; i <= n; i++) {
        	xp = xp * P % MOD;
        	if (xp < 0) {
        		xp = (xp + MOD) % MOD;
        	}
        }
    }

    public List<Integer> solve() {
    	int patternLen = pattern.length(), textLen = text.length();
    	if (patternLen > textLen) {
    		return Collections.emptyList();
    	}
    	
    	long patternHash = getHash(pattern, patternLen);
    	long textHash = getHash(text, patternLen);
    	
    	List<Integer> ans = new ArrayList<>();
    	for (int i = 0; i + patternLen <= textLen; i++) {
    		if (textHash == patternHash) {
    			ans.add(i + 1);
    		}
    		
    		if (i + patternLen < textLen) {
    			textHash = (textHash * P - (text.charAt(i) - 'a') * xp + (text.charAt(i + patternLen)  - 'a')) % MOD;
    			if (textHash < 0) {
    				textHash = (textHash + MOD) % MOD;
    			}
    		}
    	}
    	return ans;
    }
    
    private long getHash(String s, int len) {
    	long result = 0;
    	for (int i = 0; i < len; i++) {
    		result = (result * P + s.charAt(i) - 'a') % MOD;
    		if (result < 0) {
    			result = (result + MOD) % MOD;
    		}
    	}
    	
    	return result;
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
        	String text = scanner.next();
        	String pattern = scanner.next();
        	Main solution = new Main(text, pattern);
	        List<Integer> ans = solution.solve();
	        if (ans.isEmpty()) {
	        	printWriter.println("Not Found");
	        } else {
	        	printWriter.println(ans.size());
	        	for (int i = 0; i < ans.size(); i++) {
	        		printWriter.print(ans.get(i));
	        		if (i != ans.size() - 1) {
	        			printWriter.print(" ");
	        		}
	        	}
	        	printWriter.println();
	        }
	        
	        if (t != 0) {
	        	printWriter.println();
	        }
	        printWriter.flush();
        }
    }
}
