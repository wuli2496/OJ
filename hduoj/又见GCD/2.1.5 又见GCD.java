import java.util.Scanner;

public class Main 
{
    public static void main(String args[])
    {
        Scanner cin = new Scanner(System.in);
        int n;
        n = cin.nextInt();
        
        while (n-- > 0)
        {
            int a, b;
            a = cin.nextInt();
            b = cin.nextInt();
            
            Solution solver = new Solution(a, b);
            int ans = solver.run();
            System.out.println(ans);
        }
    }
}

class Solution
{
    public Solution(int a, int b)
    {
        this.a = a;
        this.b = b;
    }
    
    public int run()
    {
        int x = a / b;
        
        for (int i = 2; ; ++i)
        {
            if (gcd(x, i) == 1)
            {
                return i * b;
            }
        }
    }
    
    private int gcd(int n, int m)
    {
        int tmp = 0;
        if (n < m)
        {
            tmp = n;
            n = m;
            m = tmp;
        }
        
        while (n % m != 0)
        {
            tmp = n % m;
            n = m;
            m = tmp;
        }
        
        return m;
    }
    
    private final int a;
    private final int b;
}