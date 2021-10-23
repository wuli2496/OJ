import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.BufferedInputStream;

class Codechef {
	private static final boolean DEBUG = false;
	private static final int X = 131;
	private String s;
	private long[] hash;
	private long[] xp;

	
	public Codechef(String s) {
		this.s = s;
	}
	
	public void init() {
		int len = s.length();
		hash = new long[len + 1];
		xp = new long[len + 1];
		hash[len] = 0;
		
		for (int i = len - 1; i >= 0; --i) {
			hash[i] = hash[i + 1] * X + s.charAt(i) - 'a';
		}
		
		xp[0] = 1;
		for (int i = 1; i <= len; ++i) {
			xp[i] = xp[i - 1] * X;
		}
	}
	
	public int solve(int p) {
		int strLen = s.length();
		int len = Math.min(p, strLen - p);
		
		int left = 1, right = len;
		int ans = 0;
		while (left <= right) {
			int mid = (left + right) / 2;
			if (check(p, mid)) {
				ans = mid;
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		
		return ans;
	}
	
	private boolean check(int p, int len) {
		long leftHash = hash[0] - hash[len] * xp[len];
		long rightHash = hash[p] - hash[p + len] * xp[len];
		
		return leftHash == rightHash;
	}
	
	public static void main(String[] args) throws IOException {
		Scanner scanner;
		
		PrintWriter printWriter = new PrintWriter(new BufferedOutputStream(System.out));
		
		if (DEBUG) {
			scanner = new Scanner(new BufferedInputStream(new FileInputStream("f:\\OJ\\uva_in.txt")));
		} else {
			scanner = new Scanner(System.in);
		}
		
		String s;
		s = scanner.next();
		Codechef codechef = new Codechef(s);
		codechef.init();
		
		int q = scanner.nextInt();
		for (int i = 0; i < q; ++i) {
			int p = scanner.nextInt();
			int ans = codechef.solve(p);
			printWriter.println(ans);
		}
		printWriter.flush();
		scanner.close();
	}

}
