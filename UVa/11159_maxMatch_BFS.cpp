#include <cstdio>
#include <cstring>

using namespace std;

const int MAXN = 100;

int x[MAXN], y[MAXN];
int g[MAXN][MAXN];
int n, m;
int mx[MAXN], my[MAXN];

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

int match()
{
	int pre[MAXN];
	int ans = 0;

	for (int i = 0; i < n; i++) {
		if (mx[i] == -1) {
			for (int j = 0; j < m; j++) pre[j] = -2;
			int q[MAXN];
			int front = 0, rear = 0;
			int cur;

			for (int j = 0; j < m; j++) {
				if (g[i][j]) {
					pre[j] = -1;
					q[rear++] = j;
				}
			}

			while (front < rear) {
				cur = q[front];
				if (my[cur] == -1) break;
				front++;
				for (int j = 0; j < m; j++) {
					if (pre[j] == -2 && g[my[cur]][j]) {
						pre[j] = cur;
						q[rear++] = j;
					}
				}
			}

			if (front >= rear) continue;

			while (pre[cur] > -1) {
				mx[my[pre[cur]]] = cur;
				my[cur] = my[pre[cur]];
				cur = pre[cur];
			}		

			mx[i] = cur; my[cur] = i;	
			ans++;
		}

	}
	return ans;
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
    int ans = match();

    printf("%d\n", ans);
}

int main()
{
#ifndef ONLINE_JUDGE
    freopen("./uva_in.txt", "r", stdin);
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
