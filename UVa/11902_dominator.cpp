#include <iostream>
#include <fstream>
#include <cstring>
#include <vector>

using namespace std;

const int N = 101;
short adj_matrix[N][N];
int n;
bool vis[N];
short graph[N][N];

class Solution
{
public:
	void init()
	{
		memset(adj_matrix, 0x00, sizeof(adj_matrix));
	}

	std::vector<std::vector<bool>> solve()
	{
		std::vector<std::vector<bool>> ans(n);

		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < n; j++)
			{
				for (int k = 0; k < n; k++)
				{
					if (j == i || k == i)
					{
						graph[j][k] = 0;
					}
					else
					{
						graph[j][k] = adj_matrix[j][k];
					}
				}
			}

			memset(vis, false, sizeof(vis));
			dfs(0);

			for (int j = 0; j < n; j++)
			{
				ans[i].push_back(!vis[j]);
			}
		}

		memset(vis, false, sizeof(vis));
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < n; j++)
			{
				graph[i][j] = adj_matrix[i][j];
			}
		}

		ans[0][0] = true;
		dfs(0);
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < n; j++)
			{
				if (!vis[i] || !vis[j])
				{
					ans[i][j] = false;
				}
			}
		}
		return ans;
	}

private:
	void dfs(int cur)
	{
		vis[cur] = true;
		for (int i = 0; i < n; i++)
		{
			if (graph[cur][i] && !vis[i])
			{
				dfs(i);
			}
		}
	}


};

int main()
{
#ifndef ONLINE_JUDGE
	std::ifstream fin("f:\\OJ\\uva_in.txt");
	std::streambuf* back = std::cin.rdbuf(fin.rdbuf());
	//std::ofstream fout("f:\\OJ\\uva_out.txt");
	//std::streambuf* backoutput = std::cout.rdbuf(fout.rdbuf());
#endif

	int t;
	Solution solver;

	std::cin >> t;
	for (int i = 1; i <= t; i++)
	{
		std::cout << "Case " << i << ":" << std::endl;

		std::cin >> n;
		for (int j = 0; j < n; j++)
		{
			for (int k = 0; k < n; k++)
			{
				std::cin >> adj_matrix[j][k];
			}
		}
		std::vector<std::vector<bool>> ans = solver.solve();
		for (size_t i = 0; i < ans.size(); i++)
		{
			std::cout << "+";
			for (size_t j = 0; j < 2 * (ans.size() - 1) + 1; j++)
			{
				std::cout << "-";
			}
			std::cout << "+" << std::endl;

			std::cout << "|";
			for (size_t j = 0; j < ans[0].size(); j++)
			{
				std::cout << (ans[i][j] ? "Y" : "N");
				if (j != ans[0].size() - 1)
				{
					std::cout << "|";
				}
			}
			std::cout << "|" << std::endl;
		}
		std::cout << "+";
		for (size_t j = 0; j < 2 * (ans.size() - 1) + 1; j++)
		{
			std::cout << "-";
		}
		std::cout << "+" << std::endl;

	}
#ifndef ONLINE_JUDGE
	std::cin.rdbuf(back);
	//std::cout.rdbuf(backoutput);
#endif

	return 0;
}
