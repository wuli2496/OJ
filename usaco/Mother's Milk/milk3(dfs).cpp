/*
ID:wuli2491
TASK:milk3
 LANG:C++
 */
#include <iostream>
#include <memory>
#include <fstream>
#include <cstring>
#include <algorithm>
#include <vector>

using namespace std;

const int MAXN = 21;
const int MAXNUM = 3;

class AlgoPolicy
{
public:
    virtual ~AlgoPolicy() {}
    virtual vector<int> execute() = 0;
};

class DfsAlgo : public AlgoPolicy
{
public:
    DfsAlgo(int a, int b, int c)
    {
        caps[0] = a;
        caps[1] = b;
        caps[2] = c;
        memset(f, false, sizeof(f));
        fill(ans, ans + MAXN, false);
    }

    virtual vector<int> execute()
    {
        dfs(State(0, 0, caps[2]));

        vector<int> v;
        for (int i = 0; i < MAXN; ++i)
        {
            if (ans[i])
            {
                v.push_back(i);
            }
        }

        return v;
    }

private:
    struct State
    {
        int a[MAXNUM];

        State(int tmpa, int tmpb, int tmpc)
        {
            a[0] = tmpa;
            a[1] = tmpb;
            a[2] = tmpc;
        }
    };

    void dfs(const State& s)
    {
        if (f[s.a[0]][s.a[1]][s.a[2]])
        {
            return;
        }

        if (!s.a[0])
        {
            ans[s.a[2]] = true;
        }

        f[s.a[0]][s.a[1]][s.a[2]] = true;

        for (int i = 0; i < MAXNUM; ++i)
        {
            for (int j = 0; j < MAXNUM; ++j)
            {
                dfs(pour(s, i, j));
            }
        }
    }

    State pour(const State& s, int from, int to)
    {
        State ans = s;
        int tmp = ans.a[from];
        if (tmp + ans.a[to] > caps[to])
        {
            tmp = caps[to] - ans.a[to];
        }

        ans.a[from] -= tmp;
        ans.a[to] += tmp;

        return ans;
    }

private:
    bool f[MAXN][MAXN][MAXN];
    int caps[MAXNUM];
    bool ans[MAXN];
};

class Solution
{
public:
    Solution(AlgoPolicy* algoPolicy)
    {
        sp.reset(algoPolicy);
    }

    vector<int> run()
    {
        return sp->execute();
    }
private:
    shared_ptr<AlgoPolicy> sp;
};

int main()
{
#ifndef ONLINE_JUDGE
    ifstream fin("milk3.in");
    ofstream fout("milk3.out");
    streambuf* finback = cin.rdbuf(fin.rdbuf());
    streambuf* foutback = cout.rdbuf(fout.rdbuf());
#endif

    int a, b, c;
    cin >> a >> b >> c;
    AlgoPolicy* policy = new DfsAlgo(a, b, c);
    Solution solution(policy);
    vector<int> ans = solution.run();
    for (size_t i = 0, len = ans.size(); i < len; ++i)
    {
        cout << ans[i];
        if (i + 1 != len)
        {
            cout << " ";
        }
    }
    cout << endl;

#ifndef ONLINE_JUDGE
    cin.rdbuf(finback);
    cout.rdbuf(foutback);
#endif

    return 0;
}