/*
ID:wuli2491
TASK:wormhole
LANG:C++
 */

#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>

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

template<typename T>
void print(const T& point)
{
	cout << point;
}

class Solution
{
public:
	Solution(vector<Point> points)
	{
		m_points = points;
	}
	
	int solve()
	{
		m_rights.resize(m_points.size());
		fill(m_rights.begin(), m_rights.end(), 0);
		fillRights();
		
		m_pairs.resize(m_points.size());
		fill(m_pairs.begin(), m_pairs.end(), 0);

		return dfs();
	}

private:
	void fillRights()
	{
		for (size_t i = 1, n = m_rights.size(); i < n; ++i)
		{
			if (m_points[i + 1].y == m_points[i].y)
			{
				m_rights[i] = i + 1;
			}
		}
	}
	
	bool cycleExists()
	{
		size_t n = m_points.size();
		for (int i = 1; i < n; ++i)
		{
			int start = i;
			for (size_t j = 1; j < n; ++j)
			{
				start = m_rights[m_pairs[j]];
			}

			if (start != 0)
			{
				return true;
			}
		}

		return false;
	}

	int dfs()
	{
		int total = 0;
		int size = m_points.size();
		int i;
		for (i = 1; i < size; ++i)
		{
			if (!m_pairs[i])
			{
				break;
			}
		}

		if (i >= size)
		{
			if (cycleExists())
			{
				return 1;
			}

			return 0;
		}

		for (int v = i + 1; v < size; ++v)
		{
			if (!m_pairs[v])
			{
				m_pairs[i] = v;
				m_pairs[v] = i;
				total += dfs();
				m_pairs[i] = 0;
				m_pairs[v] = 0;
			}
		}

		return total;
	}

private:
	vector<Point> m_points;
	vector<int> m_rights;
	vector<int> m_pairs;
};

int main(int argc, char** argv)
{
	ifstream fin("wormhole.in");
	ofstream fout("wormhole.out");
	streambuf* oldin = cin.rdbuf(fin.rdbuf());
	streambuf* oldout = cout.rdbuf(fout.rdbuf());
	
	int n;
	vector<Point> points;
	points.push_back((Point){0, 0});
	cin >> n;
	for (int i = 0; i < n; ++i)
	{
		Point point;
		cin >> point.x >> point.y;
		points.push_back(point);
	}
	
	sort(points.begin() + 1, points.end());
	Solution solution(points);
	int ans = solution.solve();
	cout << ans << endl;
	return 0;
}
