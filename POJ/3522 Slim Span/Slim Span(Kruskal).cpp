//#include <bits/stdc++.h>
#include <vector>
#include <iostream>
#include <fstream>
#include <algorithm>

using namespace std;

#define _for(i, a, b) for(int i = (a); i < (b); i++)
#define _rep(i, a, b) for (int i = (a); i <= (b); i++)

struct Edge
{
    int u, v, w;
    Edge(int u, int v, int w):u(u), v(v), w(w) {}

    bool operator <(const Edge& other) const
    {
        return w < other.w;
    }
};

const int MAXN = 100 + 5;
const int INF = 1e9;
int pa[MAXN];
int n;
vector<Edge> edges;

int find_pa(int u)
{
    if (pa[u] == u) {
        return u;
    }

    pa[u] = find_pa(pa[u]);

    return pa[u];
}

void fastio()
{
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
}

int solve()
{
    int m = edges.size();
    int ans = INF;

    sort(edges.begin(), edges.end());

    _for(i, 0, m) {
        _rep(j, 1, n) {
            pa[j] = j;
        }

        int cnt = n;
        _for(j, i, m) {
            int u = find_pa(edges[j].u);
            int v = find_pa(edges[j].v);
            if (u == v) {
                continue;
            }

            pa[u] = v;
            if (--cnt == 1) {
                ans = min(ans, edges[j].w - edges[i].w);
                break;
            }
        }
    }

    if (ans == INF) ans = -1;

    return ans;
}

int main()
{
    fastio();

    #ifndef ONLINE_JUDGE
        ifstream fin("f:\\OJ\\uva_in.txt");
        streambuf* back = cin.rdbuf(fin.rdbuf());
    #endif

    int m;
    while (cin >> n >> m) {
        if (n == 0 && m == 0) {
            break;
        }

        edges.clear();
        _for(i, 0, m) {
            int u, v, w;
            cin >> u >> v >> w;
            edges.push_back(Edge(u, v, w));
        }

        int ans = solve();
        cout << ans << endl;
    }

    #ifndef ONLINE_JUDGE
        cin.rdbuf(back);
    #endif

    return 0;
}
