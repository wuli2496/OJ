import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;


class RangeModule 
{
	private TreeSet<Interval> ranges;
	
    public RangeModule() 
    {
        ranges = new TreeSet<>();
    }
    
    public void addRange(int left, int right) 
    {
        Iterator<Interval> iter = ranges.tailSet(new Interval(0, left - 1)).iterator();
        
        while (iter.hasNext())
        {
        	Interval iv = iter.next();
        	if (right < iv.left) 
        	{
        		break;
        	}
        	
        	left = Math.min(left, iv.left);
        	right = Math.max(right, iv.right);
        	iter.remove();
        }
        
        ranges.add(new Interval(left, right));
    }
    
    public boolean queryRange(int left, int right) 
    {
        Interval result = ranges.higher(new Interval(0,  left));
        
        return (result != null) && (result.left <= left) && (right <= result.right);
    }
    
    public void removeRange(int left, int right) 
    {
        Iterator<Interval> iter = ranges.tailSet(new Interval(0, left)).iterator();
        List<Interval> toAddIntervals = new ArrayList<>();
        
        while (iter.hasNext()) 
        {
        	Interval iv = iter.next();
        	if (right < iv.left)
        	{
        		break;
        	}
        	
        	if (iv.left < left)
        	{
        		toAddIntervals.add(new Interval(iv.left, left));
        	}
        	
        	if (right < iv.right)
        	{
        		toAddIntervals.add(new Interval(right, iv.right));
        	}
        	
        	iter.remove();
        } 
        
        for (Interval ivInterval : toAddIntervals)
        {
        	ranges.add(ivInterval);
        }
    }
    
    class Interval implements Comparable<Interval> 
    {
    	private int left, right;
    	
    	public Interval(int left, int right)
    	{
    		this.left = left;
    		this.right = right;
    	}
    	
    	public int compareTo(Interval other)
    	{
    		if (this.right == other.right) 
    		{
    			return this.left - other.left;
    		}
    		
    		return this.right - other.right;
    	}
    }
};
