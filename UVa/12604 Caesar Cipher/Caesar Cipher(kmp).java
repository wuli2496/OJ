import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * spoj/uva
 * @author wl
 *
 */
public class Main {
	private static final boolean DEBUG = false;
	private static final int N = 130;
    private String a, w, s;
    private int[] pos;
    
    public Main(String a, String w, String s) {
    	this.a = a;
    	this.w = w;
    	this.s = s;
    	
    	pos = new int[N];
    	for (int i = 0; i < a.length(); i++) {
    		pos[a.charAt(i)] = i;
    	}
    }
    
    public List<Integer> solve() {
    	List<Integer> results = new ArrayList<>();
    	int aLen = a.length();
    	int wLen = w.length();
    	for (int i = 0; i < aLen; i++) {
    		StringBuilder sb = new StringBuilder();
    		for (int j = 0; j < wLen; j++) {
    			char ch = a.charAt((pos[w.charAt(j)] + i) % aLen);
    			sb.append(ch);
    		}
    		String pattern = sb.toString();
    		int[] pi = prefixFunction(pattern); 
    		if (kmpSearch(pattern, s, pi) == 1) {
    			results.add(i);
    		}
    	}
    	
    	return results;
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
    		
    		if (j == patternLen) {
    			ans++;
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

        int n = scanner.nextInt();
        while (n-- > 0) {
        	String a = scanner.next();
        	String w = scanner.next();
        	String s = scanner.next();
        	Main solution = new Main(a, w, s);
	        List<Integer> ans = solution.solve();
	        if (ans.isEmpty()) {
	        	printWriter.println("no solution");
	        } else if (ans.size() == 1) {
	        	printWriter.println("unique: " + ans.get(0));
	        } else {
	        	printWriter.print("ambiguous:");
	        	for (int i = 0; i < ans.size(); i++) {
	        		printWriter.print(" " + ans.get(i));
	        	}
	        	printWriter.println();
	        }
	        printWriter.flush();
        }
    }
}
