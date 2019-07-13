//
// Created by John on 2017/9/7.
//

#include <iostream>
#include <fstream>
#include <string>
#include <cstring>
#include <cstdlib>
#include <algorithm>

const int MAXN = 1000;
const int MAXM = 100;
const int MAXT = 100;

int dp[MAXN][MAXM][MAXT];
int ti[MAXN];

class Solution
{
public:
    void init(int n, int t, int m)
    {
        this->n = n; this->t = t; this->m = m;
    }

    int solve()
    {
        memset(dp, 0x00, sizeof(dp));
        for (int i = 1; i <= n; i++)
        {
            for (int j = 1; j <= m; j++)
            {
                for (int k = 0; k <= t; k++)
                {
                    dp[i][j][k] = std::max(dp[i][j][k], dp[i - 1][j][k]);
                    if (k >= ti[i])
                    {
                        dp[i][j][k] = std::max(dp[i][j][k], dp[i - 1][j][k - ti[i]] + 1);
                        dp[i][j][k] = std::max(dp[i][j][k], dp[i - 1][j - 1][t] + 1);
                    }
                }
            }
        }

        return dp[n][m][t];
    }
private:
    int n, t, m;
};

Solution solution;

int main()
{
#ifndef ONLINE_JUDGE
    std::ifstream fin("f:\\OJ\\uva_in.txt");
    std::streambuf* back = std::cin.rdbuf(fin.rdbuf());
#endif

    int cas;
    std::cin >> cas;
    while (cas--)
    {
        int n, t, m;
        std::cin >> n >> t >> m;
        solution.init(n, t, m);

        for (int i = 1; i <= n; i++)
        {
            std::string s;
            std::cin >> s;
            ti[i] = atoi(s.data());
        }

        int ans = solution.solve();
        std::cout << ans << std::endl;
        if (cas) std::cout << std::endl;
    }
#ifndef ONLINE_JUDGE
    std::cin.rdbuf(back);
#endif
    return 0;
}

