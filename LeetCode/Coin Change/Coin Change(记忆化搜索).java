
class Solution 
{
	public int coinChange(int[] coins, int amount) 
	{
		return dp(coins, amount, new int[amount + 1]);
    }
	
	private int dp(int[] coins, int amount, int[] memo)
	{
		if (amount < 0)
		{
			return -1;
		}
		
		if (amount == 0)
		{
			return 0;
		}
		
		if (memo[amount] != 0)
		{
			return memo[amount];
		}
		
		int minCost = Integer.MAX_VALUE;
		for (int coin : coins)
		{
			int res = dp(coins, amount - coin, memo);
			if (res != -1)
			{
				minCost = Math.min(minCost, res + 1);
			}
		}
		
		return memo[amount] = (minCost == Integer.MAX_VALUE ? -1 : minCost); 
	}
}