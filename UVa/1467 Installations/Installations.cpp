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
    ~AlgoPolicy() {}
    virtual Result execute() = 0;
};

struct Job
{
    int serveTime;
    int deadline;

    Job()
    {
        serveTime = 0;
        deadline = 0;
    }

    bool operator<(const Job& other) const
    {
        if (deadline != other.deadline)
        {
            return deadline < other.deadline;
        }

        return serveTime < other.serveTime;
    }
};

class GreedyAlgo : public AlgoPolicy<int>
{
public:
    GreedyAlgo(vector<Job> jobs)
    {
        this->jobs = jobs;
    }

    virtual int execute() override
    {
        sort(jobs.begin(), jobs.end());

        size_t len = jobs.size();
        vector<int> sums(len);
        sums[0] = jobs[0].serveTime;
        for (size_t i = 1; i < len; ++i)
        {
            sums[i] = sums[i - 1] + jobs[i].serveTime;
        }

        int maximum = 0, secondMax = 0;
        int posMax = 0, posSencodMax = 0;
        for (size_t i = 0; i < len; ++i)
        {
            int penaltyVal = sums[i] - jobs[i].deadline;
            if (penaltyVal > maximum)
            {
                secondMax = maximum;
                maximum = penaltyVal;
                posSencodMax = posMax;
                posMax = i;
            }
            else if (penaltyVal > secondMax)
            {
                secondMax = penaltyVal;
                posSencodMax = i;
            }
        }

        int ans = maximum + secondMax;
        int pos1 = min(posMax, posSencodMax);
        int pos2 = max(posMax, posSencodMax);
        int s = sums[pos2];
        for (int i = 0; i <= pos1; ++i)
        {
            maximum = 0;
            secondMax = 0;
            int sum = 0;
            for (int j = 0; j < len; ++j)
            {
                if (j < i)
                {
                    sum = sums[j];
                }
                else if (j == i)
                {
                    sum = s; 
                }
                else if (j <= pos2)
                {
                    sum = sums[j] - jobs[i].serveTime;
                } 
                else 
                {
                    sum = sums[j];
                }
                
                sum -= jobs[j].deadline;
                if (sum > maximum)
                {
                    secondMax = maximum;
                    maximum = sum;
                }
                else if (sum > secondMax)
                {
                    secondMax = sum;
                }    
            }
            ans = min(ans, maximum + secondMax);
        }

        return ans;
    }

private:
    vector<Job> jobs;
};

AlgoPolicy<int>* makeAlgo(const string& algoName, vector<Job>& jobs)
{
    if (algoName == "greedy")
    {
        return new GreedyAlgo(jobs);
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
        vector<Job> jobs(n);
        for (int i = 0; i < n; ++i)
        {
            cin >> jobs[i].serveTime >> jobs[i].deadline;
        }

        AlgoPolicy<int>* algo = makeAlgo("greedy", jobs);
        Solution solution(algo);
        int ans = solution.run();
        cout << ans << endl;
    }
#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif
}
