import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Codeforce {
    private static final boolean DEBUG = false;
    private static final int N = 26;
    private String pattern, text;
    
	public Codeforce(String s, String t) {
        this.text = s;
        this.pattern = t + "#";
    }

    public int solve() {
    	int[][] next = computeAutomation(pattern);
    	int textLen = text.length();
    	int patternLen = pattern.length();
    	int[][] dp = new int[textLen + 1][patternLen + 1];
    	for (int i = 0; i < textLen; i++) {
    		Arrays.fill(dp[i], -1);
    	}
    	
    	dp[0][0] = 0;
    	for (int i = 0; i < textLen; i++) {
    		for (int j = 0; j <= patternLen; j++) {
    			if (dp[i][j] < 0) continue;
    			if (text.charAt(i) == '?') {
    				for (int k = 0; k < N; k++) {
    					int nextState = next[j][k];
    					dp[i + 1][nextState] = Math.max(dp[i + 1][nextState], dp[i][j] + (nextState == patternLen - 1 ? 1 : 0));
    				}
    			} else {
    				int nextState = next[j][text.charAt(i) - 'a'];
    				dp[i + 1][nextState] = Math.max(dp[i + 1][nextState], dp[i][j] + (nextState == patternLen - 1? 1 : 0));
    			}
    			
    		}
    	}
    	
    	int ans = 0;
    	for (int i = 0; i < patternLen; i++) {
    		ans = Math.max(ans,  dp[textLen][i]);
    	}
    	return ans;
    }
    
    private int[][] computeAutomation(String pattern) {
    	int n = pattern.length();
    	int[][] aut = new int[n][N];
    	
    	int[] pi = prefixFunction(pattern);
    	for (int i = 0; i < n; i++) {
    		for (int j = 0; j < N; j++) {
    			if (i > 0 && 'a' + j != pattern.charAt(i)) {
    				aut[i][j] = aut[pi[i - 1]][j];
    			} else {
    				aut[i][j] = i + (pattern.charAt(i) == 'a' + j ? 1 : 0);
    			}
    		}
    	}
    	
    	return aut;
    }
    
    private int[] prefixFunction(String pattern) {
    	int len = pattern.length();
    	int[] pi = new int[len];
    	for (int i = 1; i < len; i++) {
    		int j = pi[i - 1]; 
    		while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
    			j = pi[j - 1];
    		}
    		
    		if (pattern.charAt(i) == pattern.charAt(j)) {
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

        while (scanner.hasNext()) {
        	String s = scanner.next();
        	String t = scanner.next();
	        Codeforce codeforce = new Codeforce(s, t);
	        int ans = codeforce.solve();
	        printWriter.println(ans);
	        printWriter.flush();
        }

    }
}