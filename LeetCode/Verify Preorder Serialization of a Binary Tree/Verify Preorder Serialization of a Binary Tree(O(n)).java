class Solution 
{
    public boolean isValidSerialization(String preorder) 
    {
    	int slots = 1;
    	int n = preorder.length();
    	for (int i = 0; i < n; ++i)
    	{
    		if (preorder.charAt(i) == ',')
    		{
    			--slots;
    			
    			if (slots < 0)
    			{
    				return false;
    			}
    			
    			if (preorder.charAt(i - 1) != '#')
    			{
    				slots += 2;
    			}
    		}
    	}
    	
    	slots = (preorder.charAt(n - 1) == '#' ? slots - 1 : slots + 2);
    	
    	return slots == 0;
    }
}
