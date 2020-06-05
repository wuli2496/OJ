#include <bits/stdc++.h>

using namespace std;

#define forn(i, n) for (int i = 0; i < (int)(n); ++i)
#define for1(i, n) for (int i = 1; i <= (int)(n); ++i)
#define ford(i, n) for (int i = (int)(n) - 1; i >=0; --i)
#define fore(i, s, e) for (int i = (s); i < (e); ++i)
#define sz(x) (int)(x).size()
#define all(x) (x).begin(), (x).end()
#define rall(x) (x).rbegin(), (x).rend()

template <typename T> 
istream& operator>>(istream& in, vector<T>& v) 
{
    for (auto& i : v) {
        in >> i;
    }

    return in;
}

template <typename T>
ostream& operator<<(ostream& out, vector<T>& v)
{
    for (auto& i : v) {
        out << i << " ";
    }
    out << endl;

    return out;
}

template <typename T, typename U>
void chkMin(T& a, U b)
{
    if (a > b) {
        a = b;
    }    
}

template <typename T, typename U>
void chkMax(T& a, U b)
{
    if (a < b) {
        a = b;
    }
}

class Solution
{
public:
    Solution(int n, string& source, string& target)
        : s(source), t(target)
    {
        this->n = n;
    }

    int solve()
    {
        vector<vector<int>> suff(26, vector<int>(n + 2)), suff2(26, vector<int>(n + 2));
        
        for (int i = n; i >= 1; --i) {
            forn(j, 26) {
                suff[j][i] = suff[j][i + 1];
                suff2[j][i] = suff2[j][i + 1];
            }

            ++suff[s[i - 1] - 'a'][i];
            ++suff2[t[i - 1] - 'a'][i];
        }
        
        forn(i, 26) {
            if (suff[i][1] != suff2[i][1]) {
                return -1;
            }
        }
        vector<vector<int>> dp(n + 1, vector<int>(n + 1));
        forn(i, n + 1) {
            forn(j, n + 1) {
                dp[i][j] = INF;
            }
        }

        forn(i, n + 1) {
            dp[0][i] = 0;
        }

        for1(i, n) {
            for (int j = i; j <= n; ++j) {
                dp[i][j] = 1 + dp[i - 1][j];
                if (s[i - 1] == t[j - 1]) {
                    dp[i][j] = min(dp[i][j], dp[i - 1][j - 1]);
                }

                int k = t[j - 1] - 'a';
                if (suff[k][i + 1] > suff2[k][j + 1]) {
                    dp[i][j] = min(dp[i][j], dp[i][j - 1]);
                }
            }
        }

        if (dp[n][n] == INF) {
            return -1;
        }

        return dp[n][n];
    }
private:
    const string& s, t;
    int n;
    const static int INF = 0x3f3f3f3f;
};

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

#ifndef ONLINE_JUDGE
    ifstream fin("/home/wl/OJ/OJ/codeforces/codeforces_in");
    streambuf* cinback = cin.rdbuf(fin.rdbuf());
#endif
    
    int t;
    cin >> t;
    while (t--) {
        int n;
        string s, t;
        cin >> n >> s >> t;
        Solution solution(n, s, t);
        int ans = solution.solve();
        cout << ans << endl;
    }

#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif
}
