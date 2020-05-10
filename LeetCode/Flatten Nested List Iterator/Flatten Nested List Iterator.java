class NestedIterator implements Iterator<Integer> {

    public NestedIterator(List<NestedInteger> nestedList) 
    {
        stack = new Stack<>();
        ansStack = new Stack<>();
        
        for (NestedInteger nestedInteger : nestedList) 
        {
			stack.push(nestedInteger);
		}
        
        flattern(stack, ansStack);
    }

    @Override
    public Integer next() 
    {
        return ansStack.pop();
    }

    @Override
    public boolean hasNext() 
    {
       return !ansStack.isEmpty();
    }
    
    private void flattern(Stack<NestedInteger> aidStack, Stack<Integer> ansStack)
    {
    	while (!aidStack.isEmpty())
    	{
    		NestedInteger cur = aidStack.pop();
    		if (cur.isInteger())
    		{
    			ansStack.push(cur.getInteger());
    		}
    		else 
    		{
    			for (NestedInteger nestedInteger : cur.getList()) 
    			{
    				aidStack.push(nestedInteger);
				}
    		}
    	}
    }
    
    private Stack<NestedInteger> stack;
    private Stack<Integer> ansStack;
}
