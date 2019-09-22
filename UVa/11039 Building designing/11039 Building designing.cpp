#include <iostream>
#include <vector>
#include <algorithm>
#include <memory>
#include <fstream>

using namespace std;

template<typename T>
void print(T t)
{
    cout << t << endl;
}

template<typename T>
class AlgoPolicy
{
public:
    virtual ~AlgoPolicy() {}
    virtual T execute() = 0;
};

class GreedyAlgo : public AlgoPolicy<int>
{
public:
    GreedyAlgo(vector<int>& v)
    {
        vec = v;
    }

    virtual int execute() override
    {
        return solve();
    }

private:
    int solve()
    {
        if (vec.size() == 0)
        {
            return 0;
        }

        if (vec.size() == 1)
        {
            return 1;
        }

        sort(vec.begin(), vec.end());
        int left = 0, right = vec.size() - 1;
        if (getSign(vec[left]) * getSign(vec[right]) > 0)
        {
            return 1;
        }
        
        bool leftStatus = false;
        initStatus(left, right, leftStatus);
        
        int maxVal = 0;
        if (leftStatus)
        {
            maxVal = abs(vec[left]);
        }
        else 
        {
            maxVal = vec[right];
        }
        int ans = 1;
        for (; left < right; )
        {
            if (leftStatus)
            {
                rightMax(maxVal, left, right, ans);
            }
            else 
            {
                leftMax(maxVal, left, right, ans);
            }

            leftStatus = !leftStatus;
        }

        return ans;
    }

    void leftMax(int& maxVal, int& left, int right, int& ans)
    {
        while (left < right)
        {
            if (vec[left] > 0)
            {
                left = right;
                return;
            }

            if (abs(vec[left]) >= maxVal)
            {
                ++left;
            }
            else 
            {
                maxVal = abs(vec[left]);
                ++ans;
                return;
            }
        }

        return;
    }

    void rightMax(int& maxVal, int left, int &right, int& ans)
    {
        while (left < right)
        {
            if (vec[right] < 0)
            {
                right = left;
                return;
            }

            if (abs(vec[right]) >= maxVal)
            {
                --right;
            }
            else 
            {
                maxVal = abs(vec[right]);
                ++ans;
                return;
            }
        }

        return;
    }

    void initStatus(int left, int right, bool& leftStart)
    {
        int absLeft = abs(vec[left]);
        int absRight = abs(vec[right]);

        if (absLeft >= absRight)
        {
            leftStart = true;
        }
        else 
        {
            leftStart = false;
        }

        return;


    }

    int getSign(int t)
    {
        if (t < 0)
        {
            return -1;
        }
        else if (t > 0)
        {
            return 1;
        }

        return 0;
    }

private:
    vector<int> vec;
};

class Solution
{
public:
    Solution(AlgoPolicy<int>* p)
    {
        sp.reset(p);
    }

    int run()
    {
        return sp->execute();
    }
private:
    shared_ptr<AlgoPolicy<int>> sp;
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
        vector<int> v;
        for (int i = 0; i < n; ++i)
        {
            int value;
            cin >> value;
            v.push_back(value);
        }

        AlgoPolicy<int> *p = new GreedyAlgo(v);
        Solution solution(p);
        int ans = solution.run();
        cout << ans << endl;
    }

#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif

    return 0;
}
