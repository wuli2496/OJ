import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Codeforce {
	private static final boolean DEBUG = false;
	private static final int[] dx = { 1, 0, -1, 1 };
	private static final int[] dy = { 0, 1, 1, 1 };

	private boolean solve(char[][] ch) {
		for (int i = 1; i <= 10; i++) {
			for (int j = 1; j <= 10; j++) {
				if (ch[i][j] == '.') {
					for (int dir = 0; dir < 4; dir++) {
						int p, q;
						for (p = 1; ch[i + p * dx[dir]][j + p * dy[dir]] == 'X'; p++)
							;
						for (q = 1; ch[i - q * dx[dir]][j - q * dy[dir]] == 'X'; q++)
							;
						
						if (p + q > 5) {
							return true;
						}
					}
				}
			}
		}
		
		return false;
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
			char[][] ch = new char[12][12];
			for (int i = 0; i < 12; i++) {
				ch[0][i] = 'O';
				ch[11][i] = 'O';
			}

			for (int i = 1; i <= 10; i++) {
				ch[i][0] = ch[i][11] = 'O';
				String s = scanner.next();
				for (int j = 0; j < s.length(); j++) {
					ch[i][j + 1] = s.charAt(j);
				}
			}
			
			Codeforce codeforce = new Codeforce();
			boolean ans = codeforce.solve(ch);
			if (ans) {
				printWriter.println("YES");
			} else {
				printWriter.println("NO");
			}
			printWriter.flush();
		}

		scanner.close();
	}
}
