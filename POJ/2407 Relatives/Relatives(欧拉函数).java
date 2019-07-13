import java.util.Scanner;

public class Main 
{
    public static void main(String args[])
    {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNext())
        {
            int x;
            x = cin.nextInt();
            if (x == 0)
            {
            	break;
            }
            
            int ans = Util.eular(x);
            System.out.println(ans);
        }
        
        cin.close();
    }
}

class Util
{
    public static int eular(int x)
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
}