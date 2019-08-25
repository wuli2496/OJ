/*
ID:wuli2491
TASK:sprime
LANG:C++
*/

#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <memory>

using namespace std;

class MathUtil
{
public:
    static bool isPrime(int num)
    {
       if (num == 2)
       {
           return true;
       }

       if (num % 2 == 0)
       {
           return false;
       }

       for (int i = 3; i * i <= num; i += 2)
       {
           if (num % i == 0)
           {
               return false;
           }
       }

       return true;
    }
};

class AlgoPocily
{
public:
    virtual ~AlgoPocily() {};
    virtual  void execute(vector<int>& ans) = 0;
};

class DfsAlgo : public AlgoPocily
{
public:
    DfsAlgo(int n)
    {
        m_len = n;
    }

    void execute(vector<int>& ans) override
    {
        dfs(2, m_len - 1, ans);
        dfs(3, m_len - 1, ans);
        dfs(5, m_len - 1, ans);
        dfs(7, m_len - 1, ans);
    }

private:
    void dfs(int n, int depth, vector<int>& ans)
    {
        if (depth == 0)
        {
            ans.push_back(n);
            return;
        }

        n *= 10;
        if (MathUtil::isPrime(n + 1))
        {
            dfs(n + 1, depth - 1, ans);
        }

        if (MathUtil::isPrime(n + 3))
        {
            dfs(n + 3, depth - 1, ans);
        }

        if (MathUtil::isPrime(n + 7))
        {
            dfs(n + 7, depth - 1, ans);
        }

        if (MathUtil::isPrime(n + 9))
        {
            dfs(n + 9, depth - 1, ans);
        }
    }
private:
    int m_len;
};

class Solution
{
public:
    Solution(AlgoPocily* policy)
    {
        pimpl.reset(policy);
    }

    void run(vector<int>& v)
    {
        pimpl->execute(v);
    }
private:
    shared_ptr<AlgoPocily> pimpl;
};

void print(int num)
{
    cout << num << endl;
}

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
#ifndef ONLINE_JUDGE
    ifstream fin("sprime.in");
    ofstream fout("sprime.out");
    streambuf* cinback = cin.rdbuf(fin.rdbuf());
    streambuf* coutback = cout.rdbuf(fout.rdbuf());
#endif

    int n;
    cin >> n;
    AlgoPocily* algoPolicy = new DfsAlgo(n);
    vector<int> v;
    Solution solution(algoPolicy);
    solution.run(v);
    for_each(v.begin(), v.end(), print);

#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
    cout.rdbuf(coutback);
#endif
    return 0;
}
