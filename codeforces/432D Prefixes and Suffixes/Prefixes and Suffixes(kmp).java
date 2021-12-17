import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * spoj/uva
 * @author wl
 *
 */
public class Main {
	private static final boolean DEBUG = false;
    private String pattern;
    
    public Main(String text) {
    	this.pattern = text;
    }
    
    static class Node implements Comparable<Node> {
    	
    	int l;
    	int c;
    	
		@Override
		public int compareTo(Main.Node o) {
			// TODO Auto-generated method stub
			return l - o.l;
		}
    	
    }
    
    public List<Node> solve() {
    	int n = pattern.length();
    	int[] cnt = new int[n + 1];
    	
    	int[] pi = prefixFunction(pattern);
    	for (int i = 0; i < n; i++) {
    		cnt[pi[i]]++;
    	}
    	
    	for (int i = n - 1; i > 0; i--) {
    		cnt[pi[i - 1]] += cnt[i];
    	}
    	
    	for (int i = 0; i <= n; i++) {
    		cnt[i]++;
    	}
    
    	List<Node> ans = new ArrayList<>();
    	int cur = n - 1;
    	while (cur > 0) {
    		Node node = new Node();
    		node.l = cur + 1;
    		node.c = cnt[cur + 1];
    		ans.add(node);
    		
    		cur = pi[cur] - 1;
    	}
    	
    	if (cur == 0) {
    		if (cnt[cur + 1] > 0) {
    			Node node = new Node();
        		node.l = 1;
        		node.c = cnt[cur + 1];
        		ans.add(node);
    		}
    	}
    	
    	Collections.sort(ans);
    	
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
      
    	String text = scanner.next();
    	Main solution = new Main(text);
    	List<Main.Node> ans = solution.solve();
        printWriter.println(ans.size());
        ans.forEach(node -> {
        	printWriter.println(String.format("%d %d", node.l, node.c));
        });
	
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
