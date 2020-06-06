#include <bits/stdc++.h>

using namespace std;

#define forn(i, n) for (int i = 0; i < (int)(n); ++i)
#define for1(i, n) for (int i = 1; i <= (int)(n); ++i)
#define ford(i, n) for (int i = (int)(n) - 1; i >=0; --i)
#define fore(i, s, e) for (int i = (s); i < (e); ++i)
#define sz(x) (int)(x).size()
#define all(x) (x).begin(), (x).end()
#define rall(x) (x).rbegin(), (x).rend()

using LL = long long;
using vi = vector<int>;

template <typename T> 
istream& operator>>(istream& in, vector<T>& v) 
{
    for (auto& i : v) {
        in >> i;
    }

    return in;
}

template <typename T>
ostream& operator<<(ostream& out, vector<T>& v)
{
    for (auto& i : v) {
        out << i << " ";
    }
    out << endl;

    return out;
}

template <typename T, typename U>
void chkMin(T& a, U b)
{
    if (a > b) {
        a = b;
    }    
}

template <typename T, typename U>
void chkMax(T& a, U b)
{
    if (a < b) {
        a = b;
    }
}

template <typename T> 
inline void read(T& a)
{
    char c = getchar();
    int f = 1;
    a = 0;
    while (c > '9' || c < '0') {
        if (c == '-') {
            f = -1;
        }

        c = getchar();
    }

    while (c <= '9' && c >= '0') {
        a = (a << 1) + (a << 3) + c - '0';
        c = getchar();
    }

    a *= f;
}

template <typename T>
void write(T a)
{
    if (a < 0) {
        putchar('-');
        a = -a;
    }

    if (a > 9) {
        write(a / 10);
    }

    putchar(a % 10 + '0');
}

const int N = 500005;
const int M = 1000005;

struct Graph
{
    int first[N];
    int next[M];
    int to[M];
    int tot;

    void add(int u, int v)
    {
        to[++tot] = v;
        next[tot] = first[u];
        first[u] = tot;
    }
}graph;

vi topic[N];
vi target;

class Solution
{
public:
    Solution(int n) 
    {
        this->n = n;
        target.resize(n + 1);
    }

    void add(int topicNum, int blog)
    {
        topic[topicNum].emplace_back(blog);
        target[blog] = topicNum;
    }

    void solve()
    {
        vi tag(n + 1);
        vi ans;
        for1(i, n) {
            forn(j, sz(topic[i])) {
                int u = topic[i][j];
                for (int k = graph.first[u]; k != 0; k = graph.next[k]) {
                   int v = graph.to[k];
                   tag[target[v]] = u;
                }
                
                int k;
                for(k = 1; tag[k] == u; ++k);
                if (k != i) {
                    cout << "-1" << endl;
                    return;
                }

                ans.emplace_back(u);
            }
        }   

        cout << ans;
    }

private:
    int n;
};


int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

#ifndef ONLINE_JUDGE
    ifstream fin("/home/wl/OJ/OJ/codeforces/codeforces_in");
    streambuf* cinback = cin.rdbuf(fin.rdbuf());
#endif
    int n, m;
    cin >> n >> m;
    forn(i, m) {
        int a, b;
        cin >> a >> b;
        graph.add(a, b); graph.add(b, a);
    }

    Solution solution(n);
    for1(i, n) {
        int t;
        cin >> t;
        solution.add(t, i);
    }
    solution.solve();

#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif
}
