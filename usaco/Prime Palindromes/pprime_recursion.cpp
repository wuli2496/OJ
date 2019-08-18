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

        if (m_a <= 5)
        {
            ans.push_back(5);
        }

        if (m_a <= 7 && m_b >= 7)
        {
            ans.push_back(7);
        }

        if (m_a <= 11 && m_b >= 11)
        {
            ans.push_back(11);
        }

        genPalind(3, 0, 100, 1, ans);
        genPalind(5, 0, 10000, 1, ans);
        genPalind(7, 0, 1000000, 1, ans);

        return ans;
    }

private:
    void tryPalind(int num, vector<int>& v)
    {
        if (num < m_a || num > m_b)
        {
            return;
        }

        if (isPrime(num))
        {
            v.push_back(num);
        }
    }

    void genPalind(int n, int sum, int left, int right, vector<int>& v)
    {
        if (n == 2)
        {
            for (int i = 0; i < 10; ++i)
            {
                int num = sum + left * i + right * i;
                tryPalind(num, v);
            }

            return;
        }

        if (n == 1)
        {
            for (int i = 0; i < 10; ++i)
            {
                int num = sum + right * i;
                tryPalind(num, v);
            }

            return;
        }

        int nleft = left / 10;
        int nright = right * 10;
        n -= 2;
        for (int i = 0; i < 10; ++i)
        {
            genPalind(n, sum + i * left + i * right, nleft, nright, v);
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
