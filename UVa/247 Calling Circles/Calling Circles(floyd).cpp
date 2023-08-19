#include <bits/stdc++.h>

using namespace std;

const int N = 30;

#define _for(i, a, b) for(int i = (a); i < (b); i++)
#define _rep(i, a, b) for (int i = (a); i <= (b); i++)

int n, m;
int graph[N][N];
bool vis[N];
map<string, int> nameMap;
vector<string> names;

int getId(const string& name)
{
    if (!nameMap.count(name))
    {
        int size = names.size();
        nameMap[name] = size;
        names.push_back(name);
    }


    return nameMap[name];
}

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
    while (cin >> n >> m) {
        if (n == 0 && m == 0) {
            break;
        }

        if (kase > 1) {
            cout << endl;
        }

        nameMap.clear();
        names.clear();
        memset(graph, 0, sizeof(graph));
        fill(vis, vis + N, false);
        _for(i, 0, m) {
            string a, b;
            cin >> a >> b;
            int u = getId(a);
            int v = getId(b);
            graph[u][v] = 1;
        }

        _for(k, 0, n) {
            _for(i, 0, n) {
                _for(j, 0, n) {
                    graph[i][j] = graph[i][j] || (graph[i][k] && graph[k][j]);
                }
            }
        }

        cout << "Calling circles for data set " << kase << ":" << endl;
        _for(u, 0, n) {
            if (vis[u]) {
                continue;
            }

            vis[u] = true;
            cout << names[u];

            _for(v, 0, n) {
                if (!vis[v] && graph[u][v] && graph[v][u]) {
                    vis[v] = true;
                    cout << ", " << names[v];
                }
            }

            cout << endl;
        }

        kase++;
    }


    #ifndef ONLINE_JUDGE
        cin.rdbuf(back);
    #endif

    return 0;
}
