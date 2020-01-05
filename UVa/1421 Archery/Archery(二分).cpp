#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <memory>
#include <cmath>

using namespace std;

template<typename Result>
class AlgoPolicy
{
public:
    virtual ~AlgoPolicy() {}
    virtual Result execute() = 0;
};

template<typename Result>
class InputPolicy
{
public:
    virtual ~InputPolicy() {}
    
    virtual bool hasNext() = 0;
    
    virtual Result next() = 0;
};

class OutputPolicy
{
public:
    virtual ~OutputPolicy() {}
    
    virtual void write() = 0;
};

struct Line 
{
    int d;
    int l, r;
};

struct Data
{
    int w;
    vector<Line> lines;
};

bool cmp(const Line& a, const Line& b)
{
    return a.d < b.d;
}

class BinaryAlgo : public AlgoPolicy<string>
{
public:
    BinaryAlgo(const Data& data)
    {
        this->data = data;
    }
    
    string execute() override
    {
        sort(data.lines.begin(), data.lines.end(), cmp);
        
        double l = 0, r = data.w;
        while (r - l > EPS)
        {
            double mid = (l + r) / 2;
            int flag = check(mid);
            if (flag == 0)
            {
                return "YES";
            }
            else if (flag == -1)
            {
                r = mid;
            }
            else 
            {
                l = mid;
            }
        }
        
        return "NO";
    }
    
private:
    int check(double x)
    {
        double L = atan2(data.lines[0].d, data.lines[0].r - x);
        double R = atan2(data.lines[0].d, data.lines[0].l - x);
        
        for (size_t i = 1; i < data.lines.size(); ++i)
        {
            double l = atan2(data.lines[i].d, data.lines[i].r - x);
            double r = atan2(data.lines[i].d, data.lines[i].l - x);
            
            if (r + EPS < L)
            {
                return -1;
            }
            
            if (R + EPS < l)
            {
                return 1;
            }
            
            L = max(L, l);
            R = min(R, r);
        }
        
        return 0;
    }
private:
    Data data;
    const double EPS = 1e-8;
};

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

class LimitInput : public InputPolicy<Data>
{
public:
    LimitInput(istream& in) : instream(in)
    {
        instream >> testCases;
    }
    
    bool hasNext() override
    {
        return testCases > 0;
    }
    
    Data next() override
    {
        --testCases;
        Data data;
        instream >> data.w;
        int n;
        instream >> n;
        for (int i = 0; i < n; ++i)
        {
            Line line;
            instream >> line.d >> line.l >> line.r;
            data.lines.push_back(line);
        }
        
        return data;
    }
    
private:
    istream& instream;
    int testCases;
};

class Output : public OutputPolicy
{
public:
    Output(ostream& o, string& s): out(o), str(s)
    {
        
    }
    
    void write() override
    {
        out << str << endl;
    }
private:
    ostream& out;
    string& str;
};

int main(int argc, char **argv)
{
	ios::sync_with_stdio(false);
    cin.tie(nullptr);

#ifndef ONLINE_JUDGE
    ifstream fin("F:\\OJ\\uva_in.txt");
    streambuf* cinback = cin.rdbuf(fin.rdbuf());
#endif
    
    shared_ptr<InputPolicy<Data>> in(new LimitInput(cin));
    while (in.get() != nullptr && in->hasNext())
    {
        Data data = in->next();
        AlgoPolicy<string>* algo = new BinaryAlgo(data);
        Solution<string> solution(algo);
        string s = solution.run();
        shared_ptr<OutputPolicy> output(new Output(cout, s));
        output->write();
    }
  
#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif

	return 0;
}
