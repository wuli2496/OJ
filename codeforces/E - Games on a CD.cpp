#include <bits/stdc++.h>

using namespace std;

using ll = long long;
using pii = pair<int, int>;

#define REP(i, a, b) for(int i = (a); i < (b); ++i)

const int MOD[2] = {1000000007, 1000000009};
const int MAXN = 2e6 + 5;
const int P = 131;

int n, k;
int xp[2][MAXN], h[2][MAXN];
string s;
map<pii, int> arr;
int vis[MAXN];
int tim;
int ans[MAXN];

void init()
{
    REP(i, 0, 2) {
        xp[i][0] = 1;
        REP(j, 1, MAXN) {
            xp[i][j] = (ll)xp[i][j - 1] * P % MOD[i];
        }
    }
}

void fastio()
{
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
}

void getPre(const string& str, int k)
{
    int len = str.length();
    REP(i, 0, 2) {
        h[i][0] = 0;
        for (int j = 1; j <= len; ++j) {
            h[i][j] = ((ll)h[i][j - 1] * P + str[j - 1]) % MOD[i];
        }

        for (int j = len + 1, m = 0; m < k; ++j, ++m) {
            h[i][j] = ((ll)h[i][j - 1] * P + str[m]) % MOD[i];
        }
    }
}

pii getHash(const string& s)
{
    int len = s.length();
    int res[2] = {0, 0};
    REP(i, 0, 2) {
        REP(j, 0, len) {
            res[i] = ((ll)res[i] * P + s[j]) % MOD[i];
        }
    }

    return pii(res[0], res[1]);
}

pii getHash(int l, int r)
{
    int res[2] = {0, 0};
    REP(i, 0, 2) {
        res[i] = (h[i][r] - (ll)h[i][l - 1] * xp[i][r - l + 1] % MOD[i] + MOD[i]) % MOD[i];
    }

    return pii(res[0], res[1]);
}

bool check(int st)
{
    tim++;
    int l = st, r = st + k - 1;
    for (int i = 1; i <= n; ++i, l += k, r += k) {
        pii p = getHash(l, r);
        if (!arr.count(p)) {
            return false;
        }
        int val = arr[p];
        if (vis[val] == tim) {
            return false;
        }
        vis[val] = tim;
        ans[i - 1] = val;
    }

    return true;
}

int main()
{
    fastio();
    #ifndef ONLINE_JUDGE
        ifstream fin("f:\\OJ\\uva_in.txt");
        streambuf* cinback = cin.rdbuf(fin.rdbuf());
    #endif // ONLINE_JUDGE

    init();

    while (cin >> n >> k) {
        arr.clear();
        tim = 1;
        cin >> s;
        getPre(s, k);
        int g;
        cin >> g;
        REP(i, 0, g) {
            cin >> s;
            pii p = getHash(s);
            arr[p] = i + 1;
        }

        bool ok = false;
        for (int i = 1; i <= k; ++i) {
            if (check(i)) {
                ok = true;
                break;
            }
        }

        if (!ok) {
            cout << "NO" << endl;
        } else {
            cout << "YES" << endl;
            REP(i, 0, n) {
                cout << ans[i] << (i == n - 1 ? '\n' : ' ');
            }
        }
    }

    #ifndef ONLINE_JUDGE
        cin.rdbuf(cinback);
    #endif // ONLINE_JUDGE
    return 0;
}
