import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * spoj/uva
 * @author wl
 *
 */
public class Main {
	private static final boolean DEBUG = true;
   
    private Map<Integer, List<Integer>> vToIndexMap = new HashMap<>();
    
    private void add(int v, int index) {
    	if (!vToIndexMap.containsKey(v)) {
    		vToIndexMap.put(v, new ArrayList<>());
    	}
    	
    	vToIndexMap.get(v).add(index);
    }
    
    public Integer solve(int k, int v) {
    	if (!vToIndexMap.containsKey(v) || vToIndexMap.get(v).size() < k) {
    		return 0;
    	}
    	
    	return vToIndexMap.get(v).get(k - 1);
    }
    
    @SuppressWarnings("resource")
    public static void main(String[] args) throws IOException {
    	
    	Scanner scanner;

        PrintWriter printWriter = new PrintWriter(new BufferedOutputStream(System.out));

        if (DEBUG) {
            scanner = new Scanner(new BufferedInputStream(new FileInputStream("f:\\OJ\\uva_in.txt")));
            //printWriter = new PrintWriter(new BufferedOutputStream(new FileOutputStream("f:\\OJ\\uva_in_java.txt")));
        } else {
            scanner = new Scanner(new BufferedInputStream(System.in));
            //printWriter = new PrintWriter(new BufferedOutputStream(System.out));;
        }
      
        while (scanner.hasNext()) {
        	int n = scanner.nextInt();
        	int m = scanner.nextInt();
	        Main solution = new Main();
	        for (int i = 0; i < n; i++) {
	        	int v = scanner.nextInt();
	        	solution.add(v, i + 1);
	        }
        	
	        for (int i = 0; i < m; i++) {
	        	int k = scanner.nextInt();
	        	int v = scanner.nextInt();
	        	Integer ans = solution.solve(k, v);
	            printWriter.println(ans);
	        }
            
        	printWriter.flush();
        }
        
  
    }
    
    static class FastScanner {
        StringTokenizer st;
        BufferedReader br;

        public FastScanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        public String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        public String nextLine() throws IOException {
            return br.readLine();
        }

        public double nextDouble() throws IOException {
            String x = next();
            StringBuilder sb = new StringBuilder("0");
            double res = 0, f = 1;
            boolean dec = false, neg = false;
            int start = 0;
            if (x.charAt(0) == '-') {
                neg = true;
                start++;
            }
            for (int i = start; i < x.length(); i++)
                if (x.charAt(i) == '.') {
                    res = Long.parseLong(sb.toString());
                    sb = new StringBuilder("0");
                    dec = true;
                } else {
                    sb.append(x.charAt(i));
                    if (dec)
                        f *= 10;
                }
            res += Long.parseLong(sb.toString()) / f;
            return res * (neg ? -1 : 1);
        }

        public boolean ready() throws IOException {
            return br.ready();
        }

    }
}
