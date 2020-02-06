#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <memory>
#include <set>
#include <cmath>
#include <map>

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
    vector<int> d;
};

void print(int num)
{
    cout << num << " ";
}

class DfsAlgo : public AlgoPolicy<set<int>>
{
public:
    DfsAlgo(const Data& data)
    {
        this->data = data;
    }
    
    set<int> execute() override
    {        
        vector<int> availableSet;
        initAvailalbleSet(availableSet);
        
        set<int> dset(data.d.begin(), data.d.end());
        
        map<int, int> property;
        set<int> ans;
        vector<int> curAns;
        curAns.push_back(0);
        dfs(0, availableSet, 0, curAns, 0, ans, property, dset);
        
        return ans;
    }
    
    
private:
    void initAvailalbleSet(vector<int>& s)
    {
        set<int> ans;
        for (size_t i = 0; i < data.d.size(); ++i)
        {
            ans.insert(data.d[i]);
        }
        
        for (size_t i = 0; i < data.d.size(); ++i)
        {
            for (size_t j = i + 1; j < data.d.size(); ++j)
            {
                ans.insert(data.d[j] - data.d[i]);
            }
        }
        
        
        for(auto& n : ans)
        {
            s.push_back(n);
        }
    }
    
    void dfs(int curDepth, const vector<int>& v, int cur, vector<int>& curAns, int size, set<int>& ans, map<int, int>& property, const set<int>& dset)
    {
        if (curDepth >= MAXSIZE)
        {
            return;
        }
        
        if (size == static_cast<int>(data.d.size()))
        {
            if (ans.size() == 0 || curAns.size() < ans.size())
            {
                ans.clear();
                for (size_t i = 0; i < curAns.size(); ++i)
                {
                    ans.insert(curAns[i]);
                }
            }
            
            return;
        }
        
        for (size_t i = cur; i < v.size(); ++i)
        {
            int cnt = add(v[i], curAns, property, dset);
            if (!cnt)
            {
                del(v[i], curAns, property, dset);
                continue;
            }
            
            curAns.push_back(v[i]);
            dfs(curDepth + 1, v, i + 1, curAns, size + cnt, ans, property, dset);
            curAns.pop_back();
            del(v[i], curAns, property, dset);
        }
    }
    
    int add(int d, const vector<int>& curAns, map<int, int>& property, const set<int>& dset)
    {
        int cnt = 0;
        for (size_t i = 0; i < curAns.size(); ++i)
        {
            int diff = abs(curAns[i] - d);
            if (dset.count(diff))
            {
                ++property[diff];
                if (property[diff] == 1)
                {
                    ++cnt;
                }
            }
        }
        
        return cnt;
    }
    
    void del(int d, const vector<int>& curAns, map<int, int>& property, const set<int>& dset)
    {
        for (size_t i = 0; i < curAns.size(); ++i)
        {
            int diff = abs(curAns[i] - d);
            if (dset.count(diff))
            {
                --property[diff];
            }
        }
    }
private:
    Data data;
    const int MAXSIZE = 7;
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
        instream >> n;
        return n != 0;
    }
    
    Data next() override
    {
        Data data;
        for (int i = 0; i < n; ++i)
        {
            int d;
            instream >> d;
            data.d.push_back(d);
        }
        
        sort(data.d.begin(), data.d.end());
        
        data.d.erase(unique(data.d.begin(), data.d.end()), data.d.end());
        
        return data;
    }
    
private:
    istream& instream;
    int n;
};

class Output : public OutputPolicy
{
public:
    Output(ostream& o, set<int>& s): out(o), ans(s)
    {
        
    }
    
    void write() override
    {
        out << "Case " << caseNo++ << ":" << endl;
        out << ans.size() << endl;
        bool first = true;
        for (auto& n : ans)
        {
            if (first)
            {
                out << n;
                first = false;
            }
            else 
            {
                out << " " << n;
            }
        }
        
        out << endl;
    }
private:
    ostream& out;
    set<int>& ans;
    
    static int caseNo;
};

int Output::caseNo = 1;

int main()
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
        AlgoPolicy<set<int>>* algo = new DfsAlgo(data);
        Solution<set<int>> solution(algo);
        set<int> s = solution.run();
        shared_ptr<OutputPolicy> output(new Output(cout, s));
        output->write();
    }
  
#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif

    return 0;
}
