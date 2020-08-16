import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static final int N = 4;
    private final boolean DEBUG = false;

    public static void main(String[] args) {
	    Main solution = new Main();
	    solution.init();
	    while (solution.hasNext()) {
	        solution.input();
	        String ans = solution.solve();
	        System.out.println(ans);
        }
    }

    private void init() {
        InputStream inputStream = System.in;
        if (DEBUG) {
            try {
                inputStream = new BufferedInputStream(new FileInputStream("f:\\OJ\\uva_in.txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        scanner = new Scanner(inputStream);
    }

    private boolean hasNext() {
        return scanner.hasNext();
    }

    private void input() {
        n = scanner.nextInt();
        l = new int[n];
        lens = new int[n];
        ch = new char[n][N];
        fillStr = new char[n][N];
        vis = new int[n];
        for (int i = 0; i < n; ++i) {
            l[i] = scanner.nextInt();
        }
        s = scanner.next();
    }

    private String solve() {
        boolean ok = dfs(0, 0, 0);
        if (ok) {
            return "Yes";
        } else {
            return "No";
        }
    }

    private void dfs2(int cur) {
        int sum = 0;
        for (int i = lens[cur] - 1; i >= 0; --i) {
            if (ch[cur][i] == cur + '0') {
                if (p < 0) {
                    return;
                }

                fillStr[cur][i] = s.charAt(p);
                --p;
                ++sum;
            } else {
                fillStr[cur][i] = ch[cur][i];
                int index = ch[cur][i] - '0';
                if (vis[index] == 0) {
                    dfs2(index);
                } else {
                    p -= vis[index];
                }

                sum += vis[index];
            }
        }
        vis[cur] = sum;
    }

    private boolean dfs(int cur, int len, int pos) {
        if (cur == n) {
            Arrays.fill(vis, 0);
            p = s.length() - 1;
            dfs2(0);
            if (p != -1) {
                return false;
            }

            String s2 = new String(fillStr[0], 0, lens[0]);
            for (int j = 0; j < n; ++j) {
                for (int i = s2.length() - 1; i >= 0; --i) {
                    if (s2.charAt(i) >= '0' && s2.charAt(i) <= '9') {
                        int temp = s2.charAt(i) - '0';
                        s2 = s2.replace("" + s2.charAt(i), new String(fillStr[temp], 0, lens[temp]));
                    }
                }
            }

            if (s2.equals(s)) {
                return true;
            }

            return false;
        }

        if (len != 0 && pos == len) {
            if (dfs(cur + 1, 0, 0)) {
                return true;
            }

            return false;
        }

        if (len == 0) {
            for (int i = 1; i <= l[cur]; ++i) {
                lens[cur] = i;
                if (dfs(cur, i, pos)) {
                    return true;
                }
            }
        } else {
            for (int i = cur; i < n; ++i) {
                ch[cur][pos] = (char)(i + '0');
                if (dfs(cur, len, pos + 1)) {
                    return true;
                }
            }
        }

        return false;
    }

    private Scanner scanner;
    private int n;
    private int[] l;
    private String s;
    private int[] lens;
    private char[][] ch;
    private int[] vis;
    private int p;
    private char[][] fillStr;
}
