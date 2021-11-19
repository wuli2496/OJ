import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Codeforce {
    private static final boolean DEBUG = false;
    private static final long P = 131;
    private static final long MOD = 1000000009;
    private String[] s;
    private long[][] preHash;
    private long[] xp;

    public Codeforce(String[] s) {
        this.s = s;

        int n = Math.max(s[0].length(), Math.max(s[1].length(), s[2].length()));
        xp = new long[n + 1];
        xp[0] = 1;

        for (int i = 1; i <= n; i++) {
            xp[i] = xp[i - 1] * P % MOD;
            if (xp[i] < 0) {
                xp[i] = (xp[i] + MOD) % MOD;
            }
        }

        preHash = new long[3][];
        for (int i = 0; i < 3; i++) {
            preHash[i] = new long[s[i].length() + 1];
            preHash[i][0] = 0;
            for (int j = 1; j <= s[i].length(); j++) {
                preHash[i][j] = (preHash[i][j - 1] * P + s[i].charAt(j - 1) - 'a') % MOD;
                if (preHash[i][j] < 0) {
                    preHash[i][j] = (preHash[i][j] + MOD) % MOD;
                }
            }
        }
    }

    public int solve() {
        for (int i = 0; i < 2; i++) {
            for (int j = i + 1; j < 3; j++) {
                if (contain(i, j) || contain(j, i)) {
                    int x = (s[i].length() < s[j].length() ? j : i);
                    int y = 3 - i - j;
                    if (contain(x, y) || contain(y, x)) {
                        return Math.max(s[x].length(), s[y].length());
                    }

                    return s[x].length() + s[y].length() - Math.max(overlap(x, y), overlap(y, x));
                }
            }
        }

        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == j) {
                    continue;
                }

                int k = 3 - i - j;
                ans = Math.min(ans, s[i].length() + s[j].length() + s[k].length() - overlap(i, j) - overlap(j, k));
            }
        }
        return ans;
    }

    private boolean contain(int a, int b) {
        int a1 = s[a].length(), b1 = s[b].length();
        if (a1 > b1) {
            return false;
        }

        long ah = getPreHash(a, 1, a1);
        for (int i = 1; i + a1 <= b1; i++) {
            long bh = getPreHash(b, i, a1);
            if (ah == bh) {
                return true;
            }
        }
        return false;
    }

    private int overlap(int a, int b) {
        int a1 = s[a].length(), b1 = s[b].length();
        int ans = 0;
        for (int i = 1; i <= Math.min(a1, b1); i++) {
            long ah = getPreHash(a, a1 - i + 1, i);
            long bh = getPreHash(b, 1, i);
            if (ah == bh) {
                ans = i;
            }
        }

        return ans;
    }

    private long getPreHash(int index, int l, int len) {
        long result = (preHash[index][l + len - 1] - preHash[index][l - 1] * xp[len]) % MOD;
        if (result < 0) {
            result = (result + MOD) % MOD;
        }

        return result;
    }
    
    @SuppressWarnings("resource")
    public static void main(String[] args) throws IOException {

        java.util.Scanner scanner;

        PrintWriter printWriter = new PrintWriter(new BufferedOutputStream(System.out));

        if (DEBUG) {
            scanner = new java.util.Scanner(new BufferedInputStream(new FileInputStream("f:\\OJ\\uva_in.txt")));
        } else {
            scanner = new java.util.Scanner(new BufferedInputStream(System.in));
        }


        while (scanner.hasNext()) {
            String[] s = new String[3];
            for (int i = 0; i < 3; ++i) {
                s[i] = scanner.next();
            }

            Codeforce codeforce = new Codeforce(s);
            int ans = codeforce.solve();
            printWriter.println(ans);
            printWriter.flush();
        }
    }

    static class Scanner {
        StringTokenizer st;
        BufferedReader br;

        public Scanner(InputStream s) {
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