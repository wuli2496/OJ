class Solution 
{
    public int maxProfit(int[] prices) 
    {
    	if (prices == null || prices.length < 2)
    	{
    		return 0;
    	}
    	
    	int dp_i_0 = 0;
    	int dp_i_1 = Integer.MIN_VALUE;
    	int prev = 0;
    	
    	for (int i = 0; i < prices.length; ++i) 
    	{
    		int tmp = dp_i_0;
    		dp_i_0 = Math.max(dp_i_0, dp_i_1 + prices[i]);
    		dp_i_1 = Math.max(dp_i_1, prev - prices[i]);
    		prev = tmp;
    	}
    	
    	return dp_i_0;
    }
}