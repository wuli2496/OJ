import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Codeforce {
	private static final boolean DEBUG = false;
	private List<List<Integer>> inList;
	private List<List<Integer>> outList;
	private int n;
	
	public Codeforce(int n) {
		this.n = n;
		inList = new ArrayList<>();
		outList = new ArrayList<>();
		for (int i = 0; i < n + 1; i++) {
			inList.add(new ArrayList<>());
			outList.add(new ArrayList<>());
		}
	}
	
	public List<Integer> solve() {
		List<Integer> result = new ArrayList<>();
		PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
		
		for (int i = 1; i <= n; ++i) {
			if (outList.get(i).size() == 0) {
				pq.add(i);
			}
		}
		
		
		int curLabel = n;
		int[] vals = new int[n + 1];
		while (!pq.isEmpty()) {
			Integer cur = pq.poll();
			vals[cur] = curLabel--;
			
			for (int v : inList.get(cur)) {
				outList.get(v).remove(cur);
				if (outList.get(v).size() == 0) {
					pq.add(v);
				}
			}
		}
		
		for (int i = 1; i <= n; ++i) {
			result.add(vals[i]);
		}
		return result;
	}
	
	public void addEdge(int v, int u) {
		outList.get(v).add(u);
		inList.get(u).add(v);
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
			int m = scanner.nextInt();
			Codeforce codeforce = new Codeforce(n);
			for (int i = 0; i < m; i++) {
				int v = scanner.nextInt();
				int u = scanner.nextInt();
				codeforce.addEdge(v, u);
			}
			List<Integer> ans = codeforce.solve();
			for (int i = 0; i < ans.size(); i++) {
				printWriter.print(ans.get(i));
				if (i != ans.size() - 1) {
					printWriter.print(" ");
				}
			}
			printWriter.println();
			
			printWriter.flush();
		}

		scanner.close();
	}
}
