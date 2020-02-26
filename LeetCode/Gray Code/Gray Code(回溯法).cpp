class Solution
{
public:
    vector<int> grayCode(int n)
    {
        if (n == 0)
        {
            return {0};
        }

        vector<int> ans;
        backtrace(ans, n);

        return ans;
    }

private:
    void backtrace(vector<int>& ans, int n)
    {
        if (n == 0)
        {
            ans.push_back(0);
            return;
        }

        backtrace(ans, n - 1);
        int mask = 1 << (n - 1);
        for (int i = static_cast<int>(ans.size()) - 1; i >= 0; --i)
        {
            ans.push_back(ans[i] | mask);
        }
    }
};