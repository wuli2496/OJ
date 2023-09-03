#include <bits/stdc++.h>

using namespace std;

#define _for(i, a, b) for(int i = (a); i < (b); i++)
#define _rep(i, a, b) for (int i = (a); i <= (b); i++)

struct Edge
{
    int u, v, d;
};

struct HeapNode
{
    int u, d;

    bool operator<(const HeapNode& other) const
    {
        return d > other.d;
    }
};

template <int SZV, int INF>
struct Dijkstra
{
    int n;
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

    void addEdge(int u, int v, int d)
    {
        graph[u].push_back(edges.size());
        edges.push_back({u, v, d});
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
                int v = edge.v;
                if (d[u] + edge.d < d[v]) {
                    d[v] = d[u] + edge.d;
                    p[v] = graph[u][i];
                    pq.push({v, d[v]});
                }
            }
        }
    }

    void getPath(int s, int e, deque<int>& path, bool rev = false)
    {
        int x = e;
        if (rev) {
            path.push_back(x);
        } else {
            path.push_front(x);
        }

        while (x != s) {
            x = edges[p[x]].u;
            if (rev) {
                path.push_back(x);
            } else {
                path.push_front(x);
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

const int MAXN = 500 + 4;
const int INF = 1e9;

int main()
{
    fastio();

    #ifndef ONLINE_JUDGE
        ifstream fin("f:\\OJ\\uva_in.txt");
        streambuf* back = cin.rdbuf(fin.rdbuf());
    #endif

    int N, S, E;
    int kase = 0;
    while (cin >> N >> S >> E) {
        if (kase++) {
            cout << endl;
        }

        Dijkstra<MAXN, INF> sd, ed;
        sd.init(N + 1); ed.init(N + 1);

        int M;
        cin >> M;
        _for(i, 0, M) {
            int X, Y, Z;
            cin >> X >> Y >> Z;
            sd.addEdge(X, Y, Z);
            sd.addEdge(Y, X, Z);
            ed.addEdge(X, Y, Z);
            ed.addEdge(Y, X, Z);
        }

        sd.dijkstra(S);
        ed.dijkstra(E);
        int cu = -1;
        int ans = INF;
        deque<int> path;
        if (sd.d[E] < ans) {
            ans = sd.d[E];
            sd.getPath(S, E, path);
        }

        auto update = [&](int u, int v, int d) {
            if (sd.d[u] < ans && ed.d[v] < ans && sd.d[u] + d + ed.d[v] < ans) {
                ans = sd.d[u] + d + ed.d[v];
                cu = u;
                path.clear();
                sd.getPath(S, u, path);
                ed.getPath(E, v, path, true);
            }
        };

        int K;
        cin >> K;
        _for(i, 0, K) {
            int u, v, d;
            cin >> u >> v >> d;
            update(u, v, d);
            update(v, u, d);
        }

        _for(i, 0, path.size()) {
            if (i) {
                cout << " ";
            }
            cout << path[i];

        }
        cout << endl;
        if (cu == -1) {
            cout << "Ticket Not Used" << endl;
        } else {
            cout << cu << endl;
        }
        cout << ans << endl;
    }

    #ifndef ONLINE_JUDGE
        cin.rdbuf(back);
    #endif

    return 0;
}
