#include <bits/stdc++.h>

using namespace std;

const int maxn = 100000 + 10;


vector<int> p(maxn);

int findset(int x)
{
    if (p[x] == x) {
        return x;
    } else {    
        return p[x] = findset(p[x]);
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
    
    int x, y;
    while (cin >> x) {
        for (int i = 0; i < maxn; i++) {
            p[i] = i;
        }
        
        int refusals = 0;
        while (x != -1) {
            cin >> y;
            x = findset(x);
            y = findset(y);
            if (x == y) {
                ++refusals;
            } else {
                p[x] = y;
            }
            
            cin >> x;
        }
        
        cout << refusals << endl;
    }
    
    #ifndef ONLINE_JUDGE
        cin.rdbuf(back);
    #endif
    
    return 0;
}