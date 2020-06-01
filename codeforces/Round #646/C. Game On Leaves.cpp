#include <bits/stdc++.h>

using namespace std;

#define forn(i, s, e) for(int i = (s); i < (e); ++i)
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
    Solution(int n, int x)
    {
        this->n = n;
        this->x = x;

        degree.resize(n + 1);
    }

    ~Solution() {}

    void addDegree(int u, int v)
    {
        ++degree[u];
        ++degree[v];

        return;
    }

    string solve()
    {
        if (degree[x] == 0 || degree[x] == 1) {
            return "Ayush";
        }    

        if (n % 2 == 1) {
            return "Ashish";
        }

        return "Ayush";
    }

private:
    vector<int> degree;
    int n, x;
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
        int n, x;
        cin >> n >> x;
        Solution solution(n, x);
        forn(i, 0, n - 1) {
            int u, v;
            cin >> u >> v;
            solution.addDegree(u, v);
        }

        cout << solution.solve() << endl;
    }
#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif
}
