#include <iostream>
#include <fstream>
#include <vector>
#include <memory>
#include <algorithm>

using namespace std;

template<typename Result>
class AlgoPolicy
{
public:
    ~AlgoPolicy() {}
    virtual Result execute() = 0;
};

class GreedyAlgo : public AlgoPolicy<int>
{
public:
    GreedyAlgo(vector<int>& horsesTian, vector<int>& horsesQi)
    {
        this->horsesTian = horsesTian;
        this->horsesQi = horsesQi;
    }

    virtual int execute() override
    {
        sort(horsesTian.begin(), horsesTian.end());
        sort(horsesQi.begin(), horsesQi.end());

        int ans = 0;
        int len = horsesTian.size();
        int tianMin = 0, tianMax = len - 1;
        int qiMin = 0, qiMax = len - 1;
        while (tianMin <= tianMax)
        {
            if (horsesTian[tianMax] > horsesQi[qiMax])
            {
                --tianMax;
                --qiMax;
                ++ans;
            }
            else if (horsesTian[tianMin] > horsesQi[qiMin])
            {
                ++tianMin;
                ++qiMin;
                ++ans;
            }
            else 
            {
                if (horsesTian[tianMin] < horsesQi[qiMax]) 
                {
                    --ans;
                }

                ++tianMin;
                --qiMax;
            }   
        }
        return ans * 200;
    }

private:
    vector<int> horsesTian;
    vector<int> horsesQi;
};

AlgoPolicy<int>* makeAlgo(const string& algoName, vector<int>& horsesTian, vector<int>& horsesQi)
{
    if (algoName == "greedy")
    {
        return new GreedyAlgo(horsesTian, horsesQi);
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
    int n;
    while (cin >> n)
    {
        if (n == 0)
        {
            break;
        }

        vector<int> horsesTian(n), horsesQi(n);
        for (int i = 0; i < n; ++i)
        {
            cin >> horsesTian[i];
        }

        for (int i = 0; i < n; ++i)
        {
            cin >> horsesQi[i];
        }

        AlgoPolicy<int> *algo = makeAlgo("greedy", horsesTian, horsesQi);
        Solution solution(algo);
        int ans = solution.run();

        cout << ans << endl;
    }
#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif
}
