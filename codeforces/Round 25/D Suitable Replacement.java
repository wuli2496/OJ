import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Codeforce {
	private static final boolean DEBUG = false;
	private static final int N = 26;
	private int[] cnts;
	private int[] cntt;
	private int cnt;
	
	public String solve(String s, String t) {
		cnt = 0;
		cnts = new int[N];
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '?') {
				cnt++;
			} else {
				cnts[s.charAt(i) - 'a']++;
			}
		}
		
		
		if (cnt == 0) {
			return s;
		}
		
		
		cntt = new int[N];
		for (int i = 0; i < t.length(); i++) {
			cntt[t.charAt(i) - 'a']++;
		}
		
		int l = 0, r = (int) 1e6;
		int ans = 0;
		while (l <= r) {
			int mid = (l + r) >> 1;
			if (check(mid)) {
				ans = mid;
				l = mid + 1;
			} else {
				r = mid - 1;
			}
		}
		
		for (int i = 0; i < N; ++i) {
			cntt[i] = Math.max(cntt[i] * ans - cnts[i], 0);
		}
		
		StringBuilder sb = new StringBuilder(s);
		int p = 0;
		for (int i = 0; i < s.length(); ++i) {
			
			while (p < N - 1 && cntt[p] == 0) p++;
			if (s.charAt(i) == '?') {
				sb.setCharAt(i, (char) (p + 'a'));
				cntt[p]--;
			}
		}
		
		return sb.toString();
	}
	
	private boolean check(int mid) {
		long result = 0;
		for (int i = 0; i < N; ++i) {
			result += ((cnts[i] >= (long)cntt[i] * mid) ? 0 : ((long)cntt[i] * mid - cnts[i]));
		}
		
		return result <= cnt;
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
			String t = scanner.next();
			Codeforce codeforce = new Codeforce();
			String ans = codeforce.solve(s, t);
			printWriter.println(ans);
			
			printWriter.flush();
		}

		scanner.close();
	}
}
