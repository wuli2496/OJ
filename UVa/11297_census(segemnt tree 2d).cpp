#include <iostream>
#include <fstream>
#include <algorithm>
#include <string>
#include <climits>

const int N = 501;
const int MAXN = 4 * N;

int matrix[N][N];
int maxv[MAXN][MAXN], minv[MAXN][MAXN];

class SegmentTree2
{
public:
	SegmentTree2()
	{
		m_minv = &minv[0];
		m_maxv = &maxv[0];
		m_matrix = &matrix[0];
	}

	void build(int n)
	{
		this->n = n;
		build2D(1, 1, n);
	}

	void modify(int x, int y, int v)
	{
		this->x = x;
		this->y = y;
		this->v = v;

		modify2D(1, 1, n);
	}

	void query(int x1, int y1, int x2, int y2)
	{
		this->x1 = x1; this->y1 = y1; this->x2 = x2; this->y2 = y2;
		this->min_ans = INT_MAX;
		this->max_ans = INT_MIN;
		query2D(1, 1, n);
	}

	inline int getMin() { return min_ans; }
	inline int getMax() { return max_ans; }

	void output()
	{
		/*
		for (int i = 1; i <= n; i++)
		{
			for (int j = 1; j <= n; j++)
			{
				std::cout << m_matrix[i][j] << " ";
			}
			std::cout << std::endl;
		}
		*/

		//std::cout << m_maxv[8][1] << std::endl;
	}
private:
	void build2D(int o, int l, int r)
	{
		if (l == r)
		{
			xo = o;
			row = l;
			build1D(1, 1, n);
		}
		else
		{
			int mid = (l + r) >> 1;
			build2D(o * 2, l, mid);
			build2D(o * 2 + 1, mid + 1, r);
			for (int i = 1; i <= 4 * n; i++)
			{
				m_maxv[o][i] = std::max(m_maxv[o * 2][i], m_maxv[o * 2 + 1][i]);
				m_minv[o][i] = std::min(m_minv[o * 2][i], m_minv[o * 2 + 1][i]);
			}
		}
	}

	void build1D(int o, int l, int r)
	{
		if (l == r)
		{
			m_maxv[xo][o] = m_minv[xo][o] = m_matrix[row][l];
		}
		else
		{
			int mid = (l + r) >> 1;
			build1D(o * 2, l, mid);
			build1D(o * 2 + 1, mid + 1, r);
			m_maxv[xo][o] = std::max(m_maxv[xo][o * 2], m_maxv[xo][o * 2 + 1]);
			m_minv[xo][o] = std::min(m_minv[xo][o * 2], m_minv[xo][o * 2 + 1]);
		}
	}

	void modify2D(int o, int l, int r)
	{
		if (l == r)
		{
			xo = o;
			leaf = true;
			modify1D(1, 1, n);
		}
		else
		{
			int mid = (l + r) >> 1;
			if (x <= mid) modify2D(o * 2, l, mid);
			else modify2D(o * 2 + 1, mid + 1, r);
			xo = o;
			leaf = false;
			modify1D(1, 1, n);
		}
	}

	void modify1D(int o, int l, int r)
	{
		if (l == r)
		{
			if (leaf)
			{
				m_maxv[xo][o] = m_minv[xo][o] = v;
				return;
			}

			m_maxv[xo][o] = std::max(m_maxv[xo * 2][o], m_maxv[xo * 2 + 1][o]);
			m_minv[xo][o] = std::min(m_minv[xo * 2][o], m_minv[xo * 2 + 1][o]);
		}
		else
		{
			int mid = (l + r) >> 1;
			if (y <= mid) modify1D(o * 2, l, mid);
			else modify1D(o * 2 + 1, mid + 1, r);
			m_maxv[xo][o] = std::max(m_maxv[xo][o * 2], m_maxv[xo][o * 2 + 1]);
			m_minv[xo][o] = std::min(m_minv[xo][o * 2], m_minv[xo][o * 2 + 1]);
		}
	}

	void query2D(int o, int l, int r)
	{
		if (x1 <= l && x2 >= r)
		{
			xo = o;
			query1D(1, 1, n);
		}
		else
		{
			int m = (l + r) >> 1;
			if (x1 <= m) query2D(o * 2, l, m);
			if (x2 > m) query2D(o * 2 + 1, m + 1, r);
		}
	}

	void query1D(int o, int l, int r)
	{
		if (y1 <= l && y2 >= r)
		{
			min_ans = std::min(min_ans, m_minv[xo][o]);
			max_ans = std::max(max_ans, m_maxv[xo][o]);
		}
		else
		{
			int m = (l + r) >> 1;
			if (y1 <= m) query1D(o * 2, l, m);
			if (y2 > m) query1D(o * 2 + 1, m + 1, r);
		}
	}
private:
	int xo;
	bool leaf;
	int row;
	int n;
	int x, y, v;
	int x1, y1, x2, y2;
	int min_ans, max_ans;
	int(*m_maxv)[MAXN];
	int(*m_minv)[MAXN];
	int (*m_matrix)[N];
};


SegmentTree2 segment_tree;
int main()
{
#ifndef ONLINE_JUDGE
	std::ifstream fin("f:\\OJ\\uva_in.txt");
	std::streambuf* back = std::cin.rdbuf(fin.rdbuf());
#endif

	int n;
	while (std::cin >> n)
	{
		for (int i = 1; i <= n; i++)
		{
			for (int j = 1; j <= n; j++)
			{
				std::cin >> matrix[i][j];
			}
		}
		segment_tree.build(n);
		segment_tree.output();
		int q;
		std::cin >> q;
		while (q--)
		{
			std::string s;
			std::cin >> s;
			if (s == "q") 
			{
				int x1, y1, x2, y2;
				std::cin >> x1 >> y1 >> x2 >> y2;
				segment_tree.query(x1, y1, x2, y2);
				std::cout << segment_tree.getMax() << " " << segment_tree.getMin() << std::endl;
			}
			else if (s == "c")
			{
				int x, y, v;
				std::cin >> x >> y >> v;
				segment_tree.modify(x, y, v);
			}

		}
	}


#ifndef ONLINE_JUDGE
	std::cin.rdbuf(back);
#endif

	return 0;
}