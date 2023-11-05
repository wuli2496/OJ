#include <bits/stdc++.h>

using namespace std;

#define _for(i, a, b) for(int i = (a); i < (b); i++)
#define _rep(i, a, b) for (int i = (a); i <= (b); i++)

struct Edge
{
    int from, to, dist, open, close;
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
    int n;
    bool done[SZV];
    vector<Edge> edges;
    vector<int> graph[SZV];
    int d[SZV];
    int p[SZV];

    void init(int n)
    {
        this-> n = n;
        _for(i, 0, n) {
            graph[i].clear();
        }
        edges.clear();
    }

    void addEdge(int from, int to, int w, int open, int close)
    {
        graph[from].push_back(static_cast<int>(edges.size()));
        edges.push_back((Edge){from, to, w, open, close});
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
            _for(i, 0, static_cast<int>(graph[u].size())) {
                Edge& edge = edges[graph[u][i]];
                int newDist = arrive(d[u], edge);
                if (newDist < d[edge.to]) {
                    d[edge.to] = newDist;
                    pq.push((HeapNode){edge.to, newDist});
                }
            }


        }
    }

    int arrive(int d, const Edge& edge)
    {
        int k = d % (edge.open + edge.close);
        if (k + edge.dist <= edge.open) {
            return d + edge.dist;
        }

        return d - k + edge.open + edge.close + edge.dist;
    }
};



void fastio()
{
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
}

const int MAXN = 305;
const int INF = 1e6;

static Dijkstra<MAXN, INT_MAX> solver;

int main()
{
    fastio();

    #ifndef ONLINE_JUDGE
        ifstream fin("f:\\OJ\\uva_in.txt");
        streambuf* back = cin.rdbuf(fin.rdbuf());
    #endif

    int kase = 1;
    int n, m, s, t;
    while (cin >> n >> m >> s >> t) {
        solver.init(n + 1);

        _for(i, 0, m) {
            int u, v, a, b, w;
            cin >> u >> v >> a >> b >> w;
            if (w > a) {
                continue;
            }

            solver.addEdge(u, v, w, a, b);
        }


        solver.dijkstra(s);
        cout << "Case " << kase++ << ": " << solver.d[t] << endl;
    }


    #ifndef ONLINE_JUDGE
        cin.rdbuf(back);
    #endif

    return 0;
}
