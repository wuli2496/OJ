import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

class Main {
	private static final boolean DEBUG = false;
	private static final long X = 131;
	private static final long MOD = 0x7fffffffffffffffL;
	private String text;
	private long[] xp;
	private long[] h;
	
	public Main(String text) {
		this.text = text;
	}
	
	public String solve() {
		init();
		
		int n = text.length();
		int[] ans = new int[n + 1];
		boolean[][] vis = new boolean[n][n + 1];
		ans[1] = n;
		
		for (int i = 0; i < n; ++i) {
			for (int j = 1; i + j <= n; ++j) {
				if (!vis[i][j]) {
					vis[i][j] = true;
					
					int num = 1;
					long tmp = (h[i] - h[i + j] * xp[j]) & MOD;
					int now = i + j;
					while (now + j <= n && ((h[now] - h[now + j] * xp[j]) & MOD) == tmp) {
						num++;
						vis[now][j] = true;
						now += j;
						ans[num] = Math.max(j * num, ans[num]);
					}
				}
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= n; ++i) {
			sb.append(ans[i]);
			if (i != n) {
				sb.append(" ");
			}
		}
		return sb.toString();
	}
	
	private void init() {
		int len = text.length();
		xp = new long[len + 1];
		xp[0] = 1;
		for (int i = 1; i <= len; ++i) {
			xp[i] = (xp[i - 1] * X) & MOD;
		}
		
		h = new long[len + 1];
		h[len] = 0;
		for (int i = len - 1; i >= 0; i--) {
			h[i] = (h[i + 1] * X + (text.charAt(i) - 'a')) & MOD;
		}
	}
	
	public static void main(String[] args) throws IOException {
		
		Scanner scanner;
		
		PrintWriter printWriter = new PrintWriter(new BufferedOutputStream(System.out));
		
		if (DEBUG) {
			scanner = new Scanner(new BufferedInputStream(new FileInputStream("f:\\OJ\\uva_in.txt")));
		} else {
			scanner = new Scanner(new BufferedInputStream(System.in));
		}
		
		int t = scanner.nextInt();
		for (int i = 0; i < t; ++i) {
			String text = scanner.next();
			Main uva = new Main(text);
			String ans = uva.solve();
			printWriter.print("Case #" + (i + 1) + ": ");
			printWriter.println(ans);
			
			printWriter.flush();
		}
		scanner.close();
	}
}
