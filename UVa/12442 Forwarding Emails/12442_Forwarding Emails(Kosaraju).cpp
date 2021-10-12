#include <iostream>
#include <fstream>
#include <cstring>
#include <vector>
#include <set>

const int N = 50001;
std::set<int> s[N];

class Solution
{
public:

    void init(int n)
    {
        m_n = n;
    }

    void setEdge(int i, int j)
    {
        edges[i].push_back(j);
        reverseEdges[j].push_back(i);
    }

    int solve()
    {
        Kosaraju();

        /*for (int i = 0; i < cnt; i++)
        {
            std::cout << "toposort[" << i << "]:" << toposort[i] << std::endl;
        }*/

        memset(d, 0x00, sizeof(d));
        for (int i = 1; i <= m_n; i++)
        {
            d[scc[i]]++;
        }

        /*for (int i = 1; i <= m_n; i++)
        {
            std::cout << "scc[" << i << "]:" << scc[i] << std::endl;
        }

        for (int i = 1; i<= scc_no; i++)
        {
            std::cout << "d[" << i << "]:" << d[i] << std::endl;
        }*/


        memset(f, 0x00, sizeof(f));
        for (int i = 1; i <= m_n; i++)
        {
            for (size_t j = 0; j < edges[i].size(); j++)
            {
                int v = edges[i][j];
                if (scc[i] != scc[v])
                {
                    s[scc[i]].insert(scc[v]);
                }
            }
        }

        for (int i = 1; i <= scc_no; i++)
        {
            if (!f[i]) dp(i);
        }

        int max = 0, ans = 1;
        for (int i = 1; i <= scc_no; i++)
        {
            if (f[i] > max)
            {
                max = f[i];
            }
        }

        for (int i = 1; i <= m_n; i++)
        {
            if (f[scc[i]] == max)
            {
                ans = i;
                break;
            }
        }

        for (int i = 1; i <= m_n; i++)
        {
            edges[i].clear();
            reverseEdges[i].clear();
        }

        for (int i = 1; i <= scc_no; i++)
        {
            s[i].clear();
        }

        return ans;
    }

private:
    void dfs(int u)
    {
        if(vis[u]) return;
        vis[u] = true;

        for (size_t i = 0; i < edges[u].size(); i++)
        {
            int v = edges[u][i];
            dfs(v);
        }
        toposort[cnt++] = u;
    }

    void dfs2(int u)
    {
        if (scc[u]) return;
        scc[u] = scc_no;
        for (size_t i = 0; i < reverseEdges[u].size(); i++)
        {
            int v = reverseEdges[u][i];
            dfs2(v);

        }
    }

    void Kosaraju()
    {
        memset(vis, false, sizeof(vis));
        memset(scc, 0x00, sizeof(scc));
        memset(toposort, 0x00, sizeof(toposort));
        cnt = 0;

        for (int i = 1; i <= m_n; i++)
        {
            if (!vis[i])
            {
                dfs(i);
            }
        }

        scc_no = 0;
        for (int i = cnt - 1; i >= 0; i--)
        {
            if (!scc[toposort[i]])
            {
                scc_no++;
                dfs2(toposort[i]);
            }
        }
    }

    int dp(int u)
    {
        if (f[u]) return f[u];

        f[u] = d[u];

        for (std::set<int>::iterator it = s[u].begin(); it != s[u].end(); it++)
        {
            f[u] += dp(*it);
        }
        return f[u];
    }


private:

    int m_n;
    std::vector<int> edges[N], reverseEdges[N];
    bool vis[N];
    int toposort[N];
    int cnt;
    int scc[N];
    int scc_no;
    int d[N];
    int f[N];
};


int main() {
#ifndef ONLINE_JUDGE
    std::ifstream fin("f:\\OJ\\uva_in.txt");
    std::streambuf* old = std::cin.rdbuf(fin.rdbuf());
#endif

    int t;
    Solution solver;
    std::cin >> t;
    for (int i = 1; i <= t; i++)
    {
        int n;
        std::cin >> n;
        solver.init(n);
        for (int i = 0; i < n; i++)
        {
            int a, b;
            std::cin >> a >> b;
            solver.setEdge(a, b);
        }

        int ans = solver.solve();

        std::cout << "Case " << i << ": " << ans << std::endl;

    }
#ifndef ONLINE_JUDGE
    std::cin.rdbuf(old);
#endif
    return 0;
}