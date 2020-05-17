/*
ID: wuli2491
TASK: holstein
LANG: C++
*/
#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <queue>
#include <set>
#include <memory>
#include <algorithm>

using namespace std;

template<typename Result>
class AlgoPolicy
{
public:
    virtual ~AlgoPolicy() {}
    virtual Result execute() = 0;
};

struct Data
{
    vector<int> vitamins;
    vector<vector<int>> feeds;
};

class BfsAlgo : public AlgoPolicy<string>
{
public:
    BfsAlgo(const Data& otherData)
        :  data(otherData)
    {

    }

   string execute() override
   {
        queue<int> q;
        q.push(0);
        set<int> vis;
        vis.insert(0);
        while (!q.empty())
        {
           int curState = q.front(); q.pop();
           if (isTarget(curState))
           {
                return generateStr(curState);
           }

           for (size_t i = 0; i < data.feeds.size(); ++i)
           {
               int newState = curState | (1 << i);
               if (vis.count(newState) == 0)
               {
                   vis.insert(newState);
                   q.push(newState);
               }
           }
        }
   }

private:
   bool isTarget(int s)
   {
       for (size_t i = 0; i < data.vitamins.size(); ++i)
       {
           int total = 0;
           for (size_t j = 0; j < data.feeds.size(); ++j)
           {
               if (s & (1 << j))
               {
                   total += data.feeds[j][i];
               }
           }

           if (total < data.vitamins[i])
           {
               
               return false;
           }
       }

       return true;
   }

   string generateStr(int state)
   {
       int num = 0;
       for (size_t i = 0; i < data.feeds.size(); ++i)
       {
          if (state & (1 << i))
          {
              ++num;
          }
       }

      string ans = to_string(num);

      for (size_t i = 0; i < data.feeds.size(); ++i)
      {
          if (state & (1 << i))
          {
              ans += " ";
              ans += to_string(i + 1);
          }
      }

      return ans;
   }

private:
    const Data& data;
};

class DfsAlgo : public AlgoPolicy<string>
{
public:
    DfsAlgo(Data& otherData)
        : data(otherData)
    {
        best = data.feeds.size() + 1;
    }
    
    string execute() override
    {
        dfs(0, 0);
        return generateStr(bestFeed);
    }

private:
    void dfs(int fcnt, int fid)
    {
        size_t index;
        for (index = 0; index < data.vitamins.size(); ++index)
        {
            if (data.vitamins[index] > 0)
            {
                break;
            }
        }

        if (index >= data.vitamins.size())
        {
            best = fcnt;
            bestFeed = curFeed;
            return;
        }

        while ((fcnt + 1 < best) && (fid < data.feeds.size()))
        {
            for (size_t i = 0; i < data.vitamins.size(); ++i)
            {
                data.vitamins[i] -= data.feeds[fid][i];
            }

            curFeed.push_back(fid + 1);
            dfs(fcnt + 1, fid + 1);
            curFeed.pop_back();
            for (size_t i = 0; i < data.vitamins.size(); ++i)
            {
                data.vitamins[i] += data.feeds[fid][i];
            }
            ++fid;
        }
    }

    string generateStr(const vector<int>& v)
    {
        string ans = to_string(best);
        for (auto& n : v)
        {
            ans += " ";
            ans += to_string(n);
        }

        return ans;
    }
private:
    Data& data;
    int best;
    vector<int> bestFeed, curFeed;
};

template<typename Result>
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

class AlgoFactory
{
public:
    static AlgoFactory& getInstance()
    {
        static AlgoFactory instance;
        return instance;
    }

    AlgoPolicy<string>* createAlgo(string algoName, Data& data)
    {
        if (algoName == "bfs")
        {
            return new BfsAlgo(data);
        }
        else if (algoName == "dfs")
        {
            return new DfsAlgo(data);
        }
        else 
        {
            return nullptr;
        }
    }

private:
    AlgoFactory() {}
    ~AlgoFactory() {}
    AlgoFactory(const AlgoFactory& other);
    AlgoFactory& operator=(const AlgoFactory& other);
};

void printNum(int n)
{
    cout << n << " ";
}

void print(Data& data)
{
    for_each(data.vitamins.begin(), data.vitamins.end(), printNum);
    cout << endl;

    for(auto& feed : data.feeds)
    {
        for_each(feed.begin(), feed.end(), printNum);
        cout << endl;
    }
    cout << endl;
}
int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

#ifndef ONLINE_JUDGE
    ifstream fin("holstein.in");
    streambuf* cinback = cin.rdbuf(fin.rdbuf());

    ofstream fout("holstein.out");
    streambuf* coutback = cout.rdbuf(fout.rdbuf());
#endif
    
    Data data;
    int v;
    cin >> v;
    data.vitamins.resize(v);
    for (int i = 0; i < v; ++i)
    {
        cin >> data.vitamins[i];
    }

    int g;
    cin >> g;
    data.feeds.resize(g);
    for (int i = 0; i < g; ++i)
    {
        for (int j = 0; j < v; ++j)
        {
            int n;
            cin >> n;
            data.feeds[i].push_back(n);
        }   
    }

    //print(data);
    AlgoPolicy<string>* algo = AlgoFactory::getInstance().createAlgo("dfs", data);
    Solution<string> solution(algo);
    string ans = solution.run();
    cout << ans << endl;

#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
    cout.rdbuf(coutback);
#endif
}
