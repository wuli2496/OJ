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
        genOddEven(1, 9, ans);
        genOddEven(10, 99, ans);
        genOddEven(100, 999, ans);
        genOddEven(1000, 9999, ans);
        
        return ans;
    }

private:
    int genPanlindrome(int num, bool isOdd)
    {
        const int MAXN = 20;
        char buf[MAXN];
        
        sprintf(buf, "%d", num);
        char *p = buf + strlen(buf);
        char *q = p; 
        if (isOdd)
        {
            q -= 1;
        }

        while (q > buf)
        {
            *p++ = *--q;
        }

        *p = '\0';

        return atol(buf);
    }

    void genOddEven(int low, int high, vector<int>& v)
    {
        for (int i = low; i <= high; ++i)
        {
            int num = genPanlindrome(i, true);
            if (num >= m_a && num <= m_b)
            {
                if (isPrime(num))
                {
                    v.push_back(num);
                }
            }
        }

        for (int i = low; i <= high; ++i)
        {
            int num = genPanlindrome(i, false);
            if (num >= m_a && num <= m_b)
            {
                if (isPrime(num))
                {
                    v.push_back(num);
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
