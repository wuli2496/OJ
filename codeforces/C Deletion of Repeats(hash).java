import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Codeforce {
    private static final boolean DEBUG = false;
    private static final long P = 131;
    private static final long MOD = 1000000009;
    private long[] xp;
    private long[] hash;
    private int[] nums;
    private Map<Integer, Integer> numMap;
    private List<Integer>[] numSeqLists;
   
    @SuppressWarnings("unchecked")
	public Codeforce(int[] s) {
        this.nums = s;
        int n = nums.length;
        xp = new long[n + 1];
        hash = new long[n + 1];
        xp[0] = 1;
        hash[0] = 0;
        
        numMap = new HashMap<>();
        numSeqLists = new ArrayList[n];
        for (int i = 1; i <= n; i++) {
        	if (!numMap.containsKey(nums[i - 1])) {
        		int size = numMap.size();
        		numMap.put(nums[i - 1], size);
        		numSeqLists[size] = new ArrayList<>();
        	}
        	
        	numSeqLists[numMap.get(nums[i - 1])].add(i);
        	
        	xp[i] = xp[i - 1] * P % MOD;
        	if (xp[i] < 0) {
        		xp[i] = (xp[i] + MOD) % MOD;
        	}
        	
        	hash[i] = (hash[i - 1] * P + nums[i - 1]) % MOD;
        	if (hash[i] < 0) {
        		hash[i] = (hash[i] + MOD) % MOD;
        	}
        }
    }

    public int[] solve() {
    	int size = numMap.size();
    	List<int[]> range = new ArrayList<>();
    	for (int i = 0; i < size; i++) {
    		for (int j = 0; j < numSeqLists[i].size(); j++) {
    			for (int k = j + 1; k < numSeqLists[i].size(); k++) {
    				if (check(numSeqLists[i].get(j), numSeqLists[i].get(k))) {
    					range.add(new int[] {numSeqLists[i].get(k) - numSeqLists[i].get(j), numSeqLists[i].get(j)});
    				}
    			}
    		}
    	}
    	
    	Collections.sort(range, new Comparator<int[]>() {

			@Override
			public int compare(int[] o1, int[] o2) {
				// TODO Auto-generated method stub
				if (o1[0] != o2[0]) {
					return o1[0] - o2[0];
				}
				
				return o1[1] - o2[1];
			}
    		
		});
    	
    	int pos = 1;
    	for (int i = 0; i < range.size(); i++) {
    		int[] v = range.get(i);
    		if (v[1] >= pos) {
    			pos= v[1] + v[0];
    		}
    	}
    	
    	int[] result = new int[nums.length - pos + 1];
    	for (int i = 0; i < result.length; i++, pos++) {
    		result[i] = nums[pos - 1];
    		
    	}
    	
        return result;
    }
    
    private boolean check(int l, int r) {
    	int lr = r - 1;
    	int rr = 2 * r - l - 1;
    	if (rr > nums.length) {
    		return false;
    	}
    	
    	long leftHash = getHash(l, lr);
    	long rightHash = getHash(r, rr);
    	
    	return leftHash == rightHash;
    }
    
    private long getHash(int l, int r) {
    	long result = (hash[r] - hash[l - 1] * xp[r - l + 1]) % MOD;
    	if (result < 0) {
    		result = (result + MOD) % MOD;
    	}
    	
    	return result;
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
        	int[] nums = new int[n];
        	for (int i = 0; i < n; i++) {
        		nums[i] = scanner.nextInt();
        	}
	        Codeforce codeforce = new Codeforce(nums);
	        int[] ans = codeforce.solve();
	        
	        printWriter.println(ans.length);
	        for (int i = 0; i < ans.length; i++) {
	        	printWriter.print(ans[i]);
	        	if (i != ans.length - 1) {
	        		printWriter.print(" ");
	        	}
	        }
	        printWriter.println();
	        printWriter.flush();
        }

    }
}