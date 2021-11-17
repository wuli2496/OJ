import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Codeforce {
    private static final boolean DEBUG = false;
    private static final long P = 131;
    private static final long MOD = 1000000009;
    private String s;
    private long[] preHash;
    private long[] suffixHash;
    private long[] xp;

    public Codeforce(String s) {
        this.s = s;

        int n = s.length();
        preHash = new long[n + 1];
        suffixHash = new long[n + 1];
        xp = new long[n + 1];
        preHash[0] = 0;
        suffixHash[n] = 0;
        xp[0] = 1;

        for (int i = 1; i <= n; i++) {
            preHash[i] = (preHash[i - 1] * P + s.charAt(i - 1) - 'a') % MOD;
            if (preHash[i] < 0) {
                preHash[i] = (preHash[i] + MOD) % MOD;
            }

            xp[i] = xp[i - 1] * P % MOD;
            if (xp[i] < 0) {
                xp[i] = (xp[i] + MOD) % MOD;
            }

            suffixHash[n - i] = (suffixHash[n - i + 1] * P + s.charAt(n - i) - 'a') % MOD;
            if (suffixHash[n - i] < 0) {
                suffixHash[n - i] = (suffixHash[n - i] + MOD) % MOD;
            }
        }
    }

    public List<Integer> solve() {
        List<Integer> result = new ArrayList<>();
        int n = s.length();
        int[][] dp = new int[n + 1][n + 1];
        int[] cnt = new int[n + 1];
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }
        cnt[1] = n;
        for (int len = 2; len <= n; len++) {
            for (int l = 0; l + len - 1 < n; l++) {
                int r = l + len - 1;
                int ll = l + len / 2 - 1;
                int rr = r - len / 2 + 1;

                if (getPreHash(l + 1, ll + 1) == getSuffixHash(rr, r + 1)) {
                    dp[l][r] = Math.min(dp[l][ll], dp[rr][r]) + 1;
                }
                cnt[dp[l][r]]++;
            }
        }

        for (int i = n - 1; i >= 1; i--) {
            cnt[i] += cnt[i + 1];
        }

        for (int i = 1; i <= n; i++) {
            result.add(cnt[i]);
        }

        return result;
    }

    private long getPreHash(int l, int r) {
        long result = (preHash[r] - preHash[l - 1] * xp[r - l + 1]) % MOD;
        if (result < 0) {
            result = (result + MOD) % MOD;
        }

        return result;
    }

    private long getSuffixHash(int l, int r) {
        long result = (suffixHash[l] - suffixHash[r] * xp[r - l]) % MOD;
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


        String s = scanner.next();
        Codeforce codeforce = new Codeforce(s);
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