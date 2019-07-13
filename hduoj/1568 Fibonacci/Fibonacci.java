import java.util.Scanner;

public class Main 
{
    public static void main(String args[])
    {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNext())
        {
            int n;
            n = cin.nextInt();
           
            Solution solution = new Solution(n);
            int ans = solution.execute();
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
		init();
	}
	
	public int execute()
	{
		if (this.n < N)
		{
			return fib[this.n];
		}
		
		
		double ans = a1 + this.n * a2;
		double digits = ans - Math.floor(ans);
		
		ans = Math.pow(10, digits);
		
		return (int)Math.floor(ans * 1000);
	}
	
	private void init()
	{
		fib = new int[N];
		fib[0] = 0;
		fib[1] = 1;
		
		for (int i = 2; i < N; ++i)
		{
			fib[i]= fib[i - 1] + fib[i - 2]; 
		}
	}
	
	private static final int N = 21;
	private int[] fib;
	private final int n;
	private final double a1 = Math.log10(1.0 / Math.sqrt(5));
	private final double a2 = Math.log10((1 + Math.sqrt(5)) / 2);
}

