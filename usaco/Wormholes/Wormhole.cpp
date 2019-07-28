/*
ID:wuli2491
TASK:wormhole
LANG:C++
 */

#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <iterator>

using namespace std;

class Point
{
public:
	bool operator<(const Point& other) const
	{
		if (y != other.y)
		{
			return y < other.y;
		}
		
		return x < other.x;
	}

public:
	int x, y;
};

ostream& operator<<(ostream& stream, const Point& point)
{
	stream << "(" << point.x << "," << point.y << ")" << endl;
}

void print(const Point& point)
{
	cout << point;
}


class Solution
{
public:
	Solution(vector<Point> points)
	{
		m_points = points;
		ans = 0;
	}
	
	int solve()
	{
		sort(m_points.begin(), m_points.end());
#ifdef DEBUG
		for_each(m_points.begin(), m_points.end(), print);
#endif
		fillRights();
#ifdef DEBUG
		copy(m_rights.begin(), m_rights.end(), ostream_iterator<int>(cout, " "));		
		cout << endl;
#endif
		m_vis.resize(m_points.size());
		fill(m_vis.begin(), m_vis.end(), false);
		m_pairs.resize(m_points.size());
		fill(m_pairs.begin(), m_pairs.end(), -1);

		dfs(0);

		return ans;
	}

private:
	void fillRights()
	{
		m_rights.resize(m_points.size());
		fill(m_rights.begin(), m_rights.end(), -1);

		for (int i = 0; i < m_rights.size() - 1; ++i)
		{
			if (m_points[i + 1].y == m_points[i].y)
			{
				m_rights[i] = i + 1;			
			}
		}
	}

	void dfs(int cur)
	{
		if (cur > m_points.size() - 2)
		{
#ifdef DEBUG
			copy(m_pairs.begin(), m_pairs.end(), ostream_iterator<int>(cout, " "));
			cout << endl;
#endif
			check();			
			return;
		}
	
		int u = -1;
		int size = m_points.size();
		for (int i = 0; i < size; ++i)
		{
			if (!m_vis[i])
			{
				u = i;
				break;
			}
		}

		m_vis[u] = true;
		for (int v = u + 1; v < size; ++v)
		{
			if (!m_vis[v])
			{
				m_vis[v] = true;
				m_pairs[u] = v;
				m_pairs[v] = u;
				dfs(cur + 2);
				m_vis[v] = false;
			}
		}

		m_vis[u] = false;
	}

	void check()
	{
		int size = m_points.size();
		vector<int> vis(size);
		fill(vis.begin(), vis.end(), -1);
		int p = -1;
		for (int i = 0; i < size; ++i)
		{
			if (vis[i] != -1)
			{
				continue;
			}
			
			p = i;
			while ((p != -1) && (vis[p] == -1))
			{
				vis[p] = i;
				p = m_rights[p];
				if (p != -1)
				{
					p = m_pairs[p];
				}
			}
			if ((p != -1) && vis[p] == i)
			{
#ifdef DEBUG
				cout << "p:" << p << " i:" << i << endl;
#endif
				++ans;
				break;
			}
		}
	}

private:
	vector<Point> m_points;
	vector<int> m_rights;
	vector<bool> m_vis;
	vector<int> m_pairs;
	int ans;
};

int main(int argc, char** argv)
{
	ifstream fin("wormhole.in");
	ofstream fout("wormhole.out");
	streambuf* oldin = cin.rdbuf(fin.rdbuf());
	streambuf* oldout = cout.rdbuf(fout.rdbuf());
	
	int n;
	vector<Point> points;
	cin >> n;
	for (int i = 0; i < n; ++i)
	{
		Point point;
		cin >> point.x >> point.y;
		points.push_back(point);
	}

	sort(points.begin(), points.end());
	Solution solution(points);
	int ans = solution.solve();
	cout << ans << endl;

	return 0;
}
