/*
ID:wuli2491
TASK:ariprog
LANG:C++
*/
#include <iostream>
#include <fstream>
#include <set>
#include <vector>
#include <algorithm>
#include <memory>

using namespace std;

const int MAXN = 125001;

class AlgoPolicy
{
public:
    virtual ~AlgoPolicy() {}
    virtual void execute() = 0;
};

class BruteForceAlgo : public AlgoPolicy
{
public:
    BruteForceAlgo(int n, int m)
    {
        this->n = n;
        this->m = m;
    }

    void init()
    {
        fill(m_nums, m_nums + MAXN, 0);
        m_numsLen = 0;
        fill(m_places, m_places + MAXN, false);

        for (int i = 0; i <= m; ++i)
        {
            for (int j = 0; j <= m; ++j)
            {
                int tmp = i * i + j * j;
                if (!m_places[tmp])
                {
                    m_places[tmp] = true;
                    m_nums[m_numsLen++] = tmp;
                }
            }
        }
    }

    virtual void execute()
    {
        init();
        sort(m_nums, m_nums + m_numsLen);

        int maxNum = 2 * m * m;
        int maxStep = maxNum / (n - 1);
        
        bool hasAns = false;

        for (int step = 1; step <= maxStep; ++step)
        {
            for (int i = 0; i < m_numsLen && m_nums[i] + (n - 2) * step <= maxNum; ++i)
            {
                bool ok = true;
                for (int j = n - 1; j >= 0; --j)
                {
                    int tmp = m_nums[i] + j * step;
                    if (!m_places[tmp])
                    {
                        ok = false;
                        break;
                    }
                }

                if (ok)
                {
                    hasAns = true;
                    cout << m_nums[i] << " " << step << endl;
                }
            }
        }

        if (!hasAns)
        {
            cout << "NONE" << endl;
        }
        
    }

private:
    int n, m;
    int m_nums[MAXN];
    bool m_places[MAXN];
    int m_numsLen;
};

int main()
{
#ifndef ONLINE_JUDGE
    ifstream fin("ariprog.in");
    ofstream fout("ariprog.out");
    streambuf* finback = cin.rdbuf(fin.rdbuf());
    streambuf* foutback = cout.rdbuf(fout.rdbuf());
#endif
    int n, m;
    cin >> n >> m;
    
    shared_ptr<AlgoPolicy> sp(new BruteForceAlgo(n, m));
    sp->execute();

#ifndef ONLINE_JUDGE
    cin.rdbuf(finback);
    cout.rdbuf(foutback);
#endif

    return 0;
}
