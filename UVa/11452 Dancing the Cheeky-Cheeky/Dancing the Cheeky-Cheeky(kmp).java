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
	private static final int N = 8;
    private String s;
    
    public Main(String x) {
    	this.s = x;
    }
    
    public String solve() {
    	int[] pi = prefixFunction(s);
    	int n = s.length();
    	int period = 0;
    	for (int i = n - 1; i >= 0; i--) {
    		if ((i + 1) % (i + 1 - pi[i]) == 0) {
    			period = i + 1 - pi[i];
    			break;
    		}
    	}
    	
    	int r = n % period;
    	StringBuilder sb = new StringBuilder();
     	for (int i = 0; i < N; i++) {
     		sb.append(s.charAt(r));
     		r = (r + 1) % period;
    	}
     	sb.append("...");
     	
     	return sb.toString(); 
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
        	String text = scanner.next();
        	Main solution = new Main(text);
	        String ans = solution.solve();
	        printWriter.println(ans);
	        printWriter.flush();
        }
    }
}
