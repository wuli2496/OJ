class Solution 
{
    public boolean isValidSerialization(String preorder) 
    {
    	int slot = 1;
    	for (String s : preorder.split(","))
    	{
    		--slot;
    		
    		if (slot < 0)
    		{
    			return false;
    		}
    		
    		if (!s.equals("#"))
    		{
    			slot += 2;
    		}
    	}
    	
    	return slot == 0;
    }
}
