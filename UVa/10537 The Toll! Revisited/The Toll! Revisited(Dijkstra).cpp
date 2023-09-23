#include <bits/stdc++.h>

using namespace std;

static auto __ = []()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    return 0;
}();

#define _for(i, a, b) for(int i = (a); i < (b); i++)
#define _rep(i, a, b) for (int i = (a); i <= (b); i++)

typedef long long LL;
const int N = 52 + 10;
const LL INF = numeric_limits<LL>::max();

int graph[N][N];
int n;
int p, start, delivery;
bool vis[N];
LL d[N];
int parent[N];

int readNode()
{
    string node;
    cin >> node;

    if (isupper(node[0])) {
        return node[0] - 'A';
    }

    return node[0] - 'a' + 26;
}

char nodeStr(int u)
{
    if (u < 26) {
        return static_cast<char>('A' + u);
    }

    return static_cast<char>('a' + (u - 26));
}

LL forward(LL l, int u)
{
    if (u >= 26) {
        return l - 1;
    }

    return l - (l + 19) / 20;
}

LL back(int u)
{
    if (u >= 26) {
        return d[u] + 1;
    }

    LL l = d[u] * 20 / 19;
    while (forward(l, u) < d[u]) {
        ++l;
    }

    return l;
}

void solve()
{
    fill_n(vis, N, false);
    fill_n(d, N, INF);
    d[delivery] = p;
    vis[delivery] = true;
    LL l = back(delivery);
    n = 52;
    _for(i, 0, n) {
        if (graph[i][delivery]) {
            d[i] = l;
            parent[i] = delivery;
        }
    }

    while (!vis[start]) {
        int minu = -1;
        _for(i, 0, n) {
            if (!vis[i] && (minu == -1 || d[i] < d[minu])) {
                minu = i;
            }
        }
        vis[minu] = true;
        LL l = back(minu);
        _for(i, 0, n) {
            if (!vis[i] && graph[i][minu]) {
                if (l < d[i]) {
                    d[i] = l;
                    parent[i] = minu;
                }
            }
        }
    }

    cout << d[start] << endl;
    cout << nodeStr(start);
    for (int u = start; u != delivery; u = parent[u]) {
        cout << "-" << nodeStr(parent[u]);
    }
    cout << endl;
}

int main()
{
#ifndef ONLINE_JUDGE
    ifstream fin("f:\\OJ\\uva_in.txt");
    streambuf* back = cin.rdbuf(fin.rdbuf());
#endif

    int kase = 1;
    while (cin >> n) {
        if (n == -1) {
            break;
        }

        memset(graph, 0, sizeof(graph));
        _for(i, 0, n) {
            int u = readNode();
            int v = readNode();

            graph[u][v] = graph[v][u] = 1;
        }

        cin >> p;
        start = readNode();
        delivery = readNode();
        cout << "Case " << kase++ << ":" << endl;
        solve();
    }

#ifndef ONLINE_JUDGE
    cin.rdbuf(back);
#endif

    return 0;
}
