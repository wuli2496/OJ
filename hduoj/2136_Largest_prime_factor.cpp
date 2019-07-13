#include <cstdio>
#include <cstring>

class Solution
{
public:
	Solution()
	{
		memset(vis, 0x00, sizeof(vis));
		int index = 1;
		for (int i = 2; i < N; i++)
		{
			if (!vis[i])
			{
				vis[i] = index;
				for (int j = i * 2; j < N; j += i)
				{
					vis[j] = index;
				}
				index++;
			}
		}
	}

	void init(int n)
	{
		m_n = n;
	}

	int solve()
	{
		return vis[m_n];
	}
private:
	int m_n;
	static const int N = 1000001;
	int vis[N];
};

Solution solver;

int main()
{
	
#ifndef ONLINE_JUDGE
	freopen("f:\\OJ\\hdu_in.txt", "r", stdin);
#endif
	int n;
	while (scanf("%d", &n) == 1)
	{
		solver.init(n);
		printf("%d\n", solver.solve());
	}
	return 0;
}