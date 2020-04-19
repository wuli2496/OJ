class Solution 
{
    public int rangeBitwiseAnd(int m, int n) 
    {
        if (m == Integer.MAX_VALUE)
        {
            return m;
        }
        
        int ans = m;
        for (int i = m + 1; i <= n; ++i)
        {
            ans &= i;
            if (ans == 0 || i == Integer.MAX_VALUE)
            {
                break;
            }
        }
        return ans;
    }
}