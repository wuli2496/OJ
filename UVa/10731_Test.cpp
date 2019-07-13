#include <iostream>
#include <fstream>
#include <vector>
#include <set>
#include <string>
#include <map>

using namespace std;

class Kosaraju
{
public:
	void init()
	{
		for (int i = 0; i < N; i++)
		{
			edges[i].clear();
			vedges[i].clear();
		}
		vertexes.clear();
	}

	void addEdge(int from, int to)
	{
		vertexes.insert(from); vertexes.insert(to);
		edges[from].push_back(to);
		vedges[to].push_back(from);
	}

	void solve()
	{
		find_scc();
		
		for (auto i : sccMap)
		{
			cout << (char)(i.first + 'A') << " " << i.second << endl;
		}

		cout << "scc_no:" << scc_no << endl;
		set<int> isVis;
		vector<set<int>> ans;
		for (auto i : vertexes)
		{
			if (isVis.count(i)) continue;
			set<int> s;
			for (map<int, int>::iterator it = sccMap.begin(); it != sccMap.end(); it++)
			{
				if (it->second == sccMap[i]) {
					s.insert(it->first);
					isVis.insert(it->first);
				}
			}
			ans.push_back(s);
		}
		for (size_t i = 0; i < ans.size(); i++)
		{
			for (auto i : ans[i])
			{
				cout << (char)('A' + i) << " ";
			}
			cout << endl;
		}
		
	}
private:
	void dfs(int u)
	{
		vis.insert(u);
		toposort.push_back(u);
		for (size_t i = 0; i < edges[u].size(); i++)
		{
			int v = edges[u][i];
			if (vis.count(v)) continue;
			
			dfs(v);
		}
		
	}

	void reverseDfs(int u)
	{
		sccMap[u] = scc_no;
		for (size_t i = 0; i < vedges[u].size(); i++)
		{
			int v = vedges[u][i];
			if (sccMap.find(v) == sccMap.end())
			{
				reverseDfs(v);
			}
		}
	}

	void find_scc()
	{
		scc_no = 0;
		sccMap.clear();
		vis.clear();
		toposort.clear();

		for (auto i : vertexes)
		{
			if (vis.count(i)) continue;
			dfs(i);
		}

		
		for (size_t i = 0; i < toposort.size(); i++)
		{
			cout << (char)('A' + toposort[i]) << endl;
		}
		
		for (size_t i = 0; i < toposort.size(); i++)
		{
			if (sccMap.find(toposort[i]) == sccMap.end()) 
			{ 
				scc_no++;
				reverseDfs(toposort[i]);
			}
		}

	}
	
private:
	static const int N = 26;
	vector<int> edges[N];
	vector<int> vedges[N];
	set<int> vertexes;
	vector<int> toposort;
	set<int> vis;
	int scc_no;
	map<int, int> sccMap;
};

Kosaraju solution;

int main()
{
#ifndef ONLINE_JUDGE
	ifstream fin("f:\\OJ\\uva_in.txt");
	streambuf* back = std::cin.rdbuf(fin.rdbuf());
#endif

	int n;
	while (cin >> n)
	{
		if (n == 0) break;
		solution.init();
		for (int i = 0; i < n; i++)
		{
			vector<string> strVertex;
			for (int j = 0; j < 5; j++)
			{
				string s;
				cin >> s;
				strVertex.push_back(s);
			}

			string preference;
			cin >> preference;
			for (int j = 0; j < (int)strVertex.size(); j++)
			{
				if (strVertex[j] != preference)
				{
					solution.addEdge(preference[0] - 'A', strVertex[j][0] - 'A');
				}
			}
		}
		solution.solve();
	}

#ifndef ONLINE_JUDGE
	cin.rdbuf(back);
#endif
	return 0;
}