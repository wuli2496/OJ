import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * spoj/uva
 * @author wl
 *
 */
public class Main {
	private static final boolean DEBUG = true;
    private boolean isStack = true;
    private boolean isPriorityQueue = true;
    private boolean isQueue = true;
    private Stack<Integer> stack;
    private Queue<Integer> queue;
    private PriorityQueue<Integer> priorityQueue;
    
    public Main() {
    	stack = new Stack<>();
    	queue = new LinkedList<>();
    	priorityQueue = new PriorityQueue<>(Collections.reverseOrder());
    }
    
    public void operation(int type, int val) {
    	if (type == 1) {
    		stack.push(val);
    		queue.add(val);
    		priorityQueue.add(val);
    		return;
    	}
    	
    	int v;
    	if (isStack) {
    		if (!stack.isEmpty()) {
    			v = stack.pop();
    			if (v != val) {
    				isStack = false;
    			}
    		} else {
    			isStack = false;
    		}
    	}
    	
    	if (isPriorityQueue) {
    		if (!priorityQueue.isEmpty()) {
    			v = priorityQueue.poll();
    			if (v != val) {
    				isPriorityQueue = false;
    			}
    		} else {
    			isPriorityQueue = false;
    		}
    	}
    	
    	if (isQueue) {
    		if (!queue.isEmpty()) {
    			v = queue.poll();
    			if (v != val) {
    				isQueue = false;
    			}
    		} else {
    			isQueue = false;
    		}
    	}
    }
    
    public String solve() {
    	int cnt = 0;
    	if (isStack) {
    		cnt++;
    	}
    	if (isPriorityQueue) {
    		cnt++;
    	}
    	if (isQueue) {
    		cnt++;
    	}
    	
    	if (cnt == 0) {
    		return "impossible";
    	}
    	
    	if (cnt >= 2) {
    		return "not sure";
    	}
    	
    	if (isStack) {
    		return "stack";
    	}
    	
    	if (isPriorityQueue) {
    		return "priority queue";
    	}
    	return "queue";
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
	        String str = scanner.next();
	        
	        Main solution = new Main();
	        int n = Integer.parseInt(str);
	        for (int i = 0; i < n; i++) {
	        	int type = scanner.nextInt();
	        	int val = scanner.nextInt();
	        	solution.operation(type, val);
	        }
        	
        	String ans = solution.solve();
            printWriter.println(ans);
            
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
