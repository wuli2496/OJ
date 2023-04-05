#include <bits/stdc++.h>

using namespace std;

const int N = 20010;

int p[N];
int val[N];
int n;
int vis[N];

struct Info
{
    string c;
    int p, q, v;
    vector<int> vec;
};

void fastio()
{
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
}

void init()
{
    for (int i = 0; i <= n; i++) {
        p[i] = i;
        val[i] = 0;
    }
}

int find_set(int x) 
{
    if (p[x] == x) {
        return x;
    }
    
    int pa = p[x];
    p[x] = find_set(p[x]);
    val[x] ^= val[pa];
    
    return p[x];
}
int main()
{
    fastio();

#ifndef ONLINE_JUDGE
    ifstream fin("f:\\OJ\\uva_in.txt");
    streambuf* back = cin.rdbuf(fin.rdbuf());
#endif
    
    int q;
    int testCase = 1;
    while (cin >> n >> q) {
        if (n == 0 && q == 0) {
            break;
        }
        
        string line;
        getline(cin, line);
        
        vector<Info> infos;
        for (int i = 0; i < q; i++) {
            getline(cin, line);
            stringstream ss(line);
            
            Info info;
            ss >> info.c;
            if (info.c[0] == 'I') {
                vector<int> v;
                int tmp;
                while (ss >> tmp) {
                    v.push_back(tmp);
                }
                
                if (v.size() == 2) {
                    info.p = v[0];
                    info.q = n;
                    info.v = v[1];
                } else {
                    info.p = v[0];
                    info.q = v[1];
                    info.v = v[2];
                }
            } else {
                int k;
                ss >> k;
                for (int i = 0; i < k; i++) {
                    int tmp;
                    ss >> tmp;
                    info.vec.push_back(tmp);
                }
            }
            
            infos.push_back(info);
        }
        
        /*for (size_t i = 0; i < infos.size(); i++) {
            cout << "c:" << infos[i].c << " p:" << infos[i].p << " q:" << infos[i].q << " v:" << infos[i].v << endl;
        }*/
        init();
        cout << "Case " << testCase++ << ":" << endl;
        int facts = 0;
        for (size_t i = 0; i < infos.size(); i++) {
            char ch = infos[i].c[0];
            if (ch == 'I') {
                facts++;
                //cout << "p:" << infos[i].p << " q:" << infos[i].q << endl;
                
                /*for (int i = 0; i <= n; i++) {
                    cout << p[i] << " ";
                }
                cout << endl;*/
                int pa = find_set(infos[i].p);
                int pb = find_set(infos[i].q);
                //cout << "pa:" << pa << " pb:" << pb << endl;
                if (pa == pb) {
                    if ((val[infos[i].p] ^ val[infos[i].q]) != infos[i].v) {
                        cout << "The first " << facts << " facts are conflicting." << endl;
                        break;
                    }
                } else {
                    if (pa == n) swap(pa, pb);
                    p[pa] = pb;
                    val[pa] = val[infos[i].p] ^ val[infos[i].q] ^ infos[i].v;
                }
            } else {
                memset(vis, 0, sizeof(vis));
                int ans = 0;
                for (size_t j = 0; j < infos[i].vec.size(); j++) {
                    int pa = find_set(infos[i].vec[j]);
                    ans ^= val[infos[i].vec[j]];
                    if (pa != n) {
                        vis[pa] ^= 1;
                    }
                }
                
                bool flag = true;
                for (int i = 0; i < n; i++) {
                    if (vis[i]) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    cout << ans << endl;
                } else {
                    cout << "I don't know." << endl;
                }
            }
        }
        cout << endl;
    }
    

#ifndef ONLINE_JUDGE
    cin.rdbuf(back);
#endif

    return 0;
}