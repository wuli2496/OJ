class NumArray 
{
    public NumArray(int[] nums) 
    {
    	this.nums = nums;
    	double sqrt = Math.sqrt(nums.length);
    	blocksLen = (int)Math.ceil(nums.length / sqrt);
    	blocks = new int[blocksLen];
    	
    	for (int i = 0; i < nums.length; ++i)
    	{
    		blocks[i / blocksLen] += nums[i];
    	}
    }
    
    public void update(int i, int val) 
    {
    	int block = i / blocksLen;
    	blocks[block] = blocks[block] - nums[i] + val;  
    	nums[i]= val; 
    	
    	return;
    }
    
    public int sumRange(int i, int j) 
    {
    	int sum = 0;
    	int startBlock = i / blocksLen;
    	int endBlock = j / blocksLen;
    	
    	if (startBlock == endBlock)
    	{
    		for (int index = i; index <= j; ++index)
    		{
    			sum += nums[index];
    		}
    	}
    	else 
    	{
    		for (int index = i; index <= (startBlock + 1) * blocksLen - 1; ++index)
    		{
    			sum += nums[index];
    		}
    		
    		for (int index = startBlock + 1; index < endBlock; ++index)
    		{
    			sum += blocks[index];
    		}
    		
    		for (int index = endBlock * blocksLen; index <= j; ++index)
    		{
    			sum += nums[index];
    		}
    	}
    	
    	return sum;
    }
    
    private int[] nums;
    private int[] blocks;
    private int blocksLen;
}
