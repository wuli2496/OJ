#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <algorithm>

using namespace std;

class Ant
{
public:
	Ant() {}

	Ant(int seqno, int loc, int dir)
	{
		this->seqno = seqno;
		this->loc = loc;
		this->dir = dir;
	}

	bool operator < (const Ant& other) const
	{
		return loc < other.loc;
	}
private:
	friend class PiotrsAnts;
	int seqno;
	int loc;
	int dir;
};

class PiotrsAnts
{
public:
	char *p[3]{"L", "Turning","R"};

	void piotrsAnts(vector<Ant> ants, int l, int t)
	{
		vector<Ant> afterAnts(ants.size());
		for (size_t i = 0; i < ants.size(); i++)
		{
			afterAnts[i].seqno = 0;
			afterAnts[i].loc = ants[i].loc + t * ants[i].dir;
			afterAnts[i].dir = ants[i].dir;
		}

		sort(ants.begin(), ants.end());

		vector<int> order(ants.size());
		for (size_t i = 0; i < ants.size(); i++)
		{
			order[ants[i].seqno] = i;
		}

		sort(afterAnts.begin(), afterAnts.end());
		for (size_t i = 0; i + 1 < afterAnts.size(); i++)
		{
			if (afterAnts[i].loc == afterAnts[i + 1].loc)
			{
				afterAnts[i].dir = afterAnts[i + 1].dir = 0;
			}
		}

		for (size_t i = 0; i < order.size(); i++)
		{
			int seq = order[i];
			int loc = afterAnts[seq].loc;
			if (loc < 0 || loc > l)
			{
				cout << "Fell off" << endl;
			}
			else
			{
				cout << loc << " " << p[afterAnts[seq].dir + 1] << endl;
			}
		}

	}
};


int main()
{
#ifndef ONLINE_JUDGE
	ifstream fin("f:\\OJ\\uva_in.txt");
	streambuf *old = cin.rdbuf(fin.rdbuf());
#endif

	int cas;
	cin >> cas;
	PiotrsAnts solver;
	for (int i = 0; i < cas; i++)
	{
		int l, t, n;
		cin >> l >> t >> n;
		vector<Ant> ants;

		for (int j = 0; j < n; j++)
		{
			int loc;
			string dir;
			cin >> loc >> dir;
			int d = (dir == "L" ? -1 : 1);
			Ant tmp(j, loc, d);
			ants.push_back(tmp);
		}
		cout << "Case #" << i + 1<< ":" << endl;
		solver.piotrsAnts(ants, l, t);
		cout << endl;
	} 

#ifndef ONLINE_JUDGE
	cin.rdbuf(old);
#endif
	return 0;
}