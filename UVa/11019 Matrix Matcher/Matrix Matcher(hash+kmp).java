import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
	private static final long P = 131;
	private static final long MOD = 1000000009;
	private static final int N = 105;
	private static final long[] base = new long[N];
	
    private char[][] s1, s2;
    private int n, m, x, y;
    
    
    public Main(char[][] s1, char[][] s2) {
    	this.s1 = s1;
    	this.s2 = s2;
    	n = s1.length;
    	m = s1[0].length;
    	x = s2.length;
    	y = s2[0].length;
    }
    
    public int solve() {
    	if (x > n || y > m) {
    		return 0;
    	}
    	
    	long[][] hash1 = new long[n - x + 1][m];
    	long[] hash2 = new long[y];
    	
    	for (int i = 0; i < m; i++) {
    		long hash = 0;
    		for (int j = 0; j < n; j++) {
    			if (j < x) {
    				hash = (hash * P + s1[j][i]) % MOD;
    				if (hash < 0) {
    					hash = (hash + MOD) % MOD;
    				}
    				if (j == x - 1) {
    					hash1[0][i] = hash;
    				}
    			} else {
    				hash = (hash * P % MOD - s1[j - x][i] * base[x] % MOD+ s1[j][i]) % MOD;
    				if (hash < 0) {
    					hash = (hash + MOD) % MOD;
    				}
    				hash1[j - x + 1][i] = hash;
    			}
    		}
    	}
    	
    	for (int i = 0; i < y; i++) {
    		long hash = 0;
    		for (int j = 0; j < x; j++) {
    			hash = (hash * P + s2[j][i]) % MOD;
    			if (hash < 0) {
    				hash = (hash + MOD) % MOD;
    			}
    		}
    		hash2[i] = hash;
    	}
    	
    	int ans = 0;
    	int[] pi = prefixFunction(hash2); 
    	for (int i = 0; i < n - x + 1; i++) {
    		ans += kmpSearch(hash2, hash1[i], pi);
    	}
    	
    	return ans;
    }
    
    private static void init() {
    	base[0] = 1;
    	for (int i = 1; i < N; i++) {
    		base[i] = base[i - 1] * P % MOD;
    		if (base[i] < 0) {
    			base[i] = (base[i] + MOD) % MOD; 
    		}
    	}
    }
    
    private int kmpSearch(long[] pattern, long[] text, int[] pi) {
    	int textLen = text.length;
    	int patternLen = pattern.length;
    	
    	int ans = 0;
    	int j = 0;
    	for (int i = 0; i < textLen; i++) {
    		while (j > 0 && text[i] != pattern[j]) {
    			j = pi[j - 1];
    		}
    		
    		if (pattern[j] == text[i]) {
    			j++;
    		}
    		
    		if (j == patternLen) {
    			ans++;
    			j = pi[j - 1];
    		}
    	}
    	
    	return ans;
    }
    
    private int[] prefixFunction(long[] pattern) {
    	int len = pattern.length;
    	int[] pi = new int[len];
    	for (int i = 1; i < len; i++) {
    		int j = pi[i - 1]; 
    		while (j > 0 && pattern[i] != pattern[j]) {
    			j = pi[j - 1];
    		}
    		
    		if (pattern[i] == pattern[j]) {
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
            //printWriter = new PrintWriter(new BufferedOutputStream(new FileOutputStream("f:\\OJ\\uva_in_java.txt")));
        } else {
            scanner = new Scanner(new BufferedInputStream(System.in));
            //printWriter = new PrintWriter(new BufferedOutputStream(System.out));;
        }
        init();
        int t = scanner.nextInt();
        while (t-- > 0) {
        	int n = scanner.nextInt();
        	int m = scanner.nextInt();
        	char[][] s1 = new char[n][m];
        	scanner.nextLine();
        	for (int i = 0; i < n; i++) {
        		String s = scanner.nextLine();
        		s1[i] = s.toCharArray();
        	}
        	int x = scanner.nextInt();
        	int y = scanner.nextInt();
        	scanner.nextLine();
        	char[][] s2 = new char[x][y];
        	for (int i = 0; i < x; i++) {
        		String s = scanner.nextLine();
        		s2[i] = s.toCharArray();
        	}
        	Main solution = new Main(s1, s2);
	        int ans = solution.solve();
	        printWriter.println(ans);
	        printWriter.flush();
        }
    }
}
