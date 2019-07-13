#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <cstring>
#include <sstream>
#include <cstdlib>
#include <algorithm>
#include <climits>

using namespace std;

class Solution
{
public:
	void init()
	{
		memset(dp, 0x00, sizeof(dp));
		memset(p, 0xff, sizeof(p));
		for (int i = 0; i < N; i++)
		{
			adjList[i].clear();
		}
	}
	void setn(int n)
	{
		this->n = n;
	}

	void addEdge(vector<int> v)
	{
		if (v.size() > 1)
		{
			for (size_t i = 1; i < v.size(); i++)
			{
				adjList[v[0]].push_back(v[i]);
				p[v[i]] = v[0];
			}
		}
	}

	int solve()
	{
		int root = findRoot();
		//cout << "root:" << root << endl;
		int ans = INT_MAX;
		for (int i = 0; i < MAXCOLOR; i++)
		{
			ans = min(ans, dynamicProgram(root, i + 1));
		}
		return ans;
	}

private:
	int findRoot()
	{
		for (int i = 0; i < n; i++)
		{
			if (p[i] == -1) return i;
		}
		return -1;
	}

	int dynamicProgram(int u, int v)
	{
		if (dp[u][v - 1]) return dp[u][v - 1];
		int& ans = dp[u][v - 1];
		ans = v;

		for (size_t i = 0; i < adjList[u].size(); i++)
		{
			int res = INT_MAX;
			for (int j = 0; j < MAXCOLOR; j++)
			{
				if (j + 1 == v) continue;
				int tmp = dynamicProgram(adjList[u][i], j + 1);
				res = min(res, tmp);
			}
			ans += res;
		}

		return ans;
	}
private:
	static const int N = 10001;
	static const int MAXCOLOR = 6;

	int dp[N][MAXCOLOR];
	int n;
	vector<int> adjList[N];
	int p[N];
};

Solution solution;

vector<int> sepstr(char *str, char *sep)
{
	char *tok = strtok(str, sep);
	vector<int> ans;
	while (tok)
	{
		int v = atoi(tok);
		ans.push_back(v);
		tok = strtok(NULL, sep);
	}

	return ans;
}

int main()
{
#ifndef ONLINE_JUDGE
	ifstream fin("f:\\OJ\\uva_in.txt");
	streambuf* back = std::cin.rdbuf(fin.rdbuf());
#endif
		
	int n;
	while (cin >> n)
	{
		if (n == 0) break;
		solution.init();
		solution.setn(n);
		string s;
		getline(cin, s);
		for (int i = 0; i < n; i++)
		{
			getline(cin, s);
			vector<int> v = sepstr(const_cast<char*>(s.c_str()), ": ");
			solution.addEdge(v);
		}
		
		int res = solution.solve();
		cout << res << endl;
	}

#ifndef ONLINE_JUDGE
	cin.rdbuf(back);
#endif
	return 0;
}