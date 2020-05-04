class Solution 
{
	public int maxProduct(String[] words) 
	{
		int n = words.length;
		
		int[] bitmask = new int[n];
		for (int i = 0; i < n; ++i)
		{
			for (char ch : words[i].toCharArray())
			{
				bitmask[i] |= (1 << bitNumber(ch));
			}
		}
		int maxProd = 0;
		for (int i = 0; i < n; ++i)
		{
			for (int j = i + 1; j < n; ++j)
			{
				if ((bitmask[i] & bitmask[j]) == 0)
				{
					maxProd = Math.max(maxProd, words[i].length() * words[j].length());
				}
			}
		}
		
		return maxProd;
    }

	
	private int bitNumber(char ch)
	{
		return ch - 'a';
	}
}