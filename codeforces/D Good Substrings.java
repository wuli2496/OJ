import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Codeforce {
    private static final boolean DEBUG = false;
    private static final long P = 1000000009;
    private static final long MOD = Long.MAX_VALUE;
    private static final int N = 26;
    private String s;
    private int k;
    private int[] goods;
    
	public Codeforce(String s, String good, int k) {
        this.s = s;
        this.k = k;
        
        goods = new int[N];
        for (int i = 0; i < N; i++) {
        	if (good.charAt(i) == '1') {
        		goods[i] = 1; 
        	}
        }
    }

    public int solve() {
    	Set<Long> sets = new HashSet<>();
    	int strLen = s.length();
    	int[] sum = new int[strLen];
    	for (int i = 0; i < strLen; i++) {
    		if (goods[s.charAt(i) - 'a'] == 0) {
    			sum[i] = 1;
    		}
    	}
    	for (int i = 1; i < strLen; i++) {
    		sum[i] += sum[i - 1];
    	}
    	
    	for (int i = 0; i < strLen; i++)  {
    		int cnt = 0;
    		long hash1 = 0;
    		for (int j = i; j < strLen; j++) {
    			if (i == 0) {
    				cnt = sum[j];
    			} else {
    				cnt = sum[j] - sum[i - 1];
    			}
    			
    			if (cnt > k) {
    				break;
    			}
    			hash1 = (hash1 * P + s.charAt(j) - 'a' + 1) % MOD;
    			if (hash1 < 0) {
    				hash1 = (hash1 + MOD) % MOD;
    			}
    			
    			if (!sets.contains(hash1)) {
					sets.add(hash1);
				}
    		}
    	}
    	
    	return sets.size();
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
        	String good = scanner.next();
        	int k = scanner.nextInt();
	        Codeforce codeforce = new Codeforce(s, good, k);
	        int ans = codeforce.solve();
	        printWriter.println(ans);
	        printWriter.flush();
        }

    }
}