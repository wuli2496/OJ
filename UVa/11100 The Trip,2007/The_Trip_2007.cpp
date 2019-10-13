#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <map>
#include <memory>
#include <string>

using namespace std;

template<typename Result>
class AlgoPolicy
{
public:
    ~AlgoPolicy() {}
    virtual Result execute() = 0;
};

class GreedyAlgo  : public AlgoPolicy<void>
{
public:
    GreedyAlgo(vector<int>& bags)
    {
        this->bags = bags;
    }

    virtual void execute() override
    {
        sort(bags.begin(), bags.end());
        map<int, int> cnt;

        for (auto &bag : bags)
        {
            ++cnt[bag];
        }
    
        int maxVal = 0;
        for (auto &entry : cnt)
        {
            maxVal = max(maxVal, entry.second);
        }

        cout << maxVal << endl;
        for (int i = 0; i < maxVal; ++i)
        {
            bool first = true;
            for (int j = i; j < static_cast<int>(bags.size()); j += maxVal)
            {
                if (first)
                {
                    first = false;
                }
                else 
                {
                    cout << " ";
                }
                cout << bags[j];
            }

            cout << endl;
        }
    }
private:
    vector<int> bags;
};

AlgoPolicy<void>* makeAlgo(const string& algoName, vector<int>& bags)
{
    if (algoName == "greedy")
    {
        return new GreedyAlgo(bags);
    }

    return nullptr;
}

class Solution
{
public:
    Solution(AlgoPolicy<void>* algo)
    {
        pimpl.reset(algo);
    }

    void run()
    {
        pimpl->execute();
    }

private:
    shared_ptr<AlgoPolicy<void>> pimpl;
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

        vector<int> bags(n);

        for (int i = 0; i < n; ++i)
        {
            cin >> bags[i];
        }

        AlgoPolicy<void>* algo = makeAlgo("greedy", bags);
        Solution solution(algo);
        solution.run();
    }
#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif
}
