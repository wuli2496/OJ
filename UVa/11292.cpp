#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>

using namespace std;

class Solution
{
public:
	void tragonOfLoowater(vector<int> dragons, vector<int> knights)
	{
		sort(dragons.begin(), dragons.end());
		sort(knights.begin(), knights.end());

		int n = dragons.size(), m = knights.size();
		int cur = 0;
		int cost = 0;
		for (int i = 0; i < m; i++)
		{
			if (knights[i] >= dragons[cur])
			{
				cur++;
				cost += knights[i];
				if (cur >= n) break;
			}
		}

		if (cur < n)
		{
			cout << "Loowater is doomed!" << endl;
		}
		else
		{
			cout << cost << endl;
		}
	}
};

Solution solver;

int main()
{
#ifndef ONLINE_JUDGE
	ifstream fin("f:\\OJ\\uva_in.txt");
	streambuf *old = cin.rdbuf(fin.rdbuf());
#endif
	int n, m;
	while (cin >> n >> m)
	{
		if (n == 0 && m == 0) break;
		vector<int> dragons, knights;
		for (int i = 0; i < n; i++)
		{
			int v;
			cin >> v;
			dragons.push_back(v);
		}

		for (int i = 0; i < m; i++)
		{
			int v;
			cin >> v;
			knights.push_back(v);
		}

		solver.tragonOfLoowater(dragons, knights);
	}

#ifndef ONLINE_JUDGE
	cin.rdbuf(old);
#endif
	return 0;
}