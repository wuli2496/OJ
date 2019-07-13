#include <iostream>
#include <fstream>
#include <climits>
#include <algorithm>

using namespace std;

int** newA(int n);
void freeA(int **a, int n);

void print(int**a, int n)
{
	for (int i = 0; i < n; ++i)
	{
		for (int j = 0; j < n; ++j)
		{
			cout << a[i][j] << " ";
		}
		cout << endl;
	}
}

class Solution
{
public:
	Solution(int **a, int n)
	{
		m_pa = a;
		m_n = n;
	}

	int solve()
	{
		int** b = newA(m_n);

		int ans = INT_MAX;
		for (int i = 0; i < (1 << m_n); ++i)
		{
			int tmp = check(b, i);
			ans = min(ans, tmp);
		}

		freeA(b, m_n);

		return ans;
	}

private:
	int check(int** b, int s)
	{
		for (int i = 0; i < m_n; ++i)
		{
			if (s & (1 << (m_n - 1 - i)))
			{
				b[0][i] = 1;
			}
			else if (1 == m_pa[0][i])
			{
				return INT_MAX;
			}
			else
			{
				b[0][i] = 0;
			}
		}

		for (int r = 1; r < m_n; ++r)
		{
			for (int c = 0; c < m_n; ++c)
			{
				int sum = 0;
				if (r > 1) sum += b[r - 2][c];
				if (c > 0) sum += b[r - 1][c - 1];
				if (c < m_n - 1) sum += b[r - 1][c + 1];
				b[r][c] = sum % 2;

				if (0 == b[r][c] && 1 == m_pa[r][c]) return INT_MAX;
			}
		}

		int ans = 0;
		for (int i = 0; i < m_n; ++i)
		{
			for (int j = 0; j < m_n; ++j)
			{
				if (b[i][j] != m_pa[i][j]) ++ans;
			}
		}
		return ans;
	}
private:
	int** m_pa;
	int m_n;
};


int** newA(int n)
{
	int**a = new int*[n];
	for (int i = 0; i < n; ++i)
	{
		a[i] = new int[n];
	}

	return a;
}

void freeA(int **a, int n)
{
	for (int i = 0; i < n; ++i)
	{
		delete[] a[i];
	}

	delete[] a;
}

int main()
{
#ifndef ONLINE_JUDGE
	ifstream fin("f:\\OJ\\uva_in.txt");
	streambuf *old = cin.rdbuf(fin.rdbuf());
#endif

	int t;
	int n;
	cin >> t;

	for (int i = 0; i < t; ++i)
	{
		cin >> n;
		int** a = newA(n);

		for (int j = 0; j < n; ++j)
		{
			for (int k = 0; k < n; ++k)
			{
				cin >> a[j][k];
			}
		}

		Solution solver(a, n);
		int ans = solver.solve();

		freeA(a, n);

		cout << "Case " << i + 1 << ": ";
		if (INT_MAX == ans)
		{
			cout << -1 << endl;
		}
		else
		{
			cout << ans << endl;
		}
	}


#ifndef ONLINE_JUDGE
	cin.rdbuf(old);
#endif
	return 0;
}