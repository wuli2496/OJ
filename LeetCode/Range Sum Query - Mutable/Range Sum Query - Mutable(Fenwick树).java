class NumArray 
{
    public NumArray(int[] nums) 
    {
    	t = new int[nums.length + 1];
    	src = nums;
    	for (int i = 0; i < nums.length; ++i)
    	{
    		add(i, nums[i]);
    	}
    }
    
    public void update(int i, int val) 
    {
    	add(i, val - src[i]);
    	src[i] = val;
    }
    
    public int sumRange(int i, int j) 
    {
    	return sum(j) - sum(i - 1);
    }
    
    private int lowbit(int x)
    {
    	return x & -x;
    }
    
    private int sum(int i)
    {
    	int x = i + 1;
    	int ans = 0;
    	while (x > 0)
    	{
    		ans += t[x];
    		x -= lowbit(x);
    	}
    	
    	return ans;
    }
    
    private void add(int i, int val) 
    {
    	int n = t.length;
    	int x = i + 1;
    	while (x < n)
    	{
    		t[x] += val;
    		x += lowbit(x);
    	}
    }
    
    private int[] t;
	private int[] src;
}