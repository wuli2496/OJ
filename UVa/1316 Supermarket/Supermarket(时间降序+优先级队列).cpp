/**
 * @brief  基于时间从大到小+优先级队列
 */
#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
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

struct Product
{
    int profit;
    int deadline;
};

bool cmpDeadline(const Product& a, const Product& b)
{
    return a.deadline > b.deadline;
}

class GreedyAlgo : public AlgoPolicy<int>
{
public:
    GreedyAlgo(vector<Product>& products)
    {
        this->products = products;
    }

   virtual int execute() override
   {
       sort(products.begin(), products.end(), cmpDeadline);

    int maxDeadline = 0;
    for (size_t i = 0; i < products.size(); ++i)
    {
        if (products[i].deadline > maxDeadline)
        {
            maxDeadline = products[i].deadline;
        }
    }

    int totalProfit = 0;
    priority_queue<int> pq;
    size_t j = 0;
    size_t n = products.size();
    for (int deadline = maxDeadline; deadline >= 1; --deadline)
    {
        while (j < n && products[j].deadline >= deadline)
        {
            pq.push(products[j].profit);
            ++j;
        }

        if (!pq.empty())
        {
            totalProfit += pq.top();
            pq.pop();
        }
    }

    return totalProfit;
   }

private:
    vector<Product> products;
};

AlgoPolicy<int>* makeAlgo(const string& name, vector<Product>& product)
{
    if (name == "greedy")
    {
        return new GreedyAlgo(product);
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

int main(int argc, char **argv)
{
	ios::sync_with_stdio(false);
    cin.tie(nullptr);

#ifndef ONLINE_JUDGE
    ifstream fin("F:\\OJ\\uva_in.txt");
    streambuf* cinback = cin.rdbuf(fin.rdbuf());
#endif

    size_t n;
    while (cin >> n)
    {
        vector<Product> products(n);
        for (size_t i = 0; i < n; ++i)
        {
            cin >> products[i].profit >> products[i].deadline;
        }

        AlgoPolicy<int>* algo = makeAlgo("greedy", products);
        Solution solution(algo);
        int ans = solution.run();
        cout << ans << endl;
    }
#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif

	return 0;
}
