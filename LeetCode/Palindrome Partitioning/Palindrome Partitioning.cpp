/*
 * Palindrome Partitioning
 * Given a string s, partition s such that every substring of the partition is a palindrome.
 * Return all possible palindrome partitioning of s.
 * Example:
 * Input: "aab"
 * Output:
 * [
 *  ["aa","b"],
 *  ["a","a","b"]
 *  ]
 */
 
 class Solution
{
public:
    vector<vector<string>> partition(string s)
    {
        vector<vector<bool>> dp;
        size_t size = s.length();
        dp.resize(size);
        for (size_t i = 0; i < size; ++i)
        {
            dp[i].resize(size);
        }

        calDp(dp, s);
        vector<vector<int>> palindrome;
        calPandrome(dp, palindrome);

        vector<vector<string>> ans;
        vector<string> row;
        dfs(s, palindrome, 0, ans, row);

        return ans;
    }

private:
    void calDp(vector<vector<bool>>& dp, const string& s)
    {
        for (int len = 0; len < (int)s.length(); ++len)
        {
            for (int i = 0; i + len < (int)s.length(); ++i)
            {
                int j = i + len;
                if (s[i] == s[j] && (j - i <= 2 || dp[i + 1][j - 1]))
                {
                    dp[i][j] = true;
                }
            }
        }
    }

    void calPandrome(const vector<vector<bool>>& dp, vector<vector<int>>& p)
    {
        p.resize(dp.size());
        for (size_t i = 0; i < dp.size(); ++i)
        {
            for (size_t j = 0; j < dp[i].size(); ++j)
            {
                if (dp[i][j] == true)
                {
                    p[i].push_back(j);
                }
            }
        }
    }

    void dfs(const string& s, const vector<vector<int>>& panlindrom, int cur, vector<vector<string>>& ans, vector<string>& row)
    {
        if (cur >= panlindrom.size())
        {
            ans.push_back(row);
            return;
        }

        for(int i =  0; i < panlindrom[cur].size(); ++i)
        {
            row.push_back(s.substr(cur, panlindrom[cur][i] - cur + 1));
            dfs(s, panlindrom, panlindrom[cur][i] + 1, ans, row);
            row.pop_back();
        }
    }
};