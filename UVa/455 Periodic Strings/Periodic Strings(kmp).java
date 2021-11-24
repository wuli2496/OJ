import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * spoj
 * @author wl
 *
 */
public class Main {
	private static final boolean DEBUG = false;
    
    public int solve(String text) {
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

        int t = scanner.nextInt();
        while (t-- > 0) {
        	String text = scanner.next();
        	Main solution = new Main();
	        int ans = solution.solve(text);
	        printWriter.println(ans);
	        if (t != 0) {
	        	printWriter.println();
	        }
	        printWriter.flush();
        }
    }
}
