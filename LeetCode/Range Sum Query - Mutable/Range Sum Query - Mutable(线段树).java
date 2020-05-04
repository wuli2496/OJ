class NumArray 
{
    public NumArray(int[] nums) 
    {
    	if (nums.length != 0)
    	{        	
        	n = nums.length;
        	tree = new int[2 * n];
        	
        	for (int i = n, j = 0; i < 2 * n; ++i, ++j)
        	{
        		tree[i]= nums[j]; 
        	}
        	
        	for (int i = n - 1; i >= 1; --i)
        	{
        		tree[i] = tree[2 * i] + tree[2 * i + 1];  
        	}
    	}
    }
    
    public void update(int i, int val) 
    {
    	int pos = i + n;
    	tree[pos] = val; 
    	while (pos > 0)
    	{
    		int left = pos;
    		int right = pos;
    		if (pos % 2 == 0)
    		{
    			right = pos + 1;
    		}
    		else 
    		{
    			left = pos - 1;
    		}
    		
    		tree[pos / 2] = tree[left] + tree[right];
    		
    		pos /= 2;
    	}
    	
    	return;
    }
    
    public int sumRange(int i, int j) 
    {
    	int left = i + n;
    	int right = j + n;
    	int sum = 0;
    	while (left <= right)
    	{
    		if (left % 2 == 1)
    		{
    			sum += tree[left];
    			++left;
    		}
    		
    		if (right % 2 == 0)
    		{
    			sum += tree[right];
    			--right;
    		}
    		
    		left /= 2;
    		right /= 2;
    	}
    	
    	return sum;
    }
    
    private int[] tree;
    private int n;
}
