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

using LL = long long;

class Solution
{
public:
    Solution(LL otherA, LL otherB)
        : a(otherA), b(otherB)
    {

    }

    ~Solution() {}

    int solve()
    {
        if (a > b) {
            swap(a, b);
        }    


        int ans = 0;
        while (a < b) {
            ++ans;
            a *= 2;
        }
        
        if (a != b) {
            return -1;
        }

        return (ans + 2) / 3;
    }

private:
    LL a, b;
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
        LL a, b;
        cin >> a >> b;
        Solution solution(a, b);
        int ans = solution.solve();
        cout << ans << endl;
    }
#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif
}
