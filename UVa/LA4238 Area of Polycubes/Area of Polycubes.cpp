#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
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

struct Point 
{
    int x, y, z;
};

class AdcAlgo : public AlgoPolicy<string>
{
public:
    explicit AdcAlgo(vector<Point> ps)
    {
        points = ps;
    }

    virtual string execute() override
    {
        int area = N * static_cast<int>(points.size());
        ostringstream stream;
        for (size_t i = 1, len = points.size(); i < len; ++i)
        {
            bool flag = false;
            for (size_t j = 0; j < i; ++j)
            {
                int d = dis(points[i], points[j]);
                if (d == 1)
                {
                    area -= DIFF;
                    flag = true;
                }
                else if (d == 0)
                {
                    flag = false;
                    break;
                }
            }
            
            if (!flag)
            {
                stream << "NO " << i + 1;
                return stream.str();
            }
        }
        
        stream << area;
        return stream.str();
    }
    
private:
    int dis(const Point& a, const Point& b)
    {
        int dx = abs(a.x - b.x);
        int dy = abs(a.y - b.y);
        int dz = abs(a.z - b.z);
        
        return dx + dy + dz;
    }
    
private:
    enum {N = 6};
    enum {DIFF = 2};
    
    vector<Point> points;
};

AlgoPolicy<string>* makeAlgo(const string& name, vector<Point> ps)
{
    if (name == "adc")
    {
        return new AdcAlgo(ps);
    }

    return nullptr;
}

template <typename Result>
class Solution
{
public:
    Solution(AlgoPolicy<Result>* algo)
    {
        pimpl.reset(algo);
    }

    Result run()
    {
        return pimpl->execute();
    }

private:
    shared_ptr<AlgoPolicy<Result>> pimpl;
};

Point convertToPoint(const string& s)
{
    stringstream stream(s);
    string tmp;
    getline(stream, tmp, ',');
    Point ans;
    ans.x = atoi(tmp.c_str());
    getline(stream, tmp, ',');
    ans.y = atoi(tmp.c_str());
    getline(stream, tmp, ',');
    ans.z = atoi(tmp.c_str());
    
    return ans;
}

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
        int p;
        cin >> p;
        vector<Point> points(p);
        string s;
        for (int j = 0; j < p; ++j)
        {
            cin >> s;
            points[j] = convertToPoint(s);
        }
        
        AlgoPolicy<string>* algo = makeAlgo("adc", points);
        Solution<string> solution(algo);
        string ans = solution.run();
        
        cout << i + 1 << " " << ans << endl;
    }
    
#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif

	return 0;
}
