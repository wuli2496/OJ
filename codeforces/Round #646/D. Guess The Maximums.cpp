#include <bits/stdc++.h>

using namespace std;

#define forn(i, n) for (int i = 0; i < (int)(n); ++i)
#define for1(i, n) for (int i = 1; i <= (int)(n); ++i)
#define ford(i, n) for (int i = (int)(n) - 1; i >=0; --i)
#define fore(i, s, e) for (int i = (s); i < (e); ++i)
#define sz(x) (int)(x).size()
#define all(x) (x).begin(), (x).end()
#define rall(x) (x).rbegin(), (x).rend()

using vi = vector<int>;
using vvi = vector<vi>;

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
    Solution(int n, int k)
    {
        this->n = n;
        this->k = k;

        s.resize(k);
    }

    void readData(istream& in)
    {
        forn(i, k) {
            int c;
            in >> c;
            s[i].resize(c);
            forn(j, c) {
                in >> s[i][j];
            }
        }    
    }
    
    void solve()
    {
        int maxVal = query(n);
        
        int low = 1, high = n;
        int pos = 0;
        while (low <= high) {
            int mid = (low + high) / 2;
            int res = query(mid);
            if (res == maxVal) {
                pos = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        
        vi ans(k, maxVal);
        forn(i, k) {
            if (count(all(s[i]), pos) == 0) {
                continue;
            }

            vi toQ(n, 1);
            for (int x : s[i]) {
                toQ[x - 1] = 0;
            }

            cout << "? " << n - sz(s[i]) << ' ';
            forn(i, n) {
                if (toQ[i]) {
                    cout << i + 1 << ' ';
                }
            }
            cout << endl;
            cin >> ans[i];
        }

        cout << "! ";
        forn(i, k) {
            cout << ans[i] << ' ';
        }
        cout << endl;
        string trash;
        cin >> trash;
    }
    
private:
    int query(int n)
    {
        cout << "? " << n << ' ';
        for1(i, n) {
            cout << i << " ";
        }
        cout << endl;
        int maxVal;
        cin >> maxVal;

        return maxVal;
    }

private:
    int n, k;
    vvi s;
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
       int n, k;
       cin >> n >> k;
       Solution solution(n, k);
       solution.readData(cin);
       solution.solve();
    }
#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif
}
