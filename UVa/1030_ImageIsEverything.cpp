#include <iostream>
#include <cstdio>
#include <fstream>

using namespace std;

const int N = 11;
const int SIX = 6;

#define REP(i, n) for (int i = 0; i < n; ++i)

char view[SIX][N][N];
char pos[N][N][N];

char readChar()
{
	char ch;

	while (1)
	{
		ch = cin.get();
		if ((('A' <= ch) && ('Z' >= ch)) || ('.' == ch))
		{
			return ch;
		}
	}
}

void getPos(int n, int v, int i, int j, int len, int& x, int& y, int& z)
{
	if (0 == v)
	{
		x = len; y = j; z = i;
	}
	else if (1 == v)
	{
		x = n - 1 - j; y = len; z = i;
	}
	else if (2 == v)
	{
		x = n - 1 - len; y = n - 1 - j; z = i;
	}
	else if (3 == v)
	{
		x = j; y = n - 1 - len; z = i;
	}
	else if (4 == v)
	{
		x = n - 1 - i; y = j; z = len;
	} 
	else
	{
		x = i; y = j; z = n - 1 - len;
	}
}

int solve(int n)
{
	REP(i, n) REP(j, SIX) REP(k, n) view[j][i][k] = readChar();
	REP(i, n) REP(j, n) REP(k, n) pos[i][j][k] = '#';

	REP(i, SIX) REP(j, n) REP(k, n) if ('.' == view[i][j][k])
	{
		REP(p, n)
		{
			int x, y, z;
			getPos(n, i, j, k, p, x, y, z);
			pos[x][y][z] = '.';
		}
	}

	for (;;)
	{
		bool done = true;
		REP(i, SIX) REP(j, n) REP(k, n)
		{
			if ('.' != view[i][j][k])
			{
				REP(p, n)
				{
					int x, y, z;
					getPos(n, i, j, k, p, x, y, z);
					if ('.' == pos[x][y][z]) continue;
					if ('#' == pos[x][y][z])
					{
						pos[x][y][z] = view[i][j][k];
						break;
					}

					if (pos[x][y][z] == view[i][j][k]) break;

					pos[x][y][z] = '.';
					done = false;
				}
			}
		}

		if (done) break;
	}

	int ans = 0;
	REP(i, n) REP(j, n) REP(k, n)
	{
		if ('.' != pos[i][j][k]) ++ans;
	}

	return ans;
}

int main()
{
#ifndef ONLINE_JUDGE
	ifstream fin("f:\\OJ\\uva_in.txt");
	streambuf *back = cin.rdbuf(fin.rdbuf());
#endif

	int n;
	while (cin >> n, n)
	{
		int ans = solve(n);
		cout << "Maximum weight: " << ans << " gram(s)" << endl;
	}

#ifndef ONLINE_JUDGE
	cin.rdbuf(back);
#endif

	return 0;
}