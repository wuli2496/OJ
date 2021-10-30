import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.BufferedInputStream;

public class Codechef {
	private static final boolean DEBUG = false;
	private static final long X = 131;
	private static final long MOD = 0x7fffffffffffffffL;
	private String text;
	private String pattern;
	private int k;

	
	public Codechef(String text,  String pattern, int k) {
		this.text = text;
		this.pattern = pattern;
		this.k = k;
	}
	
	public String solve() {
		int left = 1, right = pattern.length();
		int result = 0;
		
		while (left <= right) {
			int mid = (left + right) / 2;
			if (check(mid)) {
				result = mid;
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		
		if (result == 0) {
			return "IMPOSSIBLE";
		}
		
		return pattern.substring(0, result);
	}
	
	private boolean check(int len) {
		long textHash = calHash(text, len);
		long xp = init(len);
		long hash = calHash(pattern, len);
		
		int cnt = 0;
		if (textHash == hash) {
			++cnt;
		}
		
		for (int i = len; i < text.length(); ++i) {
			textHash = ((textHash - text.charAt(i - len) * xp) * X + text.charAt(i)) & MOD;
			if (textHash == hash) {
				++cnt;
				if (cnt >= k) {
					return true;
				}
			}
		}
		
		return cnt >= k;
		
	}
	
	private long init(int k) {
		long xp = 1;
		for (int i = 1; i <= k - 1; ++i) {
			xp = (xp * X) & MOD;
		}
		
		return xp;
	}
	
	private long calHash(String str, int k) {
		long result = 0;
		
		for (int i = 0; i < k; ++i) {
			result =  (result * X + str.charAt(i)) & MOD;
		}
		
		return result;
	}

	
	public static void main(String[] args) throws IOException {
		
		Scanner scanner;
		
		PrintWriter printWriter = new PrintWriter(new BufferedOutputStream(System.out));
		
		if (DEBUG) {
			scanner = new Scanner(new BufferedInputStream(new FileInputStream("f:\\OJ\\uva_in.txt")));
		} else {
			scanner = new Scanner(new BufferedInputStream(System.in));
		}
		
		//while (scanner.hasNext()) {
			String text = scanner.nextLine();
			String pattern = scanner.nextLine();
			int k = Integer.parseInt(scanner.nextLine());
			Codechef codechef = new Codechef(text,  pattern, k);
			String ans = codechef.solve();
			printWriter.println(ans);
			
			printWriter.flush();
		//}
		scanner.close();
	}

}
