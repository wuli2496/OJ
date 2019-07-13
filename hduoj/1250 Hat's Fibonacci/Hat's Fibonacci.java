import java.util.Scanner;
import java.math.BigInteger;

public class Main 
{
    public static void main(String args[])
    {
        Scanner cin = new Scanner(System.in);

        while (cin.hasNextInt())
        {
            int x;
            x = cin.nextInt();
            
            BigInteger ans = Solution.getInstance().run(x);
            System.out.println(ans);
           
        }
    }
}

class Solution
{
    private Solution()
    {
        fibs = new BigInteger[MAXN];
        fibs[0] = fibs[1] = fibs[2] = fibs[3] = BigInteger.ONE;
        
        for (int i = 4; i < MAXN; ++i)
        {
            fibs[i] = BigInteger.ZERO;
        }
    }
    
    public BigInteger run(int n)
    {
        if (fibs[n - 1] != BigInteger.ZERO)
        {
            return fibs[n - 1];
        }
        
        BigInteger f1 = fibs[0], f2 = fibs[1], f3 = fibs[2], f4 = fibs[3];
        BigInteger sum = BigInteger.ZERO;
        
        for (int i = 4; i < n; ++i)
        {
            sum = f1.add(f2).add(f3).add(f4);
            f1 = f2;
            f2 = f3;
            f3 = f4;
            f4 = sum;
        }
        
        fibs[n - 1] = sum;
        
        return fibs[n - 1];
    }
    
    public static Solution getInstance()
    {
        if (null == instance)
        {
            instance = new Solution();
        }
        
        return instance;
    }
    
    private static Solution instance;
    private final int MAXN = 10001;
    private final BigInteger[] fibs;
}