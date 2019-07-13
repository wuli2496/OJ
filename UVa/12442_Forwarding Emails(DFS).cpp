#include <iostream>
#include <fstream>
#include <cstring>
#include <algorithm>

const int N = 50001;


class Solution
{
public:

	void init(int n)
	{
		m_n = n;
	}

	void setEdge(int i, int j)
	{
		edge[i] = j;
	}

	int dfs(int u)
	{
		int result = 0;
		vis[u] = true;

		int v = edge[u];
		if (!vis[v])
		{
			result = dfs(v) + 1;
		}
		vis[u] = false;
		preVis[u] = true;
		
		return result;
	}

	int solve()
	{
		memset(vis, false, sizeof(vis));
		memset(preVis, false, sizeof(preVis));

		int ans = 0;
		int max = 0;
		for (int i = 1; i <= m_n; i++)
		{
			if (preVis[i]) continue;
			
			int tmp = dfs(i);
			if (tmp > max)
			{
				ans = i;
				max = tmp;
			}
		}

		return ans;
	}
private:
	int m_n;
	int edge[N];
	bool vis[N], preVis[N];
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