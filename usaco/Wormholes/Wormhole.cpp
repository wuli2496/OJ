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

class Solution
{
public:
	Solution(vector<Point> points)
	{
		m_points = points;
	}
	
	int solve()
	{
		vector<int>* pGraph = new vector<int>[m_points.size()];
		buildGraph(pGraph);		
		vector<bool> vis;
		vis.resize(m_points.size());
		int ans = 0;

		for (size_t i = 0; i < m_points.size(); ++i)
		{
			if (!vis[i])
			{
				dfs(pGraph, i, ans, vis); 
			}
		}

		return ans;
	}

private:
	void buildGraph(vector<int> *graph)
	{
		size_t len = m_points.size();
		for (size_t i = 0; i < len - 1; ++i)
		{
			if (m_points[i + 1].y == m_points[i].y)
			{
				graph[i].push_back(i + 1);
				graph[i + 1].push_back(i);
			}
		}
	}

	void dfs(vector<int>* pGraph, int cur, int& ans, vector<bool>& vis)
	{
		size_t len = pGraph[cur].size();
		vis[cur] = true;
		for (int i = 0; i < len; ++i)
		{
			int v = pGraph[cur][i];
			if (vis[v])
			{
				continue;
			}
			
			vis[v] = true;
			++ans;
			break;
		}
	}
private:
	vector<Point> m_points;
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
