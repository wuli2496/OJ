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

public class Codeforce {
	private static final boolean DEBUG = false;
	private int n;
	private List<Integer>[] adjList; 
	private int last;
	private int[] dp;
	private int globalMin;
	
	public Codeforce(int n) {
		this.n = n;
		adjList = new ArrayList[n + 1];
		for (int i = 0; i < n + 1; i++) {
			adjList[i] = new ArrayList<>();
		}
		last = 0;
		dp = new int[n + 1];
		dp[0] = n + 1;
		globalMin = n + 1;
	}
	
	public void solve(int t, int z, PrintWriter printWriter) {
		int x = (z + last) % n + 1;
		if (t == 1) {
			globalMin = Math.min(globalMin, dp[x]);
		} else {
			last = Math.min(globalMin, dp[x]);
			printWriter.println(last);
		}
	}
	
	public void dfs(int u, int f) {
		dp[u] = Math.min(u, dp[f]);
		for (int v : adjList[u]) {
			if (v == f) {
				continue;
			}
			
			dfs(v, u);
		}
	}
	
	private void addEdge(int u, int v) {
		adjList[u].add(v);
		adjList[v].add(u);
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
		int q = scanner.nextInt();
		Codeforce codeforce = new Codeforce(n);
		for (int i = 1; i <= n - 1; i++) {
			int u = scanner.nextInt();
			int v = scanner.nextInt();
			codeforce.addEdge(u, v);
		}
		
		
		for (int i = 0; i < q; i++) {
			int t = scanner.nextInt();
			int z = scanner.nextInt();
			if (i == 0) {
				codeforce.dfs(z % n + 1, 0);
			}
			
			codeforce.solve(t, z, printWriter);
		}
		printWriter.flush();
	}
	
	static class Scanner {
        StringTokenizer st;
        BufferedReader br;
 
        public Scanner(InputStream s) {
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
