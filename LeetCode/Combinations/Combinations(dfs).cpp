class Solution {
public:
    public:
    vector<vector<int>> combine(int n, int k) 
    {
        vector<vector<int>> ans;
        vector<int> v;
        dfs(1, 0, k, n, v, ans);
        
        return ans;
    }
    
private:
    void dfs(int cur, int curdep, int dep, int n, vector<int>& v, vector<vector<int>>& ans)
    {
        if (curdep == dep)
        {
            ans.push_back(v);
            return;
        }
        
        for (int i = cur; i <= n; ++i)
        {
            v.push_back(i);
            dfs(i + 1, curdep + 1, dep, n, v, ans);
            v.pop_back();
        }
    }
};