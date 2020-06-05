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

struct Node
{
    int a, b, c;
};

class Solution
{
public:
    Solution(int n)
    {
        this->n = n;
    }

    void addEdge(int u, int v)
    {
        adjList[u - 1].push_back(v - 1);
        adjList[v - 1].push_back(u - 1);
    }

    void addNode(const Node& node)
    {
        nodes.push_back(node);
    }

    LL solve()
    {
        int b1 = 0, c1 = 0;
        forn(i, n) {
            if (nodes[i].b == 1) {
                ++b1;
            }

            if (nodes[i].c == 1) {
                ++c1;
            }
        }

        if (b1 != c1) {
            return -1;
        }

        auto ans = dfs(0, -1, numeric_limits<int>::max());
        return ans.first;
    }

    pair<LL, pair<int, int>>  dfs(int u, int p, int mm)
    {
        int x = min(mm, nodes[u].a);
        
        pair<LL, pair<int, int>> ans;
        if (nodes[u].b == 1 && nodes[u].c == 0) {
            ++ans.second.first;
        } else if (nodes[u].b == 0 && nodes[u].c == 1) {
            ++ans.second.second;
        }

        for (int v : adjList[u]) {
            if (v == p) {
                continue;
            }
            
            pair<LL, pair<int, int>> son = dfs(v, u, x);
            ans.first += son.first;
            ans.second.first += son.second.first;
            ans.second.second += son.second.second;
        }

        int m = min(ans.second.first, ans.second.second);
        ans.first += (LL)x * m * 2;
        ans.second.first -= m;
        ans.second.second -= m;

        return ans;
    }

private:
    vector<Node> nodes;
    int n;

    const static int N = 200001;
    vector<int> adjList[N];
};

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

#ifndef ONLINE_JUDGE
    ifstream fin("/home/wl/OJ/OJ/codeforces/codeforces_in");
    streambuf* cinback = cin.rdbuf(fin.rdbuf());
#endif
    
    int n;
    cin >> n;
    Solution solution(n);
    forn(i, n) {
        Node node;
        cin >> node.a >> node.b >> node.c;
        solution.addNode(node);
    }

    forn(i, n - 1) {
        int u, v;
        cin >> u >> v;
        solution.addEdge(u, v);
    }

    LL ans = solution.solve();
    cout << ans << endl;

#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif
}
