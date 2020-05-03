class Solution 
{
    public boolean isAdditiveNumber(String num) 
    {
    	if (num == null || num.length() < 3)
    	{
    		return false;
    	}
    	
    	char[] chars = num.toCharArray();
    	long first, second;
    	for (int i = 0; i < num.length() / 2; ++i)
    	{
    		if (chars[0] == '0' && i > 0)
    		{
    			break;
    		}
    		
    		first = getNum(chars, 0, i);
    		
    		
    		for (int j = i + 1; j < num.length(); ++j)
    		{
    			if (chars[i + 1] == '0' && j > i + 1)
    			{
    				break;
    			}
    			
    			second = getNum(chars, i + 1, j);
    			if (isAdditiveNumberCore(chars, j + 1, first, second))
    			{
    				return true;
    			}
    		}
    	}
    	
    	return false;
    }
    
    private long getNum(char[] s, int start, int end)
    {
    	if (end >= s.length)
    	{
    		return -1;
    	}
    	
    	long num = 0;
    	for (int i = start; i <= end; ++i)
    	{
    		num = num * 10 + (s[i] - '0');
    	}
    	
    	return num;
    }
    
    private boolean isAdditiveNumberCore(char[] chars, int start, long first, long second)
    {
    	long target = first + second;
    	int len = String.valueOf(target).length();
    	if (getNum(chars, start, start + len - 1) != target)
    	{
    		return false;
    	}
    	
    	start = start + len;
    	return start == chars.length || isAdditiveNumberCore(chars, start, second, target);
    }
}