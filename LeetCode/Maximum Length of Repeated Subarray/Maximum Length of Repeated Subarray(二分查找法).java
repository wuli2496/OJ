import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

interface AlgoStrategy
{
	int execute();
}

class BinarySearchAlgo implements AlgoStrategy
{
	public BinarySearchAlgo(int[] a, int[] b)
	{
		this.a = a;
		this.b = b;
	}
	
	public int execute()
	{
		int low = 0;
		int high = Math.min(a.length, b.length);
		
		while (low < high)
		{
			int mid = (low + high + 1) >> 1;
			if (check(mid))
			{
				low = mid;
			}
			else 
			{
				high = mid - 1;
			}
		}
		
		return low;
	}
	
	private boolean check(int len)
	{
		Set<String> seenSet = new HashSet<>();
		
		for (int i = 0; i + len <= a.length; ++i)
		{
			seenSet.add(Arrays.toString(Arrays.copyOfRange(a, i, i + len)));
		}
		
		for (int i = 0; i + len <= b.length; ++i)
		{
			if (seenSet.contains(Arrays.toString(Arrays.copyOfRange(b, i, i + len))))
			{
				return true;
			}
		}
		
		return false;
	}
	
	private int[] a;
	private int[] b;
}

interface AlgoFactory<T>
{
	T create();
}

class AlgoFactoryImpl implements AlgoFactory<BinarySearchAlgo>
{
	public AlgoFactoryImpl(int[] a, int[] b)
	{
		this.a = a;
		this.b = b;
	}
	
	public BinarySearchAlgo create()
	{
		return new BinarySearchAlgo(a, b);
	}
	
	private int[] a;
	private int[] b;
}

public class Solution 
{
    public int findLength(int[] A, int[] B) 
    {
        AlgoFactory algoFactory = new AlgoFactoryImpl(A, B);
        AlgoStrategy algoStrategy = (AlgoStrategy)algoFactory.create();
        return algoStrategy.execute();
    }
}

