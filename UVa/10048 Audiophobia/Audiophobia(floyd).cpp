#include <bits/stdc++.h>

using namespace std;

#define _for(i, a, b) for(int i = (a); i < (b); i++)
#define _rep(i, a, b) for (int i = (a); i <= (b); i++)

const int N = 104;
const int INF = 1e9;

int graph[N][N];

void fastio()
{
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
}

int main()
{
    fastio();

    #ifndef ONLINE_JUDGE
        ifstream fin("f:\\OJ\\uva_in.txt");
        streambuf* back = cin.rdbuf(fin.rdbuf());
    #endif


    int kase = 1;
    int c, s, q;
    while (cin >> c >> s >> q) {
        if (c == 0 && s == 0 && q == 0) {
            break;
        }

        if (kase > 1) {
            cout << endl;
        }

        cout << "Case #" << kase++ << endl;
        _for(i, 0, c) {
            _for (j, 0, c) {
                graph[i][j] = (i == j) ? 0 : INF;
            }
        }

        _for(i, 0, s) {
            int c1, c2, d;
            cin >> c1 >> c2 >> d;

            --c1;
            --c2;
            graph[c1][c2] = min(graph[c1][c2], d);
            graph[c2][c1] = graph[c1][c2];
        }

        _for(k, 0, c) {
            _for(i, 0, c) {
                _for(j, 0, c) {
                    if (graph[i][k] != INF && graph[k][j] != INF) {
                        graph[i][j] = min(graph[i][j], max(graph[i][k], graph[k][j]));
                    }
                }
            }
        }

        _for(i, 0, q) {
            int c1, c2;
            cin >> c1 >> c2;
            --c1;
            --c2;
            int ans = graph[c1][c2];
            if (ans == INF) {
                cout << "no path" << endl;
            } else {
                cout << ans << endl;
            }
        }
    }

    #ifndef ONLINE_JUDGE
        cin.rdbuf(back);
    #endif

    return 0;
}
