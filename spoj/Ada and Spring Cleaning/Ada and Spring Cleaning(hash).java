import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.io.BufferedInputStream;

public class Codechef {
	private static final boolean DEBUG = false;
	private static final int X = 131;
	private String s;
	private long hash;
	private long xp;
	private int k;

	
	public Codechef(String s, int k) {
		this.s = s;
		this.k = k;
		init(k);
	}
	
	public int solve() {
		int strLen = s.length();
		Set<Long> set = new HashSet<>();
		set.add(hash);
		
		long curHash = hash;
		for (int i = k; i < strLen; ++i) {
			curHash = (curHash - (s.charAt(i - k) - 'a') * xp) * X + (s.charAt(i) - 'a');
			set.add(curHash);
		}
		return set.size();
	}
	
	private void init(int k) {
		hash = 0;
		
		for (int i = 0; i < k; ++i) {
			hash =  hash * X + (s.charAt(i) - 'a');
		}
		
		xp = 1;
		for (int i = 1; i <= k - 1; ++i) {
			xp = xp * X;
		}
	}

	
	public static void main(String[] args) throws IOException {
		Scanner scanner;
		
		PrintWriter printWriter = new PrintWriter(new BufferedOutputStream(System.out));
		
		if (DEBUG) {
			scanner = new Scanner(new BufferedInputStream(new FileInputStream("f:\\OJ\\uva_in.txt")));
		} else {
			scanner = new Scanner(System.in);
		}
		
		int t = scanner.nextInt();
		while (t-- > 0) {
			scanner.nextInt();
			int k = scanner.nextInt();
			String s = scanner.next();
			Codechef codechef = new Codechef(s,  k);
			int ans = codechef.solve();
			printWriter.println(ans);
		}
		
		printWriter.flush();
		scanner.close();
	}

}
