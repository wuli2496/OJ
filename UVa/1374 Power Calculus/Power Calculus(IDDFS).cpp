#include <iostream>
#include <fstream>
#include <algorithm>

using namespace std;

const int N = 100;

int v[N];
int n;

void fastio()
{
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
}

bool dfs(int depth, int limit) 
{
    if (v[depth] == n) {
        return true;
    }
    
    if (depth == limit) {
        return false;
    }
    
    int maxv = v[0];
    for (int i = 1; i <= depth; ++i) {
        maxv = max(maxv, v[i]);
    }
    
    if (maxv * (1 << (limit - depth)) < n) {
        return false;
    }
    
    for (int i = depth; i >= 0; --i) {
        v[depth + 1] = v[depth] + v[i];
        if (dfs(depth + 1, limit)) {
            return true;
        }
        
        v[depth + 1] = v[depth] - v[i];
        if (dfs(depth + 1, limit)) {
            return true;
        }
    }
    
    return false;
}

int solve(int num) 
{
    if (num == 1) {
        return 0;
    }

    v[0] = 1;
    
    for (int limit = 1; limit < 13; ++limit) {
        if (dfs(0, limit)) {
            return limit;
        }
    }
    
    return 13;
}

int main()
{
    fastio();
    
    #ifndef ONLINE_JUDGE
        ifstream fin("f:\\OJ\\uva_in.txt");
        streambuf* cinback = cin.rdbuf(fin.rdbuf());
    #endif // ONLINE_JUDGE
    
    while (cin >> n && n) {
        cout << solve(n) << endl;
    }
    
    #ifndef ONLINE_JUDGE
        cin.rdbuf(cinback);
    #endif // ONLINE_JUDGE
    
    return 0;
}
