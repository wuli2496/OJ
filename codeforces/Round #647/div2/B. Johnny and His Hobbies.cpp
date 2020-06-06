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
    Solution(int otherN, const vector<int>& num)
        : n(otherN), nums(num)
    {

    }

    int solve()
    {
        for (int i = 1; i <= 1024; ++i) {
            bool flag = check(i);
            if (flag) {
                return i;
            }
        }

        return -1;
    }

private:
    bool check(int num) 
    { 
       vector<bool> f(N);

       for (int i = 0; i < n; ++i) {
           f[nums[i]] = true;
       }

       for (int i = 0; i < n; ++i) {
           if (!f[nums[i] ^ num]) {
               return false;
           }

           f[nums[i] ^ num] = false;
       }

       for (int i = 0; i < n; ++i) {
           if (f[i] == true) {
               return false;
           }
       }

       return true;
    }
private:
    int n;
    vector<int> nums;

    const static int N = 3000;
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
       cin >> n;
       vector<int> v(n);
       for (int i = 0; i < n; ++i) {
           cin >> v[i];
       }

       Solution solution(n, v);
       int ans = solution.solve();
       cout << ans << endl;
    }
#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif
}
