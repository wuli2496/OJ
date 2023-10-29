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

    void addEdge(int from, int to, int d)
    {
        graph[from].push_back(edges.size());
        edges.push_back((Edge){from, to, d});
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
                Edge& edge = edges[graph[u][i]];
                if (d[u] + edge.dist < d[edge.to]) {
                    d[edge.to] = d[u] + edge.dist;
                    p[edge.to] = graph[u][i];
                    pq.push((HeapNode){edge.to, d[edge.to]});
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

const int maxr = 100;
const int maxc = 100;
const int UP = 0, LEFT = 1, DOWN = 2, RIGHT = 3;
const int inv[] = {2, 3, 0, 1};
const int dr[] = {-1, 0, 1, 0};
const int dc[] = {0, -1, 0, 1};
const int MAXN = maxr * maxc * 8 + 2;
const int INF = 1e6;

int R, C;
int r1, c1, r2, c2;
int grid[maxr][maxc][4];
Dijkstra<MAXN, INF> solver;
int id[maxr][maxc][4][2];
int n;


int ID(int r, int c, int dir, int doubled)
{
    int& x= id[r][c][dir][doubled];
    if (x == 0) {
        x = ++n;
    }

    return x;
}

bool canGo(int r, int c, int dir)
{
    if (r < 0 || r >= R || c < 0 || c >= C) {
        return false;
    }

    return grid[r][c][dir] > 0;
}

int main()
{
    fastio();

    #ifndef ONLINE_JUDGE
        ifstream fin("f:\\OJ\\uva_in.txt");
        streambuf* back = cin.rdbuf(fin.rdbuf());
    #endif

    int kase = 1;
    while (cin >> R >> C >> r1 >> c1 >> r2 >> c2) {
        if (R == 0) {
            break;
        }

        --r1; --c1; --r2; --c2;
        _for(r, 0, R) {
            _for(c, 0, C - 1) {
                int num;
                cin >> num;
                grid[r][c][RIGHT] = grid[r][c + 1][LEFT] = num;
            }

            if (r != R - 1) {
                _for(c, 0, C) {
                    int num;
                    cin >> num;
                    grid[r][c][DOWN] = grid[r + 1][c][UP] = num;
                }
            }
        }

        solver.init(R * C * 8 + 1);
        n = 0;
        memset(id, 0x00, sizeof(id));


        _for(dir, 0, 4) {
            if (canGo(r1, c1, dir)) {
                solver.addEdge(0, ID(r1 + dr[dir], c1 + dc[dir], dir, 1), grid[r1][c1][dir] * 2);
            }
        }

        _for(r, 0, R) {
            _for(c, 0, C) {
                _for(dir, 0, 4) {
                    if (!canGo(r, c, inv[dir])) {
                        continue;
                    }
                     _for(newdir, 0, 4) {
                        if (!canGo(r, c, newdir)) {
                                continue;
                            }

                        _for(doubled, 0, 2) {


                            int newr = r + dr[newdir];
                            int newc = c + dc[newdir];
                            int v = grid[r][c][newdir];
                            int newdoubled = 0;

                            if (dir != newdir) {
                                if (!doubled) {
                                    v += grid[r][c][inv[dir]];
                                }
                                newdoubled = 1;
                                v += grid[r][c][newdir];
                            }

                            solver.addEdge(ID(r, c, dir, doubled), ID(newr, newc, newdir, newdoubled), v);
                        }
                    }
                }
            }
        }

        solver.dijkstra(0);

        int ans = INF;
        _for(dir, 0, 4) {
            if (!canGo(r2, c2, inv[dir])) {
                continue;
            }

            _for(doubled, 0, 2) {
                int v = solver.d[ID(r2, c2, dir, doubled)];
                //cout << "v:" << v << endl;
                if (!doubled) {
                    v += grid[r2][c2][inv[dir]];
                }

                ans = min(ans, v);
            }
        }

        cout << "Case " << kase++ << ": ";
        if (ans == INF) {
            cout << "Impossible" << endl;
        } else {
            cout << ans << endl;
        }
    }


    #ifndef ONLINE_JUDGE
        cin.rdbuf(back);
    #endif

    return 0;
}
