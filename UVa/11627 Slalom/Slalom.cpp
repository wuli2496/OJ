#include <iostream>
#include <fstream>
#include <memory>
#include <vector>
#include <algorithm>
#include <string>

using namespace std;

template <typename T>
void print(vector<T> c)
{
    for (size_t i = 0; i < c.size(); ++i)
    {
        cout << c[i];
        if (i + 1 != c.size())
        {
            cout << " ";
        }
    }

    cout << endl;
}

class AlgoPolicy
{
public:
    virtual ~AlgoPolicy() {}
    virtual string execute() = 0;
};
    
struct Point
{
    int x, y;
};

class BinaryAlgo : public AlgoPolicy
{
public:
    BinaryAlgo(int w, int vh, vector<Point> points, vector<int>& skis)
    {
        this->w = w;
        this->vh = vh;
        this->points = points;
        this->skis = skis;
    }

    virtual string execute() override
    {
       sort(skis.begin(), skis.end());
       int left = -1, right = static_cast<int>(skis.size() - 1);

       while (left < right)
       {
            int mid = (left + right + 1)/ 2;
            bool ok = check(mid);
            if (ok)
            {
                left = mid;
            }
            else 
            {
                right = mid - 1;                
            }
       }

       if (left == - 1)
       {
            return "IMPOSSIBLE";
       }

       return to_string(skis[left]);
    }

private:
    bool check(int m)
    {
        double x1 = points[0].x, x2 = x1 + w;
        int vv = skis[m];
        for (size_t i = 1; i < points.size(); ++i)
        {
            double d = (double)(points[i].y - points[i - 1].y) * vh / vv;
            x1 -= d; x2 += d;
            x1 = max(x1, (double)points[i].x);
            x2 = min(x2, (double)points[i].x + w);
            if (x1 > x2 + 1e-10)
            {
                return false;
            }
        }
        
        return true;
    }

private:
    int w;
    int vh;
    vector<int> skis;
    vector<Point> points;
};

AlgoPolicy* makeAlgo(const string& algoName, int w, int vh, vector<Point> points, vector<int>& skis)
{
    if (algoName == "binary")
    {
        return new BinaryAlgo(w, vh, points, skis);
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
    int testCase;
    cin >> testCase;
    while (testCase-- > 0)
    {
        int w, vh, n;
        cin >> w >> vh >> n;
        vector<Point> points(n);
        for (int i = 0; i < n; ++i)
        {
            cin >> points[i].x >> points[i].y;
        }

        int s;
        cin >> s;
        vector<int> skis(s);
        for (int i = 0; i < s; ++i)
        {
            cin >> skis[i];
        }

        AlgoPolicy* algo = makeAlgo("binary", w, vh, points, skis);
        Solution solution(algo);
        string ans = solution.run();
        cout << ans << endl;
    }

#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif
    return 0;
}
