/**
 * @class AlgoPolicy
 * @author wl
 * @date 08/12/2019
 * @file main.cpp
 * @brief 
 */
#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
#include <algorithm>
#include <memory>
#include <cstring>

using namespace std;

const int MAXN = 10001;

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

bool cmp(const Product& a, const Product& b)
{
    return a.profit > b.profit;
}

class GreedyAlgo : public AlgoPolicy<int>
{
public:
    GreedyAlgo(vector<Product>& products)
    {
        this->products = products;
        memset(next, -1, sizeof(next));
    }

   virtual int execute() override
   {
       sort(products.begin(), products.end(), cmp);
    
        int totalProfit = 0;
        for (size_t i = 0; i < products.size(); ++i)
        {
            int t = find(products[i].deadline);
            if (t)
            {
                totalProfit += products[i].profit;
                next[t] = t - 1;
            }
        }
        return totalProfit;
   }

private:
    int find(int x)
    {
        if (next[x] == -1)
        {
            return x;
        }
        
        next[x] = find(next[x]);
        
        return next[x];
    }
private:
    vector<Product> products;
    int next[MAXN];
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
