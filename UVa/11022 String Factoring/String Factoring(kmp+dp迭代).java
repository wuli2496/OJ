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
    private int[][] dp;
    private String s;
    
    public Main(String x) {
    	this.s = x;
    }
    
    public int solve() {
    	int n = s.length();
    	dp = new int[n][n];
    	for (int i = 0; i < n; i++) {
    		dp[i][i] = 1;
    	}
    	
    	for (int len = 2; len <= n; len++) {
    		for (int l = 0; l + len <= n; l++) {
    			int r = l + len - 1;
    			dp[l][r] = Integer.MAX_VALUE; 
    			for (int k = l; k < r; k++) {
    				dp[l][r] = Math.min(dp[l][r], dp[l][k] + dp[k + 1][r]);
    			}
    			int period = calPeriod(s.substring(l, r + 1));
    			dp[l][r] = Math.min(dp[l][r], dp[l][l + period - 1]);
    		}
    	}
    	
    	return dp[0][n - 1];
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
