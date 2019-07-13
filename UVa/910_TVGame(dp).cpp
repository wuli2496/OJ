#include <iostream>
#include <fstream>
#include <vector>
#include <cstring>
#include <string>

class Solution
{
public:
	void init()
	{
		memset(move, 0x00, sizeof(move));
		memset(dp, 0x00, sizeof(dp));
		special.clear();
	}
	void addEdge(int s, int d1, int d2)
	{
		move[s][0] = d1; move[s][1] = d2;
	}

	void setn(int n)
	{
		this->n = n;
	}

	void setm(int m)
	{
		this->m = m;
	}

	void setSpecial(std::vector<int> v)
	{
		for (int spe : v)
		{
			special.push_back(spe);
		}
	}

	int solve()
	{
		dp[0][1] = 1;
		for (int i = 1; i <= m; i++)
		{
			for (int j = 1; j <= n; j++)
			{
				for (int k = 1; k <= n; k++)
				{
					if (dp[i - 1][k] && move[k][0] == j)
					{
						dp[i][j] += dp[i - 1][k];
					}

					if (dp[i - 1][k] && move[k][1] == j)
					{
						dp[i][j] += dp[i - 1][k];
					}
				}
			}
		}

		int ans = 0;
		for (int v : special)
		{
			ans += dp[m][v];
		}

		return ans;

	}
private:
	static const int MAXV = 27;
	static const int MAX_MOVE = 31;

	int move[MAXV][2];
	std::vector<int> special;
	int n, m;
	int dp[MAX_MOVE][MAXV];
};

Solution solution;

int main()
{
#ifndef ONLINE_JUDGE
	std::ifstream fin("f:\\OJ\\uva_in.txt");
	std::streambuf* back = std::cin.rdbuf(fin.rdbuf());

	std::ofstream fout("f:\\OJ\\uva_out.txt");
	std::streambuf* outback = std::cout.rdbuf(fout.rdbuf());
#endif
	int n;
	while (std::cin >> n)
	{
		solution.init();
		solution.setn(n);
		std::vector<int> v;
		for (int i = 0; i < n; i++)
		{
			std::string s, d1, d2, spe;
			std::cin >> s >> d1 >> d2 >> spe;
			int from = s[0] - 'A' + 1;
			int to1 = d1[0] - 'A' + 1;
			int to2 = d2[0] - 'A' + 1;
			solution.addEdge(from, to1, to2);
			if (spe == "x")
			{
				v.push_back(from);
			}
		}
		solution.setSpecial(v);

		int m;
		std::cin >> m;
		solution.setm(m);
		int ans = solution.solve();
		std::cout << ans << std::endl;
	}
	
#ifndef ONLINE_JUDGE
	std::cin.rdbuf(back);
	std::cout.rdbuf(outback);
#endif
	return 0;
} 