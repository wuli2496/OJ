#include <bits/stdc++.h>

using namespace std;

#define _for(i, a, b) for(int i = (a); i < (b); i++)
#define _rep(i, a, b) for (int i = (a); i <= (b); i++)

struct Edge
{
    int from, to, dist;
};

struct HeapNode
{
    int u, d;

    bool operator<(const HeapNode& other) const
    {
        return d > other.d;
    }
};

template <size_t SZV, int INF>
struct Dijkstra
{
    int n, m;
    vector<Edge> edges;
    vector<int> graph[SZV];
    bool done[SZV];
    int d[SZV], p[SZV];

    void init(int n)
    {
        this->n = n;
        edges.clear();
        _for(i, 0, n) {
            graph[i].clear();
        }
    }

    void addEdge(int from, int to, int dist)
    {
        graph[from].push_back(edges.size());
        edges.push_back({from, to, dist});
        m = edges.size();
    }

    void dijkstra(int s)
    {
        priority_queue<HeapNode> pq;
        fill_n(done, n, false);
        fill_n(d, n, INF);
        d[s] = 0;
        pq.push({s, 0});

        while (!pq.empty()) {
            HeapNode curNode = pq.top();
            pq.pop();

            int u = curNode.u;
            if (done[u]) {
                continue;
            }

            done[u] = true;
            _for(i, 0, graph[u].size()) {
                const auto& edge = edges[graph[u][i]];
                int to = edge.to;
                if (edge.dist > 0 && d[u] + edge.dist < d[to]) {
                    d[to] = d[u] + edge.dist;
                    p[to] = graph[u][i];
                    pq.push({to, d[to]});
                }
            }
        }
    }
};


void fastio()
{
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
}

const int MAXN = 100 + 4;
const int INF = 1e9;

Dijkstra<MAXN, INF> solver;
vector<int> grid[MAXN][MAXN];
int n, m, L;
int edgeIndex[MAXN][MAXN];
int used[MAXN][MAXN][MAXN];
int sumSingle[MAXN];

int computeC();
int computeC2(int from, int to);

int main()
{
    fastio();

    #ifndef ONLINE_JUDGE
        ifstream fin("f:\\OJ\\uva_in.txt");
        streambuf* back = cin.rdbuf(fin.rdbuf());
    #endif


    while (cin >> n >> m >> L) {
        solver.init(n);

        _for(i, 0, n) {
            _for(j, 0, n) {
                grid[i][j].clear();
            }
        }

        _for(i, 0, m) {
            int a, b, s;
            cin >> a >> b >> s;
            --a;
            --b;
            grid[a][b].push_back(s);
            grid[b][a].push_back(s);
        }

        _for(i, 0, n) {
            _for(j, i + 1, n) {
                if (!grid[i][j].empty()) {
                    sort(grid[i][j].begin(), grid[i][j].end());
                    solver.addEdge(i, j, grid[i][j][0]);
                    edgeIndex[i][j] = solver.m - 1;
                    solver.addEdge(j, i, grid[i][j][0]);
                    edgeIndex[j][i] = solver.m - 1;
                }
            }
        }

        int c = computeC();
        int c2 = -1;
        _for(i, 0, n) {
            _for(j, i + 1, n) {
                if (!grid[i][j].empty()) {
                    int& d1 = solver.edges[edgeIndex[i][j]].dist;
                    int& d2 = solver.edges[edgeIndex[j][i]].dist;
                    if (grid[i][j].size() == 1) {
                        d1 = d2 = -1;
                    } else {
                        d1 = d2 = grid[i][j][1];
                    }

                    c2 = max(c2, computeC2(i, j));

                    d1 = d2 = grid[i][j][0];
                }
            }
        }

        cout << c << " " << c2 << endl;
    }

    #ifndef ONLINE_JUDGE
        cin.rdbuf(back);
    #endif

    return 0;
}

int computeC()
{
    int ans = 0;
    memset(used, 0, sizeof(used));

    _for(src, 0, n) {
        solver.dijkstra(src);
        sumSingle[src] = 0;

        _for(i, 0, n) {
            if (i != src) {
                int parent = solver.edges[solver.p[i]].from;
                used[src][parent][i] = 1;
                used[src][i][parent] = 1;
            }

            sumSingle[src] += (solver.d[i] != INF ? solver.d[i] : L);
        }

        ans += sumSingle[src];
    }

    return ans;
}

int computeC2(int from, int to)
{
    int ans = 0;
    _for(src, 0, n) {
        if (!used[src][from][to]) {
            ans += sumSingle[src];
        } else {
            solver.dijkstra(src);
            _for(i, 0, n) {
                ans += (solver.d[i] != INF ? solver.d[i] : L);
            }
        }
    }

    return ans;
}
