#include <iostream>
#include <fstream>
#include <string>
#include <algorithm>
#include <memory>
#include <vector>

using namespace std;

class AlgoPolicy
{
public:
    virtual ~AlgoPolicy() {}
    virtual string execute() = 0;
};

bool cmp(string a, string b)
{
    string tmpa = a + b;
    string tmpb = b + a;

    return tmpa > tmpb;
}

class GreedyAlgo : public AlgoPolicy
{
public:
    GreedyAlgo(vector<string>& nums)
    {
        this->nums = nums;
    }

    virtual string execute() override
    {
        sort(nums.begin(), nums.end(), cmp);
        string ans;
        for (size_t i = 0; i < nums.size();  ++i)
        {
            ans += nums[i];
        }

        return ans;
    }

private:
    vector<string> nums;
};

AlgoPolicy* makeAlgo(const string& algoName, vector<string>& nums)
{
    if (algoName == "greedy")
    {
        return new GreedyAlgo(nums);
    }

    return nullptr;
}

class Solution
{
public:
    Solution(AlgoPolicy* algo)
    {
        pimpl.reset(algo);
    }

    string run()
    {
        return pimpl->execute();
    }

private:
    shared_ptr<AlgoPolicy> pimpl;
};

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

#ifndef ONLINE_JUDGE
    ifstream fin("/home/wl/OJ/OJ/UVa/uvain");
    streambuf* cinback = cin.rdbuf(fin.rdbuf());
#endif
    int n;
    while (cin >> n)
    {
        if (n == 0)
        {
            break;
        }

        vector<string> nums(n);
        for (int i = 0; i < n; ++i)
        {
            cin >> nums[i];
        }

        AlgoPolicy* algo = makeAlgo("greedy", nums);
        Solution solution(algo);
        string ans = solution.run();
        cout << ans << endl;
    }
#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif

}
