import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
	private static final boolean DEBUG = false;
	private static final int P = 131;
	private static final long MOD = 1000000009;
	
	private long[][] ha;
	private Map<Long, Integer> dict = new HashMap<>();
	private Map<Integer, List<Integer>> values = new HashMap<>();
	
	public void init(int k) {
		ha = new long[k][2];
		dict.clear();
		values.clear();
	}
	
	public void add(String s, int v) {
		long[] hashs = calHash(s);
		int t = 0;
		if (hashs[0] == hashs[1]) {
			if (dict.containsKey(hashs[0])) {
				t = dict.get(hashs[0]);
			} else {
				t = dict.size();
				ha[t][0] = ha[t][1] = hashs[0];
				dict.put(ha[t][0], t);
			}
		} else {
			if (dict.containsKey(hashs[0])) {
				t = dict.get(hashs[0]);
			} else {
				t = dict.size();
				ha[t][0] = hashs[0];
				ha[t][1] = hashs[1];
				dict.put(ha[t][0], t);
			}
		}
		
		values.computeIfAbsent(t, key -> new ArrayList<Integer>());
		values.get(t).add(v);
	}
	
	public int solve() {
		int t = dict.size();
		for (int i = 0; i < t; ++i) {
			Collections.sort(values.get(i), Collections.reverseOrder());
		}
		
		int ans = 0;
		int extra = 0;
		int ans1 = 0;
		for (int i = 0; i < t; ++i) {
			if (ha[i][0] == ha[i][1]) {
				List<Integer> vIntegers = values.get(i); 
				int len = vIntegers.size();
				int j;
				for (j = 0; j + 1 <= len - 1 && vIntegers.get(j) + vIntegers.get(j + 1) >= 0; j += 2) {
					ans += vIntegers.get(j) + vIntegers.get(j + 1);
					extra = Math.max(extra, -Math.min(vIntegers.get(j), vIntegers.get(j + 1)));
				}
				
				for (; j <= len - 1; ++j) {
					extra = Math.max(extra, vIntegers.get(j));
				}
			} else {
				if (!dict.containsKey(ha[i][1])) {
					continue;
				}
				int j = dict.get(ha[i][1]);
				int len = Math.min(values.get(i).size(), values.get(j).size());
				for (int k = 0; k < len; ++k) {
					if (values.get(i).get(k) + values.get(j).get(k) > 0) {
						ans1 += values.get(i).get(k) + values.get(j).get(k);
					}
				}
			}
		}
		
		return ans1 / 2 + ans + extra;
	}
	
	private long[] calHash(String s) {
		long[] hashs = new long[2];
		for (int i = 0; i < s.length(); ++i) {
			hashs[0] = (hashs[0] * P + (s.charAt(i) - 'a')) % MOD;
			if (hashs[0] < 0) {
				hashs[0] = (hashs[0] + MOD) % MOD;
			}
		}
		
		for (int i = s.length() - 1; i >= 0; i--) {
			hashs[1] = (hashs[1] * P + (s.charAt(i) - 'a')) % MOD;
			if (hashs[1] < 0) {
				hashs[1] = (hashs[1] + MOD) % MOD;
			}
		}
		
		return hashs;
	}
	
	public static void main(String[] args) throws IOException {
		
		Scanner scanner;
		
		PrintWriter printWriter = new PrintWriter(new BufferedOutputStream(System.out));;
		
		if (DEBUG) {
			scanner = new Scanner(new BufferedInputStream(new FileInputStream("f:\\OJ\\uva_in.txt")));
		} else {
			scanner = new Scanner(new BufferedInputStream(System.in));
			
		}
		
		while (scanner.hasNext()) {
			int k = scanner.nextInt();
			scanner.nextInt();
			Main main = new Main();
			main.init(k);
			
			for (int i = 0; i < k; ++i) {
				String s = scanner.next();
				int v = scanner.nextInt();
				main.add(s, v);
			}
			
			int ans = main.solve();
			printWriter.println(ans);
			printWriter.flush();
		}
		
		scanner.close();
	}
}