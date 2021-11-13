import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Codeforce {
	private static final boolean DEBUG = false;

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		
		Scanner scanner;
		
		PrintWriter printWriter = new PrintWriter(new BufferedOutputStream(System.out));;
		
		if (DEBUG) {
			scanner = new Scanner(new BufferedInputStream(new FileInputStream("f:\\OJ\\uva_in.txt")));
		} else {
			scanner = new Scanner(new BufferedInputStream(System.in));
			
		}
		
		while (scanner.hasNext()) {
			scanner.nextInt();
			
			String s = scanner.next();
			int cnt = 0;
			for (int i = 0; i < s.length(); ++i) {
				if (s.charAt(i) == '1') {
					cnt++;
				} else if (s.charAt(i) == '0') {
					printWriter.print(cnt);
					cnt = 0;
				}
			}
			printWriter.println(cnt);
			printWriter.flush();
		}
		
		scanner.close();
	}
}
