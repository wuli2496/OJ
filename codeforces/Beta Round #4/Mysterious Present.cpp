#include <bits/stdc++.h>

using namespace std;

struct Node 
{
    int id;
    int x, y;

    bool operator<(const Node& other) const {
        if (x != other.x) {
            return x < other.x;
        }

        return y < other.y;
    }
};

class Solution 
{
public:
    Solution(int n, int x, int y) 
    {
        v.resize(n);
        this->x = x;
        this->y = y;
    }

    ~Solution() {}
    
    void set(size_t i, const Node& node)
    {
        v[i] = node;
    }

    void solve() 
    {
        sort(v.begin(), v.end());
        
        f.resize(v.size() + 1);
        p.resize(v.size() + 1);
        fill(f.begin(), f.end(), 0);
        fill(p.begin(), p.end(), -1);

        int ans = 0;
        for (size_t i = 0, len = v.size(); i < len; ++i) {
            if (v[i].x > x && v[i].y > y) {
                f[i] = 1;

                for (size_t j = 0; j < i; ++j) {
                    if (v[i].x > v[j].x && v[i].y > v[j].y && f[j] >= f[i]) {
                        f[i] = f[j] + 1;
                        p[i] = j;
                    }
                }

                if (f[ans] < f[i]) {
                    ans = i;
                }
            }
        }

        cout << f[ans] << endl;
        if (f[ans]) {
            print(ans);
            cout << endl;
        }
    }

private:
    void print(int i)
    {
        if (i == -1) {
            return;
        }

        print(p[i]);
        cout << v[i].id << " ";
    }

private:
    vector<Node> v;
    int x, y;
    vector<int> f;
    vector<int> p;
};

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

#ifndef ONLINE_JUDGE
    ifstream fin("/home/wl/OJ/OJ/codeforces/codeforces_in");
    streambuf* cinback = cin.rdbuf(fin.rdbuf());
#endif
    
    int n, x, y;
    while (cin >> n >> x >> y) {
        Solution solution(n, x, y);
        for (int i = 0; i < n; ++i) {
            Node node;
            node.id = i + 1 ;
            cin >> node.x >> node.y;
            solution.set(i, node);
        }

        solution.solve();
    }

#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif
}
