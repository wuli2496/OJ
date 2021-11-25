import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * spoj/uva
 * @author wl
 *
 */
public class Main {
	private static final boolean DEBUG = false;
    private int[][] dp;
    private String s;
    
    public Main(String x) {
    	this.s = x;
    }
    
    public int solve() {
    	int len = s.length();
    	dp = new int[len][len];
    	for (int i = 0; i < len; i++) {
    		Arrays.fill(dp[i], Integer.MAX_VALUE);
    	}
    	
    	return dfs(0, len - 1);
    }
    
    private int dfs(int l, int r) {
    	if (dp[l][r] != Integer.MAX_VALUE) {
    		return dp[l][r];
    	}
    	
    	if (l == r) {
    		return dp[l][r] = 1;
    	}
    	
    	for (int i = l; i < r; i++) {
    		dp[l][r] = Math.min(dp[l][r], dfs(l, i) + dfs(i + 1, r));
    	}
    	
    	int period = calPeriod(s.substring(l, r + 1));
    	dp[l][r] = Math.min(dp[l][r], dfs(l, l + period - 1));
    	
    	return dp[l][r];
    }
    
    private int calPeriod(String text) {
    	int[] pi = prefixFunction(text);
    	int len = text.length();
    	int p = pi[len - 1];
    	int m = len % (len - p);
    	return m == 0 ? len - p: len;
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

        while (true) {
        	String text = scanner.next();
        	if (text.compareTo("*") == 0) {
        		break;
        	}
        	Main solution = new Main(text);
	        int ans = solution.solve();
	        printWriter.println(ans);
	        printWriter.flush();
        }
    }
}
