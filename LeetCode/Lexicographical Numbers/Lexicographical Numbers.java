import java.util.ArrayList;
import java.util.List;

class Solution 
{
    public List<Integer> lexicalOrder(int n) 
    {
    	List<Integer> ansIntegers = new ArrayList<>();
    	
    	dfs(0, n, ansIntegers);
    	
    	return ansIntegers;
    }
    
    private void dfs(int curNum, int n, List<Integer> ans)
    {
    	if (curNum > n)
    	{
    		return;
    	}
    	
    	if (curNum > 0)
    	{
    		ans.add(curNum);
    	}
    	
    	for (int start = (curNum == 0 ? 1 : 0); start < 10; ++start)
    	{
    		dfs(curNum * 10 + start, n, ans);
    	}
    }
}
