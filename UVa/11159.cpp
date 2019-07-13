#include <cstdio>
#include <cstring>

using namespace std;

const int MAXN = 100;

int x[MAXN], y[MAXN];
int g[MAXN][MAXN];
int n, m;
int mx[MAXN], my[MAXN];
bool vis[MAXN];

void input()
{
    scanf("%d", &n);
    for (int i = 0; i < n; i++) {
        scanf("%d", &x[i]);
    }    

    scanf("%d", &m);
    for (int i = 0; i < m; i++) {
        scanf("%d", &y[i]);
    }
}

bool match(int u)
{
    for (int i = 0; i < m; i++) {
        if (!vis[i] && g[u][i]) {
            vis[i] = true;
            if (my[i] == -1 || match(my[i])) {
                my[i] = u;
                mx[u] = i;
                return true;
            }
        }
    }    

    return false;
}

void solve()
{
    memset(g, 0x00, sizeof(g));

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            if ((x[i] != 0 && y[j] % x[i] == 0) || (x[i] == 0 && y[j] == 0)) g[i][j] = 1;        
        }
    }

    memset(mx, 0xff, sizeof(mx));
    memset(my, 0xff, sizeof(my));
    int ans = 0;
    for (int i = 0; i < n; i++) {
        if (mx[i] == -1) {
            memset(vis, false, sizeof(vis));
            if (match(i)) ans++;
        }
    }

    printf("%d\n", ans);
}

int main()
{
#ifndef ONLINE_JUDGE
    freopen("/cygdrive/d/OJ/uva_in.txt", "r", stdin);
#endif

    int cas;
    scanf("%d", &cas);
    for (int i = 1; i <= cas; i++) {
        printf("Case %d: ", i);
        input();
        solve();
    }
    return 0;
}
