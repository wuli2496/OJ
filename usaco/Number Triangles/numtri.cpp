/*
ID:wuli2491
TASK:numtri
LANG:C++
*/
#include <iostream>
#include <fstream>
#include <vector>
#include <memory>
#include <cstring>
#include <algorithm>

using namespace std;

class AlgoPolicy
{
public:
    ~AlgoPolicy() {}
    virtual int execute() = 0;
};

class IteratorAlgo : public AlgoPolicy
{
public:
    IteratorAlgo(vector<vector<int>>& v)
    {
        m_vec = v;   
        memset(costs, 0, sizeof(costs));
    }

    virtual int execute()
    {
        for (size_t i = 0; i < m_vec.size(); ++i)
        {
            memcpy(lastcosts, costs, sizeof(lastcosts));           
            for (size_t j = 0; j < m_vec[i].size(); ++j)
            {
                if (j == 0)
                {
                    costs[j] = lastcosts[j] + m_vec[i][j];
                }
                else 
                {
                    costs[j] = max(lastcosts[j - 1], lastcosts[j]) + m_vec[i][j];
                }
            }
        }

        int ans = 0;
        for (size_t i = 0; i < m_vec.size(); ++i)
        {
            ans = max(ans, costs[i]);
        }

        return ans;
    }

private:
    enum {
        MAXN = 1001
    };

    int costs[MAXN];
    int lastcosts[MAXN];
    vector<vector<int>> m_vec;
};

class Solution
{
public:
    Solution(AlgoPolicy* algo)
    {
        sp.reset(algo);
    }

    int run()
    {
        return sp->execute();
    }

private:
    shared_ptr<AlgoPolicy> sp;
};

int main()
{
#ifndef ONLINE_JUDGE
    ifstream fin("numtri.in");
    ofstream fout("numtri.out");
    streambuf* cinback = cin.rdbuf(fin.rdbuf());
    streambuf* coutback = cout.rdbuf(fout.rdbuf());
#endif
    int n;
    cin >> n;
    vector<vector<int>> v(n);
    for (int i = 0; i < n; ++i)
    {
        for (int j = 0; j <= i; ++j)
        {
            int m;
            cin >> m;
            v[i].push_back(m);
        }
    }

    AlgoPolicy* algoPolicy = new IteratorAlgo(v);
    Solution solution(algoPolicy);
    int ans = solution.run();
    cout << ans << endl;
#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
    cout.rdbuf(coutback);
#endif

    return 0;
}
