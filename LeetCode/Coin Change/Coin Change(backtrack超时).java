
class Solution 
{
	public int coinChange(int[] coins, int amount) 
	{
		return backtrack(0, coins, amount);
    }
	
	private int backtrack(int coinIndex, int[] coins, int amount)
	{
		if (amount == 0)
		{
			return 0;
		}
		
		if (coinIndex < coins.length && amount > 0)
		{
			int maxVal = amount / coins[coinIndex];
			int minCost = Integer.MAX_VALUE;
			for (int i = 0; i <= maxVal; ++i)
			{
				if (amount >= coins[coinIndex] * i)
				{
					int res = backtrack(coinIndex + 1, coins, amount - coins[coinIndex] * i);
					if (res != -1)
					{
						minCost = Math.min(res + i, minCost);
					}
				}
			}
			
			return minCost == Integer.MAX_VALUE ? -1 : minCost;
		}
		
		return -1;
	}
}