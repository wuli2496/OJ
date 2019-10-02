#include <cstdlib>
#include <iostream>
#include <fstream>
#include <vector>
#include <memory>
#include <queue>
#include <cmath>
#include <algorithm>

using namespace std;

struct Task
{
    int r, d;
    int w;
    
    Task()
    {
        r = 0;
        d = 0;
        w = 0;
    }

    bool operator<(const Task& other) const
    {
        return d > other.d;
    }
};

bool cmp(const Task& a, const Task& b)
{
    if (a.r != b.r)
    {
        return a.r < b.r;
    }

    return a.d < b.d;
}

void print(const string& desc, const Task& task)
{
    cout << desc<< " r:" << task.r << " d:" << task.d << " w:" << task.w << endl;
}

class AlgoPolicy
{
public:
    virtual ~AlgoPolicy() {}
    virtual int execute() = 0;
};

class BinaryAlgo : public AlgoPolicy
{
public:
    BinaryAlgo(vector<Task>& tasks)
    {
        this->tasks = tasks;
        maxd = 0;
    }
    
    virtual int execute() override
    {
       int low = 1;
       int high = 1;
       
       sort(tasks.begin(), tasks.end(), cmp);
       for (auto &task: tasks)
       {
            high += task.w;
            maxd = max(maxd, task.d);     
       }
       
       while (low < high)
       {    
           int m = low + (high - low) / 2;
            
           if (!check(m))
           {
               low = m + 1;
           }
           else 
           {
               high = m;
           }

       }

       return low;
    }

private:
    bool check(int m)
    {
        priority_queue<Task> pq;
        int taskLen = tasks.size();
        int taskIndex = 0;
        for (int i = 1; i < maxd + 1; ++i)
        {
            while (taskIndex < taskLen && tasks[taskIndex].r < i)
            {
                pq.push(tasks[taskIndex]);
                ++taskIndex;
            }
            
            int sum = m;
            while (sum && !pq.empty())
            {
                Task t = pq.top(); pq.pop();
                if (t.d < i)
                {
                    return false;
                }

                if (t.w > sum)
                {
                    t.w -= sum;
                    sum = 0;
                    pq.push(t);
                }
                else 
                {
                    sum -= t.w;
                }

                if (taskIndex == taskLen && pq.empty())
                {
                    return true;
                }
            }
        }
        
        return false;
    }
private:
    vector<Task> tasks;
    int maxd;
};

AlgoPolicy* makeAlgo(const string& algoName, vector<Task>& tasks)
{
    if (algoName == "binary")
    {
        return new BinaryAlgo(tasks);
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
    
    int run()
    {
        return pimpl->execute();
    }
private:
    shared_ptr<AlgoPolicy> pimpl;
};

int main(int argc, char** argv) 
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
#ifndef ONLINE_JUDGE
    ifstream fin("/home/wl/OJ/OJ/UVa/uvain");
    streambuf* cinback = cin.rdbuf(fin.rdbuf());
#endif

    int t;
    cin >> t;
    while (t-- > 0)
    {
        int n;
        cin >> n;
        vector<Task> tasks(n);
        for (int i = 0; i < n; ++i)
        {
            cin >> tasks[i].r >> tasks[i].d >> tasks[i].w;
        }

        AlgoPolicy* algo = makeAlgo("binary", tasks);
        Solution solution(algo);
        int ans = solution.run();
        cout << ans << endl;
    }
#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif
    return 0;
}

