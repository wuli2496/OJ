#include <iostream>
#include <fstream>
#include <memory>
#include <vector>
#include <set>
#include <algorithm>
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

const int MAXSIZE = 16;

struct Data
{
    int n;
};

class Triangle
{
public:
    Triangle()
    {
        x = y = z = 0;
    }
    
    Triangle(int x, int y, int z)
    {
        this->x = x;
        this->y = y;
        this->z = z;
    }
    
    Triangle add(int type)
    {
        int mv = (x + y + z == 0 ? 1 : -1);
        if (type == 0)
        {
            return Triangle(x, y + mv, z + mv);
        } 
        else if (type == 1)
        {
            return Triangle(x + mv, y, z + mv);
        }
        else
        {
            return Triangle(x + mv, y + mv, z);
        }
    }
    
    void move(int x, int y, int z)
    {
        this->x += x;
        this->y += y;
        this->z += z;
    }
    
    void rotate()
    {
        int tmp = x;
        x = y;
        y = z;
        z = tmp;
    }
    
    void overturn()
    {
        x = 1 - x;
        y = 1 - y;
        z = -z;
    }
    
    friend bool operator==(const Triangle& a, const Triangle& b);
    friend bool operator<(const Triangle& a, const Triangle& b);
    friend class DfsAlgo;
private:
    int x, y, z;
};

bool operator==(const Triangle& a, const Triangle& b)
{
    return a.x == b.x && a.y == b.y && a.z == b.z;
}

bool operator<(const Triangle& a, const Triangle& b)
{
    if (a.x != b.x)
    {
        return a.x < b.x;
    }
    
    if (a.y != b.y)
    {
        return a.y < b.y;
    }
    
    return a.z < b.z;
}

struct Hash
{
    int cnt;
    int key[MAXSIZE];
};

bool operator<(const Hash& a, const Hash& b)
{
    if (a.cnt != b.cnt)
    {
        return a.cnt < b.cnt;
    }
    
    for (int i = 0; i < a.cnt; ++i)
    {
        if (a.key[i] != b.key[i])
        {
            return a.key[i] < b.key[i];
        }
    }
    
    return false;
}

/*
 * 
 * 使用dfs会超时，先用dfs得到0-16的解，然后使用打表方式
1:1
2:1
3:1
4:4
5:6
6:19
7:43
8:120
9:307
10:866
11:2336
12:6588
13:18373
14:52119
15:147700
16:422016
 */
class DfsAlgo : public AlgoPolicy<int>
{
public:
    DfsAlgo(const Data& data)
    {
        this->data = data;
    }
    
    int execute() override
    {        
        vector<Triangle> v;
        v.push_back(Triangle(0, 0, 0));
        set<Hash> s;
        map<int, int> ans;
        dfs(v, ans, s);
        
        return ans[data.n];
    }
    
private:
    void dfs(vector<Triangle>& v, map<int, int>& ans, set<Hash>& s)
    {
        if (v.size() > data.n)
        {
            return;
        }
        
        if (search(v, s))
        {
            return;
        }
        
        updateHash(v, s);
        
        ++ans[static_cast<int>(v.size())];
        
        for (size_t i = 0; i < v.size(); ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                Triangle tmp = v[i].add(j);
                if (addTriangle(v, tmp))
                {
                    dfs(v, ans, s);
                    v.pop_back();
                    
                }
            }
        }
    }
    
    Hash getKey(const vector<Triangle>& v)
    {
        Hash ans;
        ans.cnt = v.size();
        
        for (int i = 0; i < ans.cnt; ++i)
        {
            ans.key[i] = v[i].x + 8;
            ans.key[i] <<= 4;
            ans.key[i] += v[i].y + 8;
            ans.key[i] <<= 4;
            ans.key[i] += v[i].z + 8;
            ans.key[i] <<= 4;
        }
        
        return ans;
    }
    
    bool find(vector<Triangle>& v, const set<Hash>& s)
    {
        for (int i = 0; i < 3; ++i)
        {
            sort(v.begin(), v.end());
            
            if (s.count(getKey(v)))
            {
                return true;
            }
            
            for (size_t j = 0; j < v.size(); ++j)
            {
                v[j].rotate();
            }
        }
        
        return false;
    }
    
    bool search(const vector<Triangle>& v, const set<Hash>& s)
    {
        for (size_t i = 0; i < v.size(); ++i)
        {
            int x = v[i].x;
            int y = v[i].y;
            int z = v[i].z;
            
            vector<Triangle> tmp = v;
            if (x + y + z == 0)
            {
                for (size_t j = 0; j < tmp.size(); ++j)
                {
                    tmp[j].move(-x, -y, -z);
                }
                
                if (find(tmp, s))
                {
                    return true;
                }
            }
            else 
            {
                for (size_t j = 0; j < tmp.size(); ++j)
                {
                    tmp[j].overturn();
                    tmp[j].move(x - 1, y - 1, z);
                }
                
                if (find(tmp, s))
                {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    void updateHash(vector<Triangle> v, set<Hash>& s)
    {
        for (int i = 0; i < 3; ++i)
        {
            sort(v.begin(), v.end());
            
            s.insert(getKey(v));
            
            for (size_t j = 0; j < v.size(); ++j)
            {
                v[j].rotate();
            }
        }
    }
    
    bool addTriangle(vector<Triangle>& v, const Triangle& tri)
    {
        for (size_t i = 0; i < v.size(); ++i)
        {
            if (v[i] == tri)
            {
                return false;
            }
        }
        
        v.push_back(tri);
        
        return true;
    }
private:
    Data data;
};

class TableAlgo : public AlgoPolicy<int>
{
public:
    TableAlgo(Data data)
    {
        this->data = data;
    }
    
    int execute() override
    {        
        return table[data.n];
    }
private:
    Data data;
    static int table[MAXSIZE + 1];
};

int TableAlgo::table[MAXSIZE + 1] = {0, 1, 1, 1, 4, 6, 19, 43, 120, 307, 866, 2336, 6588, 18373, 52119, 147700, 422016};

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
        instream >> n;
    }
    
    bool hasNext() override
    {        
        return n != 0;
    }
    
    Data next() override
    {
        Data data;
        instream >> data.n;
        
        --n;
        return data;
    }
    
private:
    istream& instream;
    int n;
};

class Output : public OutputPolicy
{
public:
    Output(ostream& o, int s): out(o), ans(s)
    {
        
    }
    
    void write() override
    {
        out << "Case #" << caseNo++ << ": " << ans << endl;
    }
private:
    ostream& out;
    int ans;
    
    static int caseNo;
};

int Output::caseNo = 1;

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
        AlgoPolicy<int>* algo = new TableAlgo(data);
        Solution<int> solution(algo);
        int s = solution.run();
        shared_ptr<OutputPolicy> output(new Output(cout, s));
        output->write();
    }
  
#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif

    return 0;
}
