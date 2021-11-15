import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Codeforce {
	private static final boolean DEBUG = false;
	private static final long P = 131;
	private static final long MOD = 100000009;
	
	private String s;
	private long[] xp;
	private long[] hash;
	private int[][] len;
	
	public Codeforce(String s) {
		this.s = s;
		int n = s.length();
		xp = new long[n];
		hash = new long[n];
		
		xp[0] = 1;
		hash[0] = s.charAt(0) - 'a';
		for (int i = 1; i < n; i++) {
			xp[i] = xp[i - 1] * P % MOD;
			if (xp[i] < 0) {
				xp[i] = (xp[i] + MOD) % MOD;
			}
			
			hash[i] = (hash[i - 1] * P + (s.charAt(i) - 'a')) % MOD;
			if (hash[i] < 0) {
				hash[i] = (hash[i] + MOD) % MOD; 
			}
		}
		
		len = new int[n][n];
		for (int i = 0; i < n; ++i) {
			for (int j = i; j < n; ++j) {
				len[i][j] = j - i + 1;
			}
		}
	}
	
	public int solve() {
		int n = s.length();
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (j + len[i][j - 1] <= n) {
					long leftHalf = getHash(i, i + len[i][j - 1] - 1);
					long rightHalf = getHash(j, j + len[i][j - 1] - 1);
					if (leftHalf == rightHalf) {
						len[i][j + len[i][j - 1] - 1] = Math.min(len[i][j + len[i][j - 1] - 1], len[i][j - 1]);
					}
				}
			}
		}
		
		int[] dp = new int[n];
		Arrays.fill(dp, n);
		for (int i = 0; i < n; i++) {
			dp[i] = len[0][i] + getLen((i + 1) / len[0][i]); 
			for (int j = 0; j < i; j++) {
				dp[i] = Math.min(dp[i], dp[j] + len[j + 1][i] + getLen((i - j) / len[j + 1][i]));
			}
		}
		
		return dp[n - 1];
	}
	
	private long getHash(int l, int r) {
		long result = hash[r];
		if (l > 0) {
			result = (result - hash[l - 1] * xp[r - l + 1]) % MOD;
		}
		
		if (result < 0) {
			result = (result + MOD) % MOD;
		}
		return result;
	}
	
	private int getLen(int num) {
		int ans = 0;
		while (num > 0) {
			ans++;
			num /= 10;
		}
		
		return ans;
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
			Codeforce codeforce = new Codeforce(s);
			int ans = codeforce.solve();
			printWriter.println(ans);
			printWriter.flush();
		}

		scanner.close();
	}
}
