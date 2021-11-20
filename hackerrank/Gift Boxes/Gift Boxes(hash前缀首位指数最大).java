import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Solution {
	private static final boolean DEBUG = false;
    private static final long P = 131;
    private static final long MOD = 1000000009;
    private String g, c;
    private long[] xp;
    private long[] hash;
   
	public Solution(String g, String c) {
        this.g = g;
        this.c = c;
        int n = c.length();
        xp = new long[n + 1];
        xp[0] = 1;
        
        for (int i = 1; i <= n; i++) {
        	xp[i] = xp[i - 1] * P % MOD;
        	if (xp[i] < 0) {
        		xp[i] = (xp[i] + MOD) % MOD;
        	}
        }
    }

    public int solve() {
    	long gHash = getHash(g);
    	
    	int n = c.length();
    	hash = new long[n + 1];
    	int ans = 0;
    	hash[0] = 0;
    	int index = 0;
    	int gSize = g.length();
    	for (int i = 0; i < n; i++) {
    		hash[index + 1] = (hash[index] * P + (c.charAt(i) - 'a')) % MOD;
    		if (hash[index + 1] < 0) {
    			hash[index + 1] = (hash[index + 1] + MOD) % MOD;
    		}
    		index++;
    		
    		if (index >= gSize) {
    			long tmp1 = getHash(index - gSize + 1, index);
        		
        		if (tmp1 == gHash) {
	    			index -= gSize;
	    			ans++;
        		}
    		}
    		
    	}
    	return ans;
    }
    
    private long getHash(int l, int r) {
    	long result = (hash[r] - hash[l - 1] * xp[r - l + 1]) % MOD;
    	if (result < 0) {
    		result = (result + MOD) % MOD;
    	}
    	
    	return result;
    }
    
    private long getHash(String s) {
    	long result = 0;
    	for (int i = 0; i < s.length(); i++) {
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
        	String g = scanner.next();
        	String c = scanner.next();
        	Solution codeforce = new Solution(g, c);
	        int ans = codeforce.solve();
	        printWriter.println(ans);
	        printWriter.flush();
        }
    }
}
