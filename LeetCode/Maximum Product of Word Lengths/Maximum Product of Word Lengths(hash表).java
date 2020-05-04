import java.util.HashMap;
import java.util.Map;

class Solution 
{
	public int maxProduct(String[] words) 
	{
		int n = words.length;
		Map<Integer, Integer> bitmaskMap = new HashMap<>();
		
		int bitmask = 0;
		for (int i = 0;  i < n; ++i)
		{
			bitmask = 0;
			for (char ch : words[i].toCharArray())
			{
				bitmask |= (1 << bitNumber(ch));
			}
			
			bitmaskMap.put(bitmask, Math.max(bitmaskMap.getOrDefault(bitmask, 0), words[i].length()));	
		}
		
		int maxProd = 0;
		for (int x : bitmaskMap.keySet())
		{
			for (int y : bitmaskMap.keySet())
			{
				if ((x & y) == 0)
				{
					maxProd = Math.max(bitmaskMap.get(x) * bitmaskMap.get(y), maxProd);
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