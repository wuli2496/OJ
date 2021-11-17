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
	private String s;
	
	public Codeforce(String s) {
		this.s = s;
	}
	
	public List<Integer> solve() {
		List<Integer> result = new ArrayList<>();
		int n = s.length();
		int[][] dp = new int[n + 1][n + 1];
		int[] cnt = new int[n + 1];
		for (int len = 1; len <= n; len++) {
			for (int l = 0; l + len - 1 < n; l++) {
				int r = l + len - 1;
				if (l == r) {
					dp[l][r] = 1;
				} else if (s.charAt(l) == s.charAt(r) && (l + 1 > r - 1 || dp[l + 1][r - 1] != 0)) {
					dp[l][r] = dp[l][l + len / 2 - 1] + 1;
				}
				cnt[dp[l][r]]++;
			}
		}
		
		for (int i = n - 1; i >= 1; i--) {
			cnt[i] += cnt[i + 1]; 
		}
		
		for (int i = 1; i <= n; i++) {
			result.add(cnt[i]);
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

		
		String s = scanner.next();
		Codeforce codeforce = new Codeforce(s);
		List<Integer> ans = codeforce.solve();
		for (int i = 0; i < ans.size(); i++) {
			printWriter.print(ans.get(i));
			if (i != ans.size() - 1) {
				printWriter.print(" ");
			}
		}
		printWriter.println();
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