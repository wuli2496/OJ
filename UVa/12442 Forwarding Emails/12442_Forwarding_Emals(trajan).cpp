//
// Created by lywu on 2017/7/26.
//

#include <iostream>
#include <fstream>
#include <stack>
#include <vector>
#include <algorithm>
#include <cstring>
#include <set>
#include <map>

const int N = 50001;
std::set<int> pset[N];

class Solution
{
public:
    void init(int n)
    {
        this->n = n;
        for (int i = 1; i <= n; i++)
        {
            g[i].clear();
        }
    }

    void setEdge(int a, int b)
    {
        g[a].push_back(b);
    }

    int solve()
    {
        find_scc();

        /*for (int i = 1; i <= n; i++)
        {
            std::cout << "scc[" << i << "]:" << scc[i] << std::endl;
        }*/

        memset(d, 0x00, sizeof(d));

        for (int i = 1; i <= n; i++)
        {
            int tmp = scc[i];
            d[tmp]++;
        }

        /*for (std::map<int, int>::iterator it = d.begin(); it != d.end(); it++)
        {
            int k = it->first, v = it->second;
            std::cout << "d[" << k << "]:" << v << std::endl;
        }*/

        for (int u = 1; u <= n; u++)
        {
            pset[scc[u]].clear();
            for (size_t i = 0; i < g[u].size(); i++)
            {
                int v = g[u][i];
                if (scc[u] != scc[v])
                {
                    pset[scc[u]].insert(scc[v]);
                }
            }
        }

        memset(dp, 0x00, sizeof(dp));
        //std::cout << "scc_no:" << scc_no << std::endl;
        for (int i = 1; i<= scc_no; i++)
        {
            if (dp[i]) continue;
            DP(i);
        }

        /*for (std::map<int, int>::iterator it = dp.begin(); it != dp.end(); it++)
        {
            int k = it->first, v = it->second;
            std::cout << "dp[" << k << "]:" << v << std::endl;
        }*/

        int max = 0;
        for (int i = 1; i <= scc_no; i++)
        {
            if (dp[i] > max)
            {
                max = dp[i];
            }
        }

        //std::cout << "max:" << max << std::endl;

        int ans = 0;
        for (int i = 1; i <= n; i++)
        {
            int sccno = scc[i];
            if (dp[sccno] == max)
            {
                ans = i;
                break;
            }
        }
        //std::cout << "ans:" << ans << std::endl;
        return ans;
    }

private:
    void dfs(int u)
    {
        pre[u] = lowgrantparent[u] = ++dfs_clock;
        s.push(u);

        for (size_t i = 0; i < g[u].size(); i++)
        {
            int v = g[u][i];
            if (!pre[v])
            {
                dfs(v);
                lowgrantparent[u] = std::min(lowgrantparent[u], lowgrantparent[v]);
            }
            else if (!scc[v])
            {
                lowgrantparent[u] = std::min(lowgrantparent[u], lowgrantparent[v]);
            }
        }

        if (pre[u] == lowgrantparent[u])
        {
            ++scc_no;
            while (true)
            {
                if (s.empty()) break;

                int tmp = s.top(); s.pop();
                scc[tmp] = scc_no;
                if (tmp == u) break;

            }
        }
    }

    void find_scc()
    {
        scc_no = 0;
        dfs_clock = 0;
        memset(pre, 0x00, sizeof(pre));
        memset(scc, 0x00, sizeof(scc));
        memset(lowgrantparent, 0x00, sizeof(lowgrantparent));

        for (int i = 1; i <= n; i++)
        {
            if (pre[i]) continue;
            dfs(i);
        }
    }

    int DP(int u)
    {
        if (dp[u]) return dp[u];
        dp[u] = d[u];

        for (std::set<int>::iterator it = pset[u].begin(); it != pset[u].end(); it++)
        {
            int v = *it;
            dp[u] += DP(v);
        }

        return dp[u];
    }
private:

    int scc_no;
    std::stack<int> s;
    int scc[N];
    int n;
    std::vector<int> g[N];
    int dfs_clock;
    int pre[N], lowgrantparent[N];
    int d[N], dp[N];
};

int main() {
#ifndef ONLINE_JUDGE
    std::ifstream fin("f:\\oj\\uva_in.txt");
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