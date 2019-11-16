import java.util.Scanner;

interface AlgoPolicy<Result>
{
	Result execute();
}

class JosephusAlgo implements AlgoPolicy<Integer>
{
	public JosephusAlgo(int m, int k, int cnt)
	{
		this.m = m;
		this.k = k;
		this.cnt = cnt;
	}
	
	public Integer execute()
	{
		return josephus(m, k, cnt);
	}
	
	private int josephus(int circleLen, int select, int c)
	{
		if (c == 1)
		{
			return (circleLen + select - 1) % circleLen;
		}
		else 
		{
			return (josephus(circleLen - 1, select, c - 1) + k) % circleLen;
		}
	}
	
	private final int m, k, cnt;
}

public class Main 
{
	public static void main(String[] args)
	{
		Scanner cin = new Scanner(System.in);
		while (cin.hasNext())
		{
			int n = cin.nextInt();
			if (n == 0)
			{
				break;
			}
			
			Solution solution = new Solution(n);
			int ans = solution.run();
			System.out.println(ans);
		}
		
		cin.close();
	}
}

class Solution
{	
	public Solution(int n)
	{
		this.n = n;
	}
	
	public int run()
	{
		return table[n];
	}
	
	private static void init()
	{
		for (int i = 1; i < MAXK; ++i)
		{
			int m = 2 * i;
			for (int k = i + 1; ; ++k)
			{
				int j = 1;
				for (; j <= i; ++j)
				{
					AlgoPolicy<Integer> algo = new JosephusAlgo(m, k, j);
					int ans = algo.execute();
					if (ans + 1 <= i)
					{
						break;
					}
				}
				
				if (j > i)
				{
					table[i] = k;
					break;
				}
			}
			
		}
	}
	
	private final int n;
	
	private static final int MAXK = 14;
	private static int[] table = new int[MAXK];
	
	static {
		init();
	}
}
