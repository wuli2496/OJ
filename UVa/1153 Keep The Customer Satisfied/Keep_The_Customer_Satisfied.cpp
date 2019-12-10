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

struct Order
{
    int quantity;
    int dueDate;

    bool operator<(const Order& other) const
    {
        return dueDate < other.dueDate;
    }
};

class GreedyAlgo : public AlgoPolicy<int>
{
public:
    explicit GreedyAlgo(vector<Order>& Orders) : orders(Orders)
    {
    }

    virtual int execute() override
    {
        sort(orders.begin(), orders.end());

        int curDate = 0;
        priority_queue<int> pq;
        for (size_t i = 0; i < orders.size(); ++i)
        {
            if (curDate + orders[i].quantity <= orders[i].dueDate)
            {
                pq.push(orders[i].quantity);
                curDate += orders[i].quantity;
            }
            else
            {
                if (pq.empty())
                {
                    continue;
                }

                int maxQuantity = pq.top();
                if (orders[i].quantity < maxQuantity)
                {
                    curDate = curDate - maxQuantity + orders[i].quantity;
                    pq.pop();
                    pq.push(orders[i].quantity);
                }
            }
        }

        return static_cast<int>(pq.size());
    }

private:
    vector<Order> orders;
};

AlgoPolicy<int>* makeAlgo(const string& name, vector<Order>& orders)
{
    if (name == "greedy")
    {
        return new GreedyAlgo(orders);
    }

    return nullptr;
}

class Solution
{
public:
    explicit Solution(AlgoPolicy<int>* algo)
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

    int testCase;
    cin >> testCase;
    for (int i = 0; i < testCase; ++i)
    {
        size_t n;
        cin >> n;
        vector<Order> orders(n);
        for (size_t i = 0; i < n; ++i)
        {
            cin >> orders[i].quantity >> orders[i].dueDate;
        }
        AlgoPolicy<int>* algo = makeAlgo("greedy", orders);
        Solution solution(algo);
        int ans = solution.run();
        cout << ans << endl;
        if (i < testCase - 1)
        {
            cout << endl;
        }
    }
    
#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif

	return 0;
}
