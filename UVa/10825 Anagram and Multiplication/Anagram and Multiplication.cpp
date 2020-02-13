#include <iostream>
#include <fstream>
#include <memory>
#include <string>
#include <algorithm>
#include <cstring>
#include <sstream>

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

struct Data
{
    int m, n;
};

class DfsAlgo : public AlgoPolicy<string>
{
public:
    DfsAlgo(const Data& data)
    {
        this->data = data;
    }
    
    string execute() override
    {        
        bool flag = false;
        for (int i = 1; i < data.n; ++i)
        {
            if (check(i))
            {
                flag = true;
                break;
            }
        }
        
        if (flag)
        {
            return convertToStr();
        }
        else 
        {
            return "Not found.";
        }
    }
    
private:
    bool check(int x)
    {
        int t = 0;
        int m = data.m;
        int n = data.n;
        for (int i = 0; i < m; ++i)
        {
            t = (t + x) % n;
            nums[i] = t;
            pos[i] = i;
        }
        
        return dfs(0);
    }
    
    bool dfs(int dep)
    {
        if (dep >= data.m)
        {
            return cal();
        }
        
        for (int i = dep; i < data.m; ++i)
        {
            swap(pos[dep], pos[i]);
            if (dfs(dep + 1))
            {
                return true;
            }
            
            swap(pos[dep], pos[i]);
        }
        
        return false;
    }
    
    bool cal()
    {
        memset(ans, 0x00, sizeof(ans));
        for (int i = 0; i < data.m; ++i)
        {
            ans[i] = nums[pos[i]];
        }
        ans[MAXM] = 0;
        
        int tmp[MAXM + 1], tmp2[MAXM + 1];
        for (int i = 2; i <= data.m; ++i)
        {
            memset(tmp, 0x00, sizeof(tmp));
            
            for (int j = 0; j < data.m; ++j)
            {
                tmp[j + 1] = (ans[j] * i + tmp[j]) / data.n;
                tmp[j] = (ans[j] * i + tmp[j]) % data.n;
            }
            
            if (tmp[data.m])
            {
                return false;
            }
            
            memcpy(tmp2, ans, sizeof(ans));
            
            sort(tmp2, tmp2 + MAXM + 1);
            sort(tmp, tmp + MAXM + 1);
            if (memcmp(tmp, tmp2, sizeof(tmp2)) != 0)
            {
                return false;
            }
        }
        
        return true;
    }
    
    string convertToStr()
    {
        stringstream ss;
        bool first = true;
        for (int i = data.m - 1; i >= 0; --i)
        {
            if (first)
            {
                first = false;
            }
            else 
            {
                ss << " ";
            }
            
            ss << ans[i];
        }
        
        return ss.str();
    }
private:
    Data data;
    enum {MAXM = 6};
    int pos[MAXM];
    int nums[MAXM];
    int ans[MAXM + 1];
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
        
    }
    
    bool hasNext() override
    {        
        instream >> data.m >> data.n;
        return data.m != 0 || data.n != 0;
    }
    
    Data next() override
    {
        return data;
    }
    
private:
    istream& instream;
    Data data;
};

template <typename T>
class Output : public OutputPolicy
{
public:
    Output(ostream& o, T& s): out(o), ans(s)
    {
        
    }
    
    void write() override
    {
        out << ans << endl;
    }
private:
    ostream& out;
    T& ans;
};

template <typename result>
AlgoPolicy<result>* getAlgo(const string& algoName, Data data)
{
    if (algoName == "dfs")
    {
        return new DfsAlgo(data);
    }
    
    return nullptr;
}

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

#ifndef ONLINE_JUDGE
    ifstream fin("/cygdrive/f/OJ/uva_in.txt");
    streambuf* cinback = cin.rdbuf(fin.rdbuf());
#endif
    
    shared_ptr<InputPolicy<Data>> in(new LimitInput(cin));
    while (in.get() != nullptr && in->hasNext())
    {
        Data data = in->next();
        Solution<string> solution(getAlgo<string>("dfs", data));
        string s = solution.run();
        shared_ptr<OutputPolicy> output(new Output<string>(cout, s));
        output->write();
    }
  
#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif

    return 0;
}
