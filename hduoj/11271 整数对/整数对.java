import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main 
{
    public static void main(String args[])
    {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNext())
        {
            int n;
            n = cin.nextInt();
            if (n == 0)
            {
            	break;
            }
            
            Solution solution = new Solution(n);
            List<Integer> ans = solution.execute();
            if (ans.isEmpty())
            {
            	System.out.println("No solution.");
            }
            else 
            {
            	boolean first = true;
            	for(Integer v : ans)
            	{
            		if (first)
            		{
            			first = false;
            		}
            		else 
            		{
            			System.out.print(" ");
            		}
            		
            		System.out.print(v);
            	}
            	
            	System.out.println();
            }
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
	
	public List<Integer> execute()
	{
		List<Integer> ansIntegers = new ArrayList<Integer>();
		
		int a = 0;
		int b = 0;
		int c = 0;
		for (int i = 1; i <= this.n; i *= 10)
		{
			b = this.n / i % 11;
			c = this.n / i / 11;
			
			if ((c != 0 || b != 0) && b < 10)
			{
				a = (this.n - b * i - c * i * 11) / 2;
				if (this.n == 2 * a + b * i + c * i * 11) 
				{
					ansIntegers.add(a  + b * i + c * i * 10);
				}
			}
			
			--b;
			if ((c != 0 || b != 0) && b >= 0)
			{
				a = (this.n - b * i - c * i * 11) / 2;
				if (this.n == 2 * a + b * i + c * i * 11) 
				{
					ansIntegers.add(a  + b * i + c * i * 10);
				}
			}
		}
		
		ansIntegers = ansIntegers.stream().distinct().collect(Collectors.toList());
		Collections.sort(ansIntegers);
		
		return ansIntegers;
		
	}
	
	private final int n;
}

