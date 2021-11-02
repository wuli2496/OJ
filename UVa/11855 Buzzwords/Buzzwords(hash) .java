import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
	private static final boolean DEBUG = false;
	private static final int P = 131;
	private static final long MOD = 1000000009;
	private String text;
	private long[] xp;
	private long[] h;
	
	public Main(String text) {
		this.text = text;
		init();
	}
	
	public List<Integer> solve() {
		List<Integer> ans = new ArrayList<>(); 
		int len = text.length();
		for (int i = 1; i <= len; i++) {
			int cnt = dup(i);
			if (cnt <= 1) {
				break;
			}
			
			ans.add(cnt);
		}
		return ans;
	}
	
	private int dup(int checkLen) {
		int len = text.length();
		int max = len - checkLen + 1;
		
		List<Long> hash = new ArrayList<>(); 
		for (int i = 0; i < max; ++i) {
			long t = (h[i] - h[i + checkLen] * xp[checkLen]) % MOD;
			if (t < 0) {
				t = (t + MOD) % MOD;
			}
			hash.add(t);
		}
		
		Collections.sort(hash);
		int cnt = 1;
		int ans = 1;
		for (int i = 1; i < hash.size(); ++i) {
			if (hash.get(i).compareTo(hash.get(i - 1)) != 0) {
				ans = Math.max(ans,  cnt);
				cnt = 1;
			} else {
				cnt++;
			}
		}
		
		ans = Math.max(ans, cnt);
		return ans;
	}
	
	private void init() {
		int len = text.length();
		xp = new long[len + 1];
		xp[0] = 1;
		for (int i = 1; i <= len; ++i) {
			xp[i] = (xp[i - 1] * P) % MOD;
			if (xp[i] < 0) {
				xp[i] = (xp[i] + MOD) % MOD;
			}
		}
		
		h = new long[len + 1];
		h[len]= 0;
		for (int i = len - 1; i >= 0; i--) {
			h[i] = (h[i + 1] * P + (text.charAt(i) - 'A')) % MOD;
			if (h[i] < 0) {
				h[i] = (h[i] + MOD) % MOD;
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		
		Scanner scanner;
		
		PrintWriter printWriter;
		
		if (DEBUG) {
			scanner = new Scanner(new BufferedInputStream(new FileInputStream("f:\\OJ\\uva_in.txt")));
			printWriter = new PrintWriter(new BufferedOutputStream(new FileOutputStream("f:\\OJ\\uva_in_java.txt")));
		} else {
			scanner = new Scanner(new BufferedInputStream(System.in));
			printWriter = new PrintWriter(new BufferedOutputStream(System.out));
		}
		
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			if (line == null || line.equals("")) {
				break;
			}
			
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < line.length(); ++i) {
				if (!Character.isUpperCase(line.charAt(i))) {
					continue;
				}
				sb.append(line.charAt(i));
			}
			
			String s = sb.toString();
			if (s.length() == 0) {
				printWriter.println("0");
			} else {
				Main main = new Main(sb.toString());
				List<Integer> ans = main.solve();
				
				ans.forEach(printWriter::println);
				printWriter.println();
			}
			printWriter.flush();
		}
		
		scanner.close();
	}
}