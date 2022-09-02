import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
	private static final boolean DEBUG = false;
	private PriorityQueue<Item> pq = new PriorityQueue<>();
    
	static class Item implements Comparable<Item> {
		int qNum;
		int period;
		int time;
		
		@Override
		public int compareTo(Main.Item o) {
			if (time != o.time) {
				return time - o.time;
			}
			
			return qNum - o.qNum;
		}
		
	}
	
	
    public void addItem(int qNum, int period) {
    	Item item = new Item();
    	item.qNum = qNum;
    	item.period = period;
    	item.time = item.period;
    	pq.add(item);
    }
    
    public int getQNum() {
    	
    	Item item = pq.poll();
    	int result = item.qNum;
    	
    	item.time = item.time + item.period;
    	pq.add(item);
    	
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
        
        Main uva = new Main();
        while (scanner.hasNext()) {
        	String s = scanner.next();
        	if ("#".equals(s)) {
        		break;
        	}
        	
        	int qNum = scanner.nextInt();
        	int period = scanner.nextInt();
        	uva.addItem(qNum, period);
        }
        
        int k = scanner.nextInt();
        while (k-- > 0) {
        	int qNum = uva.getQNum();
        	printWriter.println(qNum);
        	printWriter.flush();
        }
    }
}
