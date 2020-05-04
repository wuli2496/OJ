class Solution 
{
	public int maxProduct(String[] words) 
	{
		int n = words.length;
		
		int maxProd = 0;
		for (int i = 0; i < n; ++i)
		{
			for (int j = i + 1; j < n; ++j)
			{
				if (noCommonLetters(words[i], words[j]))
				{
					maxProd = Math.max(words[i].length() * words[j].length(), maxProd);
				}
			}
		}
		
		return maxProd;
    }
	
	private boolean noCommonLetters(String s1, String s2)
	{
		int bitmask1 = 0;
		int bitmask2 = 0;
		for (char ch : s1.toCharArray())
		{
			bitmask1 |= (1 << bitNumber(ch));
		}
		
		for (char ch : s2.toCharArray())
		{
			bitmask2 |= (1 << bitNumber(ch));
		}
		
		return (bitmask1 & bitmask2) == 0;
	}
	
	private int bitNumber(char ch)
	{
		return ch - 'a';
	}
}