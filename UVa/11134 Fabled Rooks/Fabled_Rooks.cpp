#include <iostream>
#include <fstream>
#include <vector>
#include <memory>
#include <algorithm>

using namespace std;

class AlgoPolicy
{
public:
    virtual ~AlgoPolicy() {}
    virtual void execute() = 0;
};

struct Rook
{
    int l, r;
    int pos;

    bool operator<(const Rook& other) const
    {
        return r < other.r;
    }
};

void print(Rook& rook)
{
    cout << rook.l << " " << rook.r << " " << rook.pos << endl;
}

class GreedyAlgo : public AlgoPolicy
{
public:
    GreedyAlgo(vector<Rook>& x, vector<Rook>& y)
    {
        this->x = x;
        this->y = y;
    }

    virtual void execute() override
    {
        sort(x.begin(), x.end());
        vector<bool> vis(x.size());
        fill(vis.begin(), vis.end(), false);
        
        size_t n = x.size();
        vector<int> placex(n), placey(n);
        bool found = false;
        
        place(placex, found, x, vis);
        if (!found)
        {
            cout << "IMPOSSIBLE" << endl;
            return;
        }

        sort(y.begin(), y.end());
        fill(vis.begin(), vis.end(), false);
        place(placey, found, y, vis);
        if (!found)
        {
            cout << "IMPOSSIBLE" << endl;
            return;
        }

        for (size_t i = 0; i < n; ++i)
        {
            cout << placex[i] << " " << placey[i] << endl;
        }
    }
private:
    void place(vector<int>& pos, bool& found, vector<Rook>& rooks, vector<bool>& vis)
    {
        for (size_t i = 0; i < pos.size(); ++i)
        {
            found = false;
            for (int j = rooks[i].l; j <= rooks[i].r; ++j)
            {
                if (vis[j - 1] == true)
                {
                    continue;
                }
                vis[j - 1] = true;
                pos[rooks[i].pos] = j;
                found = true;
                break;
            }

            if (!found)
            {
                break;
            }
        }
    }
private:
    vector<Rook> x, y;
};

AlgoPolicy* makeAlgo(const string& algoName, vector<Rook>& x, vector<Rook>& y)
{
    if (algoName == "greedy")
    {
        return new GreedyAlgo(x, y);
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

    void run()
    {
        pimpl->execute();
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

        vector<Rook> x(n), y(n);
        for (int i = 0; i < n; ++i)
        {
            cin >> x[i].l >> y[i].l >> x[i].r >> y[i].r;
            x[i].pos = y[i].pos = i;
        }

        AlgoPolicy* algo = makeAlgo("greedy", x, y);
        Solution solution(algo);
        solution.run();
    }
#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif

    return 0;
}
