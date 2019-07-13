#include <cstdio>
#include <algorithm>
#include <cstdlib>

using namespace std;

const int MAXN = 30;

struct Node
{
    int a, b, c;
};

Node node[MAXN];
int n, m;
int teenage[MAXN];
int pos[MAXN];
int ans;

bool input()
{
    scanf("%d%d", &n, &m);
    if (n == 0 && m == 0) return false;

    for (int i = 0; i < m; i++) {
        scanf("%d%d%d", &node[i].a, &node[i].b, &node[i].c);
    }
    return true;
}

void dfs(int cur)
{
    if (cur >= n) {
        bool flag = true;
        for (int i = 0; i < m && flag; i++) {
            if (node[i].c > 0)
                if (abs(pos[node[i].a] - pos[node[i].b]) > node[i].c) flag = false;

            if (node[i].c < 0)
                if (abs(pos[node[i].a] - pos[node[i].b]) < -node[i].c) flag = false;
        }

        if (flag) ans++;
        return;
    }

    for (int i = cur; i < n; i++) {
        swap(teenage[cur], teenage[i]);
        pos[teenage[cur]] = cur;
        pos[teenage[i]] = i;

        dfs(cur + 1);

        swap(teenage[cur], teenage[i]);
        pos[teenage[cur]] = cur;
        pos[teenage[i]] = i;
    }
}

void solve()
{
    for (int i = 0; i < n; i++) {
        teenage[i] = i;
        pos[i] = i;
    }

    ans = 0;
    dfs(0);

    printf("%d\n", ans);
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
