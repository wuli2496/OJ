#include <iostream>
#include <fstream>
#include <string>
#include <map>
#include <vector>
#include <algorithm>
#include <memory>

using namespace std;

const int MAXLEN = 21;

map<string, int> typeMap;
int cnt;

int ID(string s)
{
    if (!typeMap.count(s))
    {
        typeMap[s] = cnt++;
    }
    
    return typeMap[s];
}

class Component
{
public:
    int price;
    int quality;
};

class AlgoStrategy
{
public:
    virtual int execute() = 0;
    
    virtual ~AlgoStrategy() {}
};

class BinarySearchAlgo : public AlgoStrategy
{
public:
    BinarySearchAlgo(int budget, int quality, vector<vector<Component>>& assemb)
    : b(budget), maxQuality(quality), assembles(assemb)
    {
        
    }
    
    virtual int execute()
    {
        int low = 0, high = maxQuality;
        while (low < high)
        {
            int mid = (low + high + 1) >> 1;
            if (check(mid))
            {
                low = mid;
            }
            else 
            {
                high = mid - 1;
            }
        }
        
        return low;
    }
    
private:
    bool check(int curQuality)
    {
        int sum = 0;
        for (size_t i = 0, len = assembles.size(); i < len; ++i)
        {
            vector<Component>& v = assembles[i];
            int cheapest = b + 1;
            for (size_t j = 0, len = v.size(); j < len; ++j)
            {
                if (v[j].quality >= curQuality)
                {
                    cheapest = min(cheapest, v[j].price);
                }
            }
            
            if (cheapest == b + 1)
            {
                return false;
            }
            
            sum += cheapest;
            if (sum > b)
            {
                return false;
            }
        }
        
        return true;
    }
    
private:
    int b;
    int maxQuality;
    vector<vector<Component>>& assembles;
};

int main()
{
    #ifndef ONLINE_JUDGE
        ifstream fin("f:\\OJ\\uva_in.txt");
        streambuf* old = cin.rdbuf(fin.rdbuf());
    #endif
    
    int testCase;
    cin >> testCase;
    for (int i = 0; i < testCase; ++i)
    {
        typeMap.clear();
        cnt = 0;
        
        int n, b;
        cin >> n >> b;
        vector<vector<Component>> components;
        int maxQuality = 0;
        for (int i = 0; i < n; ++i)
        {
            Component comp;
            string type, name;
            cin >> type >> name >> comp.price >> comp.quality;
            maxQuality = max(maxQuality, comp.quality);
            size_t index = static_cast<size_t>(ID(type));
            if (index >= components.size())
            {
                components.resize(index + 1);
            }
            components[index].push_back(comp);
        }
        
        shared_ptr<AlgoStrategy> sp(new BinarySearchAlgo(b, maxQuality, components));
        int ans = sp->execute();
        cout << ans << endl;
    }
    
    #ifndef ONLINE_JUDGE
        cin.rdbuf(old);
    #endif
    
    return 0;
}