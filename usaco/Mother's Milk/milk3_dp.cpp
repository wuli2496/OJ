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

class DpAlgo : public AlgoPolicy
{
public:
    DpAlgo(int a, int b, int c)
    {
        caps[0] = a;
        caps[1] = b;
        caps[2] = c;

        memset(f, false, sizeof(f));
        memset(ans, false, sizeof(ans));

        f[0][0][caps[2]] = true;
    }

    virtual vector<int> execute()
    {
        bool finished = false;
        while (!finished)
        {
            finished = true;
            for (int i = 0; i <= caps[0]; ++i)
            {
                for (int j = 0; j <= caps[1]; ++j)
                {
                    for (int k = 0; k <= caps[2]; ++k)
                    {
                        if (f[i][j][k])
                        {
                            if (!i)
                            {
                                ans[k] = true;
                            }

                            State state(i, j, k);
                            transform(state, 0, 1, finished);
                            transform(state, 0, 2, finished);
                            
                            transform(state, 1, 0, finished);

                            transform(state, 1, 2, finished);

                            transform(state, 2, 0, finished);
                             
                            transform(state, 2, 1, finished);
                        }
                    }
                }
            }
        }

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
        
        State(int s1, int s2, int s3)
        {
            a[0] = s1;
            a[1] = s2;
            a[2] = s3;
        }
    };

    void transform(const State& s, int from, int to, bool& ok)
    {
        if (s.a[from] <= 0)
        {
            return;
        }

        State tmp = s;
        int amt = s.a[from];
        if (amt + s.a[to] > caps[to])
        {
            amt = caps[to] - s.a[to];
        }

        tmp.a[from] -= amt;
        tmp.a[to] += amt;

        if (!f[tmp.a[0]][tmp.a[1]][tmp.a[2]])
        {
            f[tmp.a[0]][tmp.a[1]][tmp.a[2]] = true;
            ok = false;
        }

        return;
    }
private:
   int caps[MAXNUM];
   bool f[MAXN][MAXN][MAXN];
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
    AlgoPolicy* policy = new DpAlgo(a, b, c);
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
