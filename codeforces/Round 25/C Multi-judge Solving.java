import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Codeforce {
	private static final boolean DEBUG = false;

	private int solve(int[] a, int k) {
		Arrays.sort(a);
		
		int ans = 0;
		for (int num : a) {
			if (num <= k) {
				continue;
			}
			
			while (num > 2 * k) {
				ans++;
				k = 2 * k;
			}
			
			k = Math.max(k,  num);
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
			int n = scanner.nextInt();
			int k = scanner.nextInt();
			int[] a = new int[n];
			for (int i = 0; i < n; ++i) {
				a[i] = scanner.nextInt();
			}
			Codeforce codeforce = new Codeforce();
			int ans = codeforce.solve(a, k);
			printWriter.println(ans);
			
			printWriter.flush();
		}

		scanner.close();
	}
}
