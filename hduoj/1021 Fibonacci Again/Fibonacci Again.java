import java.util.Scanner;

public class Main 
{
    public static void main(String args[])
    {
        Scanner cin = new Scanner(System.in);

        while (cin.hasNextInt())
        {
            int x;
            x = cin.nextInt();
            
            Solution solver = new Solution(x);
            boolean ans = solver.run();
            
            if (ans)
            {
                System.out.println("yes");
            }
            else 
            {
                System.out.println("no");
            }
            
        }
    }
}

class Solution
{
    public Solution(int x)
    {
        this.x = x;
    }
    
    public boolean run()
    {
        return this.x % 8 == 2 || this.x % 8 == 6;
    }
    
    private final int x;
}
