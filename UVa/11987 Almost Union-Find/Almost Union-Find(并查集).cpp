#include <bits/stdc++.h>

using namespace std;

class UnionFindSet
{
public:
    UnionFindSet(int n)
    {
        p.resize(2 * n + 1);
        size.resize(2 * n + 1);
        sum.resize(2 * n + 1);
        
        
        for (int i = 1; i <= n; i++) {
            p[i] = p[i + n] = i + n;
            size[i] = size[i + n] = 1;
            sum[i] = sum[i + n] = i;
        }
    }
    
    void Union(int a, int b)
    {
        int pa = find_set(a);
        int pb = find_set(b);
        if (pa == pb) {
            return;
        }
        
        p[pa] = pb;
        sum[pb] += sum[pa];
        size[pb] += size[pa];
    }
    
    void move(int a, int b)
    {
        int pa = find_set(a);
        int pb = find_set(b);
        if (pa == pb) {
            return;
        }
        
        p[a] = pb;
        sum[pb] += a;
        size[pb] += 1;
        
        sum[pa] -= a;
        size[pa] -= 1;
    }
    
    int get_size(int p)
    {
        int pa = find_set(p);
        
        return size[pa];
    }
    
    int get_sum(int p)
    {
        int pa = find_set(p);
        
        return sum[pa];
    }
    
    int find_set(int x)
    {
        if (p[x] == x) {
            return x;
        }
       
        return p[x] = find_set(p[x]);
    }
    
private:
    vector<int> p;
    vector<int> size;
    vector<int> sum;
};

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
    
    int n, m;
    while (cin >> n >> m) {
        UnionFindSet solution(n);
        for (int i = 0; i < m; i++) {
            int type, p, q;
            cin >> type;
            if (type == 1) {
                cin >> p >> q;
                solution.Union(p, q);
            } else if (type == 2) {
                cin >> p >> q;
                solution.move(p, q);
            } else {
                cin >> p;
                int size = solution.get_size(p);
                int sum = solution.get_sum(p);
                
                cout << size << " " << sum << endl;
            }
        }
    }
    
#ifndef ONLINE_JUDGE
    cin.rdbuf(back);
#endif

    return 0;
}