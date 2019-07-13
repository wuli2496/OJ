import java.util.Scanner;

public class Main 
{
    public static void main(String args[])
    {
        Scanner cin = new Scanner(System.in);
        int cn;
        cn = cin.nextInt();
        
        while (cn-- > 0)
        {
            int x;
            x = cin.nextInt();
            
            Solution solver = new Solution(x);
            int ans = solver.run();
            System.out.println(ans);
        }
        
        cin.close();
    }
}

class Solution
{
    public Solution(int x)
    {
        this.x = x;
    }
    
    public int run()
    {
        return eular(x);
    }
    
    private int eular(int x)
    {
        int ans = 1;
        
        for (int i = 2; i * i <= x; ++i)
        {
        	if (x % i == 0)
        	{
        		x = x / i;
        		ans *= (i - 1);
        		
        		while (x % i == 0)
        		{
        			x = x / i;
        			ans *= i;
        		}
        	}
        }
        
        if (x > 1)
        {
        	ans *= (x - 1);
        }
        
        return ans;
    }
    
    private final int x;
}

