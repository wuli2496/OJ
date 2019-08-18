/*
ID:wuli2491
TASK:pprime
LANG:C++
*/
#include <iostream>
#include <fstream>
#include <vector>
#include <memory>
#include <algorithm>
#include <cstring>

using namespace std;

class AlgoPolicy
{
public:
    virtual ~AlgoPolicy() {}
    virtual vector<int> execute() = 0;
};

class BruteForceAlgo : public AlgoPolicy
{
public:
    BruteForceAlgo(int a, int b):
        m_a(a), m_b(b)
    {

    }

    virtual vector<int> execute()
    {
        vector<int> ans;
        if (m_a <= 11 && m_b >= 11)
        {
            ans.push_back(11);
        }
        gen(0, 999, ans);
        sort(ans.begin(), ans.end());

        return ans;
    }

private:
    int genPanlindrome(int num, int middle)
    {
        int save = num;
        int sum = 0;
        int exp = 1;

        for(; num > 0; num /= 10)
        {
            sum = sum * 10 + num % 10;
            exp *= 10;
        }

        return save * 10 * exp + middle * exp + sum;
    }

    void gen(int low, int high, vector<int>& v)
    {
        for (int i = low; i <= high; ++i)
        {
            for (int j = 0; j < 10; ++j)
            {
                int num = genPanlindrome(i, j);
                if (num >= m_a && num <= m_b)
                {
                    if (isPrime(num))
                    {
                        v.push_back(num);
                    }
                }
            }
        }

        return;
    }

    bool isPrime(int num)
    {
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

private:
    int m_a, m_b;
};

class Solution
{
public:
    Solution(AlgoPolicy* algo)
    {
        sp.reset(algo);
    }

    vector<int> run()
    {
        return sp->execute();
    }

private:
    shared_ptr<AlgoPolicy> sp;
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
    ifstream fin("pprime.in");
    ofstream fout("pprime.out");
    streambuf* cinback = cin.rdbuf(fin.rdbuf());
    streambuf* coutback = cout.rdbuf(fout.rdbuf());
#endif
    int a, b;

    cin >> a >> b;
    AlgoPolicy* policy = new BruteForceAlgo(a, b);
    Solution solution(policy);
    vector<int> ans = solution.run();
    for_each(ans.begin(), ans.end(), print);

#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
    cout.rdbuf(coutback);
#endif

    return 0;
}
