#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <memory>
#include <queue>

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

    bool operator<(const Product& other) const
    {
        return deadline < other.deadline;
    }
};

class GreedyAlgo : public AlgoPolicy<int>
{
public:
    GreedyAlgo(vector<Product>& products)
    {
        this->products = products;
    }
    
   virtual int execute() override
   {
        sort(products.begin(), products.end());

        int totalProfit = 0;
        priority_queue<int, vector<int>, greater<int>> pq;
        for (size_t i = 0; i < products.size(); ++i)
        {
            if (products[i].deadline > pq.size())
            {
                pq.push(products[i].profit);
                totalProfit += products[i].profit;
            }
            else
            {
                if (products[i].profit > pq.top())
                {
                    totalProfit -= pq.top();
                    pq.pop();
                    pq.push(products[i].profit);
                    totalProfit += products[i].profit;
                }
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

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

#ifndef ONLINE_JUDGE
    ifstream fin("F:\\OJ\\uva_in.txt");
    streambuf* cinback = cin.rdbuf(fin.rdbuf());
#endif

    int n;
    while (cin >> n)
    {
        vector<Product> products(n);
        for (int i = 0; i < n; ++i)
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
}
