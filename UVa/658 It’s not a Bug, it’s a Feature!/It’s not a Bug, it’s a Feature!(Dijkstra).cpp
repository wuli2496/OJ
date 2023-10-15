#include <bits/stdc++.h>

using namespace std;

#define _for(i, a, b) for(int i = (a); i < (b); i++)
#define _rep(i, a, b) for (int i = (a); i <= (b); i++)

struct Edge
{
    int from, to, dist;
};

struct HeapNode
{
    int u, d;

    bool operator<(const HeapNode& other) const
    {
        return d > other.d;
    }
};

struct Patch
{
    int present, absent, introduce, remove, time;

    bool canApply(int u) const
    {
        return (u & present) == present && (absent & u) == 0;
    }

    int apply(int u) const
    {
        return (u | introduce) & (~remove);
    }
};

template <size_t SZV, int INF>
struct Dijkstra
{
    int n;
    vector<Patch> patches;
    bool done[SZV];
    int d[SZV];

    void init(int n)
    {
        this-> n = (1 << n);
        patches.clear();
    }

    void dijkstra(int s)
    {
        priority_queue<HeapNode> pq;
        fill_n(done, n, false);
        fill_n(d, n, INF);
        d[s] = 0;
        pq.push({s, 0});

        while (!pq.empty()) {
            HeapNode curNode = pq.top();
            pq.pop();

            int u = curNode.u;
            if (done[u]) {
                continue;
            }

            done[u] = true;
            _for(i, 0, patches.size()) {
                const Patch& p = patches[i];
                if (!p.canApply(u)) {
                    continue;
                }

                int v = p.apply(u);
                if (d[v] > d[u] + p.time) {
                    d[v] = d[u] + p.time;
                    pq.push({v, d[v]});
                }
            }

        }
    }
};



void fastio()
{
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
}

const int MAXN = 20;
const int MAXV = (1 << MAXN) + 4;
const int INF = 1e9;


int n, m;

void toInt(const string& s, int& i1, int& i2)
{
    i1 = i2 = 0;
    _for(i, 0, n) {
        if (s[i] == '+') {
            i1 |= (1 << i);
        }

        if (s[i] == '-') {
            i2 |= (1 << i);
        }
    }
}

Dijkstra<MAXV, INF> solver;

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

        //cout << "n:" << n << " m:" << m << endl;

        solver.init(n);

        string buf1, buf2;
        Patch patch;
        _for(i, 0, m) {
            cin >> patch.time >> buf1 >> buf2;
            toInt(buf1, patch.present, patch.absent);
            toInt(buf2, patch.introduce, patch.remove);
            solver.patches.push_back(patch);
        }

        /*
        for (int i = 0; i < solver.patches.size(); i++) {
            const Patch& patch = solver.patches[i];
            cout << patch.present << " " << patch.absent << " " << patch.introduce << " " << patch.remove << endl;
        }
        */

        solver.dijkstra(solver.n - 1);

        cout << "Product " << kase++ << endl;
        if (solver.d[0] == INF) {
            cout << "Bugs cannot be fixed." << endl;
        } else {
            cout << "Fastest sequence takes " << solver.d[0] << " seconds." << endl;
        }

        cout << endl;

    }

    #ifndef ONLINE_JUDGE
        cin.rdbuf(back);
    #endif

    return 0;
}
