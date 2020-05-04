class NumArray 
{

    public NumArray(int[] nums) 
    {
    	this.nums = nums;
    }
    
    public void update(int i, int val) 
    {
    	nums[i] = val; 
    }
    
    public int sumRange(int i, int j) 
    {
    	int sum = 0;
    	for (int start = i; start <= j; ++start)
    	{
    		sum += nums[start];
    	}
    	
    	return sum;
    }
    
    private int[] nums;
}
