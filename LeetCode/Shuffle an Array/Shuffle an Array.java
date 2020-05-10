import java.util.Random;

class Solution 
{
	public Solution(int[] nums) 
	{
		array = nums;
		original = nums.clone();
    }
    
    /** Resets the array to its original configuration and return it. */
    public int[] reset() 
    {
    	array = original;
    	original = original.clone();
    	
    	return original;
    }
    
    /** Returns a random shuffling of the array. */
    public int[] shuffle() 
    {
    	for (int i = 0; i < array.length; ++i)
    	{
    		swapAt(i, randRange(i, array.length));
    	}
    	
    	return array;
    }
    
    private int randRange(int min, int max) 
    {
    	return random.nextInt(max - min) + min;
    }
    
    private void swapAt(int a, int b)
    {
    	int tmp = array[a];
    	array[a] = array[b];
    	array[b] = tmp;
    	
    	return;
    }
    private int[] array;
    private int[] original;
    private Random random = new Random();
}
