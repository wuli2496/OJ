#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <memory>

using namespace std;

template<typename Result>
class AlgoPolicy
{
public:
    virtual ~AlgoPolicy() {}
    virtual Result execute() = 0;
};

class BinaryBaseAlgo : public AlgoPolicy<int>
{
public:
    virtual ~BinaryBaseAlgo() {}
    virtual int getLeft() = 0;
    virtual int getRight() = 0;
    virtual bool check(int mid) = 0;

    virtual int execute() override
    {
        int l = getLeft();
        int r = getRight();

        while (l < r)
        {
            int mid = l + (r - l) / 2;
            if (check(mid))
            {
               r = mid; 
            }
            else 
            {
                l = mid + 1;
            }
        }   

        return l;
    }
};

class BinaryAlgo : public BinaryBaseAlgo
{
public:
    BinaryAlgo(vector<int>& w, vector<int>& e)
    {
        this->w = w;
        this->e = e;
    }

    virtual int getLeft() override
    {
        return 0;
    }

    virtual int getRight() override
    {
        int wsum = 0, esum = 0;
        size_t n = w.size();
        for (size_t i = 0; i < n; ++i)
        {
            wsum += w[i];
            esum += e[i];
        }

        return max(wsum, esum) + 1;
    }

    virtual bool check(int mid)
    {
        mid = mid + 1;
        int sum = 0, wsum = 0, esum = 0;
        size_t n = w.size();
        int wTotal = 0, eTotal = 0;
        int x = 0, y = 0;
        for (size_t i = 0; i < n; ++i)
        {
            wTotal += w[i];
            eTotal += e[i];
            x = max(wTotal - mid, 0);
            y = max(eTotal - mid, 0);
            if (x + y > sum || x > wsum || y > esum) 
            {
                return false;
            }

            if (wTotal == 0 && eTotal > 0) 
            {
                --eTotal;
            }
            else if (wTotal > 0 && eTotal == 0)
            {
                --wTotal;
            }
            else if (wTotal > 0 && eTotal > 0)
            {
               if (wTotal + eTotal > sum) 
               {
                    ++sum;
               }

               if (wTotal > wsum)
               {
                    ++wsum;
               }

               if (eTotal > esum)
               {
                    ++esum;
               }
            }
        }

        return true;
   
    }
private:
    vector<int> w, e;
};

AlgoPolicy<int>* makeAlgo(const string& algoName, vector<int>& w, vector<int>& e)
{
    if (algoName == "binary")
    {
        return new BinaryAlgo(w, e);
    }

    return nullptr;
}

class Solution
{
public:
    Solution(AlgoPolicy<int>* algo)
    {
        pimpl.reset(algo);
    }

    int run()
    {
        return pimpl->execute();
    }

private:
    shared_ptr<AlgoPolicy<int>> pimpl;
};

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

#ifndef ONLINE_JUDGE
    ifstream fin("/home/wl/OJ/OJ/UVa/uvain");
    streambuf* cinback = cin.rdbuf(fin.rdbuf());
#endif
    int testCase;
    cin >> testCase;
    while (testCase--)
    {
        int n;
        cin >> n;
        vector<int> w(n), e(n);
        for (int i = 0; i < n; ++i)
        {
            cin >> w[i] >> e[i];
        }

       AlgoPolicy<int>* algo = makeAlgo("binary", w, e);
       Solution solution(algo);
       int ans = solution.run();
       cout << ans << endl;
    }

#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif

    return 0;
}
