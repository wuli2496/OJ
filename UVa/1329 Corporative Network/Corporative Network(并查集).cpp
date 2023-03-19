 #include <bits/stdc++.h>

using namespace std;

const int maxn = 200000 + 10;

vector<int> p(maxn);
vector<int> d(maxn);

int findset(int x)
{
    if (p[x] == x) {
        return x;
    } else {    
        int root = findset(p[x]);
        d[x] += d[p[x]];
        return p[x] = root;
    }
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
    
    int t;
    cin >> t;
    while (t--) {
        int n;
        cin >> n;
        for (int i = 1; i <= n; i++) {
            p[i] = i;
            d[i] = 0;
        }
        
        string cmd;
        cin >> cmd;
        while (cmd[0] != 'O') {
            if (cmd[0] == 'I') {
                int u, v;
                cin >> u >> v;
                p[u] = v;
                d[u] = abs(u -v) % 1000;
            } else if (cmd[0] == 'E') {
                int u;
                cin >> u;
                findset(u);
                cout << d[u] << endl;
            }
            cin >> cmd;
        }
    }
    
    #ifndef ONLINE_JUDGE
        cin.rdbuf(back);
    #endif
    
    return 0;
}