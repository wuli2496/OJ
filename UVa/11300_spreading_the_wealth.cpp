#include <iostream>
#include <fstream>
#include <algorithm>
#include <vector>

using namespace std;

typedef unsigned long long ULL;

class SpreadingTheWealth
{
public:
	ULL spreadTheWealth(vector<ULL> v)
	{
		vector<int> c(v.size());
		ULL sum = 0;
		for (size_t i = 0; i < v.size(); i++) sum += v[i];
		ULL m = sum / v.size();

		c[0] = 0;
		for (size_t i = 1; i < c.size(); i++)
		{
			c[i] = c[i - 1] + v[i - 1] - m;
		}

		sort(c.begin(), c.end());

		ULL ans = 0;
		ULL x = c[c.size() / 2];
		for (size_t i = 0; i < v.size(); i++)
		{
			ans += labs(c[i] - x);
		}

		return ans;
	}
};

SpreadingTheWealth solver;

int main()
{
#ifndef ONLINE_JUDGE
	ifstream fin("f:\\OJ\\uva_in.txt");
	streambuf *old = cin.rdbuf(fin.rdbuf());
#endif
	
	int n;
	while (cin >> n)
	{
		vector<ULL> v;
		for (int i = 0; i < n; i++)
		{
			ULL coin;
			cin >> coin;
			v.push_back(coin);
		}
		cout << solver.spreadTheWealth(v) << endl;
	}
#ifndef ONLINE_JUDGE
	cin.rdbuf(old);
#endif
	return 0;
}