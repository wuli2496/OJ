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
            int x;
            x = cin.nextInt();
            
            Solution solver = new Solution(x);
            int ans = solver.run();
            System.out.println(ans);
        }
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
       double tmp = x * Math.log10(x);
       double re = tmp - Math.floor(tmp);
       
       return (int)Math.floor(Math.pow(10.0, re));
    }
    
    private final int x;
}
