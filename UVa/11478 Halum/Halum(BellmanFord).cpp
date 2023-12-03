#include <bits/stdc++.h>

using namespace std;

#define _for(i, a, b) for(int i = (a); i < (b); i++)
#define _rep(i, a, b) for (int i = (a); i <= (b); i++)

const int INF = 1e9, NN = 500 + 10;
struct Edge
{
    int from, to;
    double dist;
};

struct BellmanFord
{
    int n, m;
    vector<Edge> edges;
    vector<int> graph[NN];
    bool inq[NN];
    double d[NN];
    int p[NN];
    int cnt[NN];

    void init(int n)
    {
        this->n = n;
        _for(i, 0, n) {
            graph[i].clear();
        }
        edges.clear();
    }

    void  addEdge(int from, int to, double dist)
    {
        edges.push_back((Edge){from, to, dist});
        m = static_cast<int>(edges.size());
        graph[from].push_back(m - 1);
    }

    bool negativeCycle()
    {
        queue<int> q;
        _for(i, 0, n) {
            d[i] = 0;
            inq[i] = true;
            q.push(i);
            cnt[i] = 0;
        }

        while (!q.empty()) {
            int u = q.front(); q.pop();
            inq[u] = false;
            _for(i, 0, graph[u].size()) {
                Edge& e = edges[graph[u][i]];
                if (d[e.to] > d[u] + e.dist) {
                    d[e.to] = d[u] + e.dist;
                    p[e.to] = graph[u][i];

                    if (!inq[e.to]) {
                        q.push(e.to);
                        inq[e.to] = true;
                        if (++cnt[e.to] >= n) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
};

void fastio()
{
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
}

static BellmanFord solver;

bool test(double x)
{
    _for(i, 0, solver.m) {
        solver.edges[i].dist -= x;
    }

    bool ret = solver.negativeCycle();
    _for(i, 0, solver.m) {
        solver.edges[i].dist += x;
    }

    return !ret;
}

int main()
{
    fastio();

    #ifndef ONLINE_JUDGE
        ifstream fin("f:\\OJ\\uva_in.txt");
        streambuf* back = cin.rdbuf(fin.rdbuf());
    #endif

    int n, m;
    while (cin >> n >> m) {
        solver.init(n);
        int ub = 0;
        _for(i, 0, m) {
            int u, v, d;
            cin >> u >> v >> d;
            ub = max(ub, d);
            solver.addEdge(u - 1, v - 1, d);
        }

        if (test(ub + 1)) {
            cout << "Infinite" << endl;
        } else if (!test(1)) {
            cout << "No Solution" << endl;
        } else {
            int L = 2, R = ub, ans = 1;
            while (L <= R) {
                int M = L + (R - L) / 2;
                if (test(M)) {
                    ans = M;
                    L = M + 1;
                } else {
                    R = M - 1;
                }
            }
            cout << ans << endl;
        }
    }

    #ifndef ONLINE_JUDGE
        cin.rdbuf(back);
    #endif

    return 0;
}
