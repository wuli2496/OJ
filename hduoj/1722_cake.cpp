#include <cstdio>

class Solution
{
public:
	void init(int p, int q)
	{
		m_p = p; m_q = q;
	}

	int solve()
	{
		return m_p + m_q - gcd(m_p, m_q);
	}

private:
	int gcd(int n, int m)
	{
		if (m == 0) return n;
		else return gcd(m, n % m);
	}
private:
	int m_p, m_q;
};

Solution solver;

int main()
{
#ifndef ONLINE_JUDGE
	freopen("f:\\OJ\\hdu_in.txt", "r", stdin);
#endif

	int p, q;
	while (scanf("%d%d", &p, &q) == 2)
	{
		solver.init(p, q);
		printf("%d\n", solver.solve());
	}
	return 0;
}