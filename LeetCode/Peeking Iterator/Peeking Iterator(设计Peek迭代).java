import java.util.Iterator;

class PeekingIterator implements Iterator<Integer> 
{
	Integer curInteger;
	Iterator<Integer> iter;
	
	public PeekingIterator(Iterator<Integer> iterator) 
	{
	    // initialize any member here.
	    curInteger = null;
	    iter = iterator;
	}
	
    // Returns the next element in the iteration without advancing the iterator.
	public Integer peek() 
	{
        if (curInteger != null)
        {
        	return curInteger;
        }
        
        curInteger = iter.next();
        
        return curInteger;
	}
	
	// hasNext() and next() should behave the same as in the Iterator interface.
	// Override them if needed.
	@Override
	public Integer next() 
	{
	    if (curInteger != null)
	    {
	    	Integer tmpInteger = curInteger;
	    	curInteger = null;
	    	return tmpInteger;
	    }
	    
	    return iter.next();
	}
	
	@Override
	public boolean hasNext() 
	{
	    return curInteger != null || iter.hasNext();
	}
}

