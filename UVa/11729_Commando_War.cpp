#include <iostream>
#include <fstream>
#include <algorithm>
#include <vector>

using namespace std;

class Job
{
private:
	int b, j;
public:
	Job(int b, int j)
	{
		this->b = b;
		this->j = j;
	}

	bool operator < (const Job& other) const
	{
		return j > other.j;
	}
	friend class Solution;
};

class Solution
{
public:
	int calcTime(vector<Job> v)
	{
		sort(v.begin(), v.end());
		int ans = 0;
		int s = 0;
		for (size_t i = 0; i < v.size(); i++)
		{
			s += v[i].b;
			ans = max(ans, s + v[i].j);
		}
		return ans;
	}
};

Solution solver;

int main()
{
#ifndef ONLINE_JUDGE
	ifstream fin("f:\\OJ\\uva_in.txt");
	streambuf *old = cin.rdbuf(fin.rdbuf());
#endif
	int n;
	int kase = 1;

	while (cin >> n, n)
	{
		vector<Job> v;
		for (int i = 0; i < n; i++)
		{
			int b, j;
			cin >> b >> j;
			Job job{ b, j };
			v.push_back(job);
		}

		int ans = solver.calcTime(v);
		cout << "Case " << kase++ << ": " << ans << endl;
	}

#ifndef ONLINE_JUDGE
	cin.rdbuf(old);
#endif
	return 0;
}