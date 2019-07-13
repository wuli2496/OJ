#include <iostream>
#include <fstream>
#include <cmath>
#include <iomanip>

using namespace std;

const double PERIMETER = 10000;

class Graveyard
{
public:
	double calDis(int n, int m)
	{
		double dis = 0;
		for (int i = 1; i < n; i++)
		{
			double location = (double)i / n * (n + m);
			dis += fabs(location - floor(location + 0.5)) / (n + m);
		}

		return dis * PERIMETER;
	}
};

Graveyard solution;

int main()
{
#ifndef ONLINE_JUDGE
	ifstream fin("f:\\OJ\\uva_in.txt");
	streambuf *old = cin.rdbuf(fin.rdbuf());
#endif

	int n, m;

	while (cin >> n >> m)
	{
		cout << fixed << setprecision(4) << solution.calDis(n, m) << endl;
	}
#ifndef ONLINE_JUDGE
	cin.rdbuf(old);
#endif
	return 0;
}