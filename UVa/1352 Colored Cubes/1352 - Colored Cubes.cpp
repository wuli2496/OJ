#include <iostream>
#include <cstring>
#include <fstream>
#include <map>
#include <vector>

using namespace std;

const int N = 24;
const int M = 6;
const int MAXN = 4;
const int dice24[N][M] = {
{3, 1, 0, 5, 4, 2},
{3, 5, 1, 4, 0, 2},
{3, 4, 5, 0, 1, 2},
{3, 0, 4, 1, 5, 2},
{1, 2, 0, 5, 3, 4},
{5, 2, 1, 4, 3, 0},
{4, 2, 5, 0, 3, 1},
{0, 2, 4, 1, 3, 5},
{0, 1, 2, 3, 4, 5},
{1, 5, 2, 3, 0, 4},
{5, 4, 2, 3, 1, 0},
{4, 0, 2, 3, 5, 1},
{5, 1, 3, 2, 4, 0},
{4, 5, 3, 2, 0, 1},
{0, 4, 3, 2, 1, 5},
{1, 0, 3, 2, 5, 4},
{4, 3, 0, 5, 2, 1},
{0, 3, 1, 4, 2, 5},
{1, 3, 5, 0, 2, 4},
{5, 3, 4, 1, 2, 0},
{2, 4, 0, 5, 1, 3},
{2, 0, 1, 4, 5, 3},
{2, 1, 5, 0, 4, 3},
{2, 5, 4, 1, 0, 3}
};

class Solution
{
public:
    Solution(int n, vector<vector<int>> v)
    {
        this->n = n;
        colors = v;
    }
    
    int execute()
    {
        vector<int> r(n);
        r[0] = 0;
        
        int ans = M * n;
        dfs(1, r, ans);
        
        return ans;
    }
    
private:
    void dfs(int cur, vector<int>& r, int& res)
    {
        if (cur == n)
        {
            calculate(r, res);
            return;
        }
        
        for (int i = 0; i < N; ++i) 
        {
            r[cur] = i;
            dfs(cur + 1, r, res);
        }
    }
    
    void calculate(vector<int>& r, int& res)
    {
        vector<vector<int>> curColors(n);
        
        for (size_t i = 0; i < r.size(); ++i)
        {
            for (int j = 0; j < M; ++j)
            {
                curColors[i].push_back(colors[i][dice24[r[i]][j]]);
            }
        }
        
        int ans = M * n;
        int tot = 0;
        for (int i = 0; i < M; ++i)
        {
            vector<int> c(ans);
            int maxface = 0;
            for (int j = 0; j < n; ++j)
            {
                c[curColors[j][i]]++;
                maxface = max(maxface, c[curColors[j][i]]);
            }
            
            tot += n - maxface;
        }
        
        res = min(res, tot);
    }
    
    
private:
    vector<vector<int>> colors;
    int n;
};

int main()
{
    #ifndef ONLINE_JUDGE
        ifstream fin("f:\\OJ\\uva_in.txt");
        streambuf *old = cin.rdbuf(fin.rdbuf());
    #endif
    
    int n;
    while (cin >> n)
    {
        if (n == 0)
        {
            break;
        }
        
        vector<vector<int>> v(n);
        map<string, int> colorMap;
        
        for (int i = 0; i < n; ++i)
        {
            for (int j = 0; j < M; ++j)
            {
                string s;
                cin >> s;
                if (colorMap.find(s) != colorMap.end())
                {
                    v[i].push_back(colorMap[s]);
                }
                else 
                {
                    int len = colorMap.size();
                    v[i].push_back(len);
                    colorMap[s] = len;
                }
            }
        }
        
        /*
        cout << "=========================" << endl;
        for (size_t i = 0; i < v.size(); ++i)
        {
            for (size_t j = 0; j < v[i].size(); ++j)
            {
                cout << v[i][j] << " ";
            }
            
            cout << endl;
        }
        cout << "==============================" << endl;
         * */
        
        Solution solver = Solution(n, v);
        int ans = solver.execute();
        cout << ans << endl;
    }

    #ifndef ONLINE_JUDGE
        cin.rdbuf(old);
    #endif
    
    return 0;
}