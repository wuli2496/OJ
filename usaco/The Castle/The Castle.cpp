/*
ID: wuli2491
TASK: castle
LANG: C++                 
*/
#include <iostream>
#include <fstream>
#include <memory>
#include <string>
#include <queue>
#include <cstring>
#include <map>
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

const int MAXN = 51;
const int DIRSIZE = 4;
const int dir[DIRSIZE] = {1, 2, 4, 8};
const int dx[DIRSIZE] = {0, -1, 0, 1};
const int dy[DIRSIZE] = {-1, 0, 1, 0};

struct Data
{
    int n, m;
    bool grid[MAXN][MAXN][DIRSIZE];
};

struct Node
{
    int x, y;
};

class FloodFillAlgo : public AlgoPolicy<string>
{
public:
    FloodFillAlgo(Data data)
    {
        this->data = data;
        memset(f, 0x00, sizeof(f));
    }
    
    string execute() override
    {
        int component = 0;
        map<int, int> componentmap;
        int maxRoom = 0;
        
		breadFirstScan(component, componentmap, maxRoom);
#if 0
        for (int i = 0; i < data.n; ++i)
        {
            for (int j = 0; j < data.m; ++j)
            {
                if (f[i][j] == 0)
                {
                    ++component;
                    componentmap[component] = 1;
                    breadFirstSearch(i, j, component, componentmap);
                    
                    if (componentmap[component] > maxRoom)
                    {
                        maxRoom = componentmap[component];
                    }
                }
            }
        }
#endif
#if 0
        cout << "======================" << endl;
        for (int i = 0; i < data.n; ++i)
        {
            for (int j = 0; j < data.m; ++j)
            {
                cout << f[i][j] << " ";
            }
            cout << endl;
        }
        cout << endl;
        cout << "======================" << endl;
#endif
        
        int largestRoom = 0;
        int removeX = 0;
        int removeY = 0;
        int removeDir = 0;
        for (int y = 0; y < data.m; ++y)
        {
            for (int x = data.n - 1; x >= 0; --x)
            {
                for (int k = 1; k < DIRSIZE - 1; ++k)
                {
                    int xx = x + dx[k];
                    int yy = y + dy[k];
                    if (xx >= 0 && yy < data.m && f[x][y] != f[xx][yy] && componentmap[f[x][y]] + componentmap[f[xx][yy]] > largestRoom)
                    {
                        largestRoom = componentmap[f[x][y]] + componentmap[f[xx][yy]];
                        removeX = x + 1;
                        removeY = y + 1;
                        removeDir = k;
                    }
                }
            }
        }
        
        stringstream ans;
        ans << component << endl;
        ans << maxRoom << endl;
        ans << largestRoom << endl;
        ans << removeX << " " << removeY << " ";
        if (removeDir == 1)
        {
            ans << 'N' << endl;
        }
        else 
        {
            ans << 'E' << endl;
        }
        
        return ans.str();
    }
    
private:
    void breadFirstSearch(int x, int y, int component, map<int, int>& componentMap)
    {
        f[x][y] = component;
        queue<Node> q;
        Node node;
        node.x = x;
        node.y = y;
        
        q.push(node);
        while (!q.empty())
        {
            Node tmp = q.front(); q.pop();
            
            for (int i = 0; i < DIRSIZE; ++i)
            {
                int xx = tmp.x + dx[i];
                int yy = tmp.y + dy[i];
                
                if (xx < 0 || xx >= data.n || yy < 0 || yy >= data.m || data.grid[tmp.x][tmp.y][i] || f[xx][yy] != 0)
                {
                    continue;
                }
                
                f[xx][yy] = component;
                ++componentMap[component];
                
                q.push((Node){xx, yy});
            }
        }
    }
	
	void depthFirstSearch(int x, int y, int component, map<int, int>& componentMap)
    {
        f[x][y] = component;
        
        for (int i = 0; i < DIRSIZE; ++i)
        {
            int xx = x + dx[i];
            int yy = y + dy[i];
            if (xx < 0 || xx >= data.n || yy < 0 || yy >= data.m || data.grid[x][y][i] || f[xx][yy] != 0)
            {
                continue;
            }
            
            ++componentMap[component];
            depthFirstSearch(xx, yy, component, componentMap);
        }
    }
	
	void breadFirstScan(int& component, map<int, int>& componentMap, int& maxRoom)
    {
        component = 0;
        for (int i = 0; i < data.n; ++i)
        {
            for (int j = 0; j < data.m; ++j)
            {
                if (f[i][j] == 0)
                {
                    f[i][j] = -2;
                    ++component;
                    floodFill(component, componentMap, maxRoom);
                }
            }
        }
    }
    
    void floodFill(int& component, map<int, int>& componentMap, int& maxRoom)
    {
        int numVisited = 0;
        do 
        {
            numVisited = 0;
            for (int i = 0; i < data.n; ++i)
            {
                for (int j = 0; j < data.m; ++j)
                {
                    if (f[i][j] == -2)
                    {
                        ++numVisited;
                        f[i][j] = component;
                        ++componentMap[component];
                        if (componentMap[component] > maxRoom)
                        {
                            maxRoom = componentMap[component];
                        }
                        
                        for (int k = 0; k < DIRSIZE; ++k)
                        {
                            int xx = i + dx[k];
                            int yy = j + dy[k];
                            if (xx < 0 || xx >= data.n || yy < 0 || yy >= data.m || data.grid[i][j][k] || (f[xx][yy] != 0 && f[xx][yy] != -2))
                            {
                                continue;
                            }
                            
                            f[xx][yy] = -2;
                        }
                    }
                }
            }
        } while (numVisited != 0);
    }
private:
    Data data;
    int f[MAXN][MAXN];
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
        return !instream.eof();
    }
    
    Data next() override
    {
        for (int i = 0; i < data.n; ++i)
        {
            for (int j = 0; j < data.m; ++j)
            {
                int num;
                instream >> num;
                
                for (int k = DIRSIZE - 1; k >= 0; --k)
                {
                    if (num >= dir[k])
                    {
                        num -= dir[k];
                        data.grid[i][j][k] = true;
                    }
                    else 
                    {
                        data.grid[i][j][k] = false;
                    }
                }
            }
        }
        
        return data;
    }
private:
    istream& instream;
    Data data;
};

class Output : public OutputPolicy
{
public:
    Output(ostream& out, string& str) : outstream(out), s(str)
    {
        
    }
    
    void write() override
    {
        outstream << s;
    }
private:
    string& s;
    ostream& outstream;
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
int main(int argc, char** argv) 
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

#ifndef ONLINE_JUDGE
    ifstream fin("castle.in");
    streambuf* cinback = cin.rdbuf(fin.rdbuf());
    
    ofstream fout("castle.out");
    streambuf* coutback = cout.rdbuf(fout.rdbuf());
#endif
    
    shared_ptr<InputPolicy<Data>> in(new LimitInput(cin));
    while (in.get() != nullptr && in->hasNext())
    {
        Data data = in->next();
        AlgoPolicy<string>* algo = new FloodFillAlgo(data);
        Solution<string> solution(algo);
        string s = solution.run();
        shared_ptr<OutputPolicy> output(new Output(cout, s));
        output->write();
    }
  
#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
    cout.rdbuf(coutback);
#endif
    
    return 0;
}
