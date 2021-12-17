import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * spoj/uva
 * @author wl
 *
 */
public class Main {
	private static final boolean DEBUG = false;
    private int[] a, b;
    
    public Main(int[] a, int[] b) {
    	this.a = a;
    	this.b = b;
    }
    
    public int solve() {
    	
    	int n = a.length;
    	int[] text = new int[n - 1];
    	for (int i = 0; i < n - 1; i++) {
    		text[i] = a[i + 1] - a[i];
    	}
    	int w = b.length;
    	int[] pattern = new int[w - 1];
    	for (int i = 0; i < w - 1; i++) {
    		pattern[i] = b[i + 1] - b[i];
    	}
    	
    	int[] pi = prefixFunction(pattern);
    	int ans = 0;
    	int textLen = n - 1;
    	int j = 0;
    	for (int i = 0; i < textLen; i++) {
    		while (j > 0 && text[i] != pattern[j]) {
    			j = pi[j - 1];
    		}
    		
    		if (text[i] == pattern[j]) {
    			j++;
    		}
    		
    		if (j == w - 1) {
    			ans++;
    			j = pi[j - 1];
    		}
    	}
    	return ans;
    }
    
    private int[] prefixFunction(int[] pattern) {
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

        FastScanner scanner;

        PrintWriter printWriter = new PrintWriter(new BufferedOutputStream(System.out));

        if (DEBUG) {
            scanner = new FastScanner(new BufferedInputStream(new FileInputStream("f:\\OJ\\uva_in.txt")));
            //printWriter = new PrintWriter(new BufferedOutputStream(new FileOutputStream("f:\\OJ\\uva_in_java.txt")));
        } else {
            scanner = new FastScanner(new BufferedInputStream(System.in));
            //printWriter = new PrintWriter(new BufferedOutputStream(System.out));;
        }
      
    	int n = scanner.nextInt();
    	int w = scanner.nextInt();
    	int[] a = new int[n];
    	int[] b = new int[w]; 
    	for (int i = 0; i < n; i++) {
    		a[i] = scanner.nextInt(); 
    	}
    	
    	for (int i = 0; i < w; i++) {
    		b[i] = scanner.nextInt();
    	}
    	if (w == 1) {
    		printWriter.println(n);
    	} else {
	    	Main solution = new Main(a, b);
	        int ans = solution.solve();
	        printWriter.println(ans);
    	}
    	
    	printWriter.flush();
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
