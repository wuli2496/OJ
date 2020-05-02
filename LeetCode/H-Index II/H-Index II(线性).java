class Solution 
{	
	public int hIndex(int[] citations) 
	{
		int idx = 0;
		int len = citations.length;
		
		for (int citation : citations)
		{
			if (citation >= len - idx)
			{
				return len - idx;
			}
			else 
			{
				++idx;
			}
		}
		
		return 0;
    }
}

