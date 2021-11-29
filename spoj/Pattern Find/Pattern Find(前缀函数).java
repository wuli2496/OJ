import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * spoj/uva
 * @author wl
 *
 */
public class Main {
	private static final boolean DEBUG = false;
    private String text, pattern;
    
    public Main(String text, String pattern) {
    	this.text = text;
    	this.pattern = pattern;
    }
    
    public List<Integer> solve() {
    	StringBuilder sb = new StringBuilder();
    	sb.append(pattern).append("#").append(text);
    	
    	String tmpString = sb.toString();
    	int[] pi = prefixFunction(tmpString);
    	
    	List<Integer> ans = new ArrayList<>();
    	for (int i = pattern.length() + 1; i < pi.length; i++) {
    		if (pi[i] == pattern.length()) {
    			ans.add(i - 2 * pattern.length() + 1);
    		}
    	}
    	
    	return ans;
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

        FastScanner scanner;

        PrintWriter printWriter = new PrintWriter(new BufferedOutputStream(System.out));

        if (DEBUG) {
            scanner = new FastScanner(new BufferedInputStream(new FileInputStream("f:\\OJ\\uva_in.txt")));
            //printWriter = new PrintWriter(new BufferedOutputStream(new FileOutputStream("f:\\OJ\\uva_in_java.txt")));
        } else {
            scanner = new FastScanner(new BufferedInputStream(System.in));
            //printWriter = new PrintWriter(new BufferedOutputStream(System.out));;
        }
      
        int t = scanner.nextInt();
        while (t-- > 0) {
        	String a = scanner.next();
        	String b = scanner.next();
        	Main solution = new Main(a, b);
	        List<Integer> ans = solution.solve();
	        if (ans.size() == 0) {
	        	printWriter.println("Not Found");
	        } else {
	        	printWriter.println(ans.size());
	        	for (int i = 0; i < ans.size(); i++) {
	        		printWriter.print(ans.get(i));
	        		printWriter.print(" \n".charAt(i == ans.size() - 1 ? 1 : 0));
	        	}
	        }
	        
	        if (t != 0) {
	        	printWriter.println();
	        }
	        
	        printWriter.flush();
        }
    }
    
    static class FastScanner {
        StringTokenizer st;
        BufferedReader br;

        public FastScanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        public String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        public String nextLine() throws IOException {
            return br.readLine();
        }

        public double nextDouble() throws IOException {
            String x = next();
            StringBuilder sb = new StringBuilder("0");
            double res = 0, f = 1;
            boolean dec = false, neg = false;
            int start = 0;
            if (x.charAt(0) == '-') {
                neg = true;
                start++;
            }
            for (int i = start; i < x.length(); i++)
                if (x.charAt(i) == '.') {
                    res = Long.parseLong(sb.toString());
                    sb = new StringBuilder("0");
                    dec = true;
                } else {
                    sb.append(x.charAt(i));
                    if (dec)
                        f *= 10;
                }
            res += Long.parseLong(sb.toString()) / f;
            return res * (neg ? -1 : 1);
        }

        public boolean ready() throws IOException {
            return br.ready();
        }

    }
}
