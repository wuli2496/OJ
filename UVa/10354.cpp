#include <cstdio>

using namespace std;

const int INF = 0x1fffffff;
const int MAXN = 128;

int adj[MAXN][MAXN];
bool ban[MAXN], vis[MAXN];
int p, r, bh, of, yh, m;
int d[MAXN], e[MAXN];

bool input()
{
    if (scanf("%d%d%d%d%d%d", &p, &r, &bh, &of, &yh, &m) != 6) return false;

    for (int i = 1; i <= p; i++) {
        for (int j = 1; j <= p; j++) adj[i][j] = INF;
    }

    for (int i = 0; i < r; i++) {
        int a, b, c;
        scanf("%d%d%d", &a, &b, &c);
        if (adj[a][b] > c) adj[a][b] = adj[b][a] = c;
    }

    return true;
}

void dijkstra(int a[], int s)
{
    for (int i = 1; i <= p; i++) {
        vis[i] = ban[i];
        a[i] = INF;
    }

    a[s] = (ban[s] ? INF : 0);

    int i, j;
    for (;;) {
        for (i = 1; i <= p && vis[i]; i++);
        for (j = i + 1; j <= p; j++) {
            if (!vis[j] && a[j] < a[i]) i = j;
        }

        if (i > p || a[i] >= INF) break;
        vis[i] = true;
        for (j = 1; j <= p; j++) {
            if (a[i] + adj[i][j] < a[j]) a[j] = a[i] + adj[i][j];
        }
    }

    for (i = 1; i <= p; i++) {
        if (ban[i]) a[i] = INF;
    }
}

void solve()
{
    for (int i = 1; i <= p; i++) ban[i] = false;
    dijkstra(d, bh);
    dijkstra(e, of);

    for (int i = 1; i <= p; i++) {
        for (int j = 1; j <= p; j++) {
            if (adj[i][j] != INF && d[i] + adj[i][j] + e[j] == d[of]) {
                adj[i][j] = adj[j][i] = INF;
            }
        }
    }

    for (int i = 1; i <= p; i++) {
        ban[i] = (d[i] + e[i] == d[of]);
    }

    dijkstra(d, yh);
    if (d[m] >= INF) printf("MISSION IMPOSSIBLE.\n");
    else printf("%d\n", d[m]);

}
int main()
{
    #ifndef ONLINE_JUDGE
        freopen("d:\\OJ\\uva_in.txt", "r", stdin);
    #endif // ONLINE_JUDGE

    while (input()) {
        solve();
    }
    return 0;
}
