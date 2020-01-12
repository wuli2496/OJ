#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <memory>
#include <set>
#include <queue>
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

struct Node
{
    int state;
    set<int> s;
};

struct Data
{
    vector<int> d;
};

class BfsAlgo : public AlgoPolicy<set<int>>
{
public:
    BfsAlgo(const Data& data)
    {
        this->data = data;
    }
    
    set<int> execute() override
    {
        int n = data.d.size();
        map<int, int> idx;
        for (int i = 0; i < n; ++i)
        {
            idx[data.d[i]] = i;
        }
        
        Node initial;
        initial.state = 0;
        initial.s.insert(0);
        
        queue<Node> q;
        q.push(initial);
        
        map<int, bool> vis;
        set<int> ans;
        int maxVal = data.d[n - 1];
        while(!q.empty())
        {
            Node curState = q.front();
            q.pop();
            
            if (curState.state == (1 << n) - 1)
            {
                if (ans.size() == 0)
                {
                    ans = curState.s;
                }
                else 
                {
                    if (ans.size() > curState.s.size())
                    {
                        ans = curState.s;
                    }
                }
            }
            
            if (curState.s.size() == MAXSIZE)
            {
                continue;
            }
            
            for (int i = 0; i < n; ++i)
            {
                if (curState.state & (1 << i))
                {
                    continue;
                }
                
                for(auto& curVal : curState.s)
                {
                    if (curVal + data.d[i] <= maxVal)
                    {
                        Node newStatus = curState;
                        int sum = curVal + data.d[i];
                        
                        for (auto& newStatusVal : newStatus.s)
                        {
                            int val = abs(newStatusVal - sum);
                            if (idx.find(val) == idx.end())
                            {
                                continue;
                            }
                             
                            newStatus.state |= (1 << idx[val]);
                        }
                        
                        newStatus.s.insert(sum);
                        if (!vis[newStatus.state])
                        {
                            q.push(newStatus);
                            vis[newStatus.state] = true;
                        }
                    }
                    
                    if (curVal > data.d[i])
                    {
                        Node newStatus = curState;
                        int sum = curVal - data.d[i];
                        
                        for (auto& newStatusVal : newStatus.s)
                        {
                            int val = abs(newStatusVal - sum);
                            if (idx.find(val) == idx.end())
                            {
                                continue;
                            }
                             
                            newStatus.state |= (1 << idx[val]);
                        }
                        
                        newStatus.s.insert(sum);
                        if (!vis[newStatus.state])
                        {
                            q.push(newStatus);
                            vis[newStatus.state] = true;
                        }
                    }
                }
            }
        }
        
        return ans;
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
        
        auto it = unique(data.d.begin(), data.d.end());
        if (it != data.d.end())
        {
            data.d.erase(it, data.d.end());
        }
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
        
        cout << endl;
    }
private:
    ostream& out;
    set<int>& ans;
    
    static int caseNo;
};

int Output::caseNo = 1;

int main(int argc, char **argv)
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
        AlgoPolicy<set<int>>* algo = new BfsAlgo(data);
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
