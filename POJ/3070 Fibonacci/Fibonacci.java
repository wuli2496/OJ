import java.util.Scanner;

public class Main 
{
    public static void main(String args[])
    {
        Scanner cin = new Scanner(System.in);
        int n;
        int m = 10000;
		int[][] data = {{1, 1}, {1, 0}};
		int ans = 0;
        while (cin.hasNext())
        {
			n = cin.nextInt();
			if (-1 == n)
			{
				break;
			}

			if (0 == n)
			{
				ans = 0;
			}
			else
			{
				Solution solution = new Solution(n, m, data);
				Matrix ansMatrix = solution.solve();

				ans = ansMatrix.getData()[0][0];
			}

			System.out.println(ans);
		}

        cin.close();
    }
}

class Solution 
{
	public Solution(int n, int m, int[][] data)
	{
		this.n = n;
		this.m = m;
		origin = new Matrix(data);
	}
	
	public Matrix solve()
	{
		return origin.pow(n - 1, m);
	}

	private final int n;
	private final int m;
	private final Matrix origin;
}

class Matrix
{
	public Matrix(int n)
	{
		this.n = n;
		data = new int[n][n];
	}
	
	public Matrix(int n, boolean unit)
	{
		this.n = n;
		data = new int[n][n];
		
		if (unit)
		{
			for (int i = 0; i < n; ++i)
			{
				for (int j = 0; j < n; ++j)
				{
					if (j == i)
					{
						data[i][j] = 1;
					}
				}
			}
		}
	}
	
	public Matrix(int[][] data)
	{
		this.n = data.length;
		this.data = data;
	}
	
	public Matrix(Matrix other)
	{
		this.n = other.n;
		data = new int[n][n];
		
		for (int i = 0; i < n; ++i)
		{
			for (int j = 0; j < n; ++j)
			{
				data[i][j]= other.data[i][j]; 
			}
		}
	}
	
	public Matrix mul(Matrix other)
	{
		Matrix ans = new Matrix(n);
		
		for (int i = 0; i < n; ++i)
		{
			for (int j = 0; j < n; ++j)
			{
				int sum = 0;
				for (int k = 0; k < n; ++k)
				{
					sum += data[i][k] * other.data[k][j]; 
				}
				
				ans.data[i][j] = sum;
			}
		}
		
		return ans;
	}
	
	public Matrix mul(Matrix other, int m)
	{
		Matrix ans = new Matrix(n);
		
		for (int i = 0; i < n; ++i)
		{
			for (int j = 0; j < n; ++j)
			{
				int sum = 0;
				for (int k = 0; k < n; ++k)
				{
					sum += data[i][k] * other.data[k][j] % m; 
				}
				
				ans.data[i][j] = sum % m;
			}
		}
		
		return ans;
	}
	
	public Matrix add(Matrix other)
	{
		Matrix ans = new Matrix(n);
		
		for (int i = 0; i < n; ++i)
		{
			for (int j = 0; j < n; ++j)
			{
				ans.data[i][j] = data[i][j] + other.data[i][j];  
			}
		}
		
		return ans;
	}
	
	public Matrix add(Matrix other, int m)
	{
		Matrix ans = new Matrix(n);
		
		for (int i = 0; i < n; ++i)
		{
			for (int j = 0; j < n; ++j)
			{
				ans.data[i][j] = (data[i][j] + other.data[i][j]) % m;  
			}
		}
		
		return ans;
	}
	
	public Matrix pow(int n, int m)
	{
		int dim = this.n;
		Matrix ans = new Matrix(dim, true);
		Matrix p = new Matrix(this);
		
		while (n > 0)
		{
			if ((n & 1) != 0)
			{
				ans = ans.mul(p, m);
			}
			
			p = p.mul(p, m);
			n >>= 1;
		}
		
		return ans;
	}
	
	public void print()
	{
		for (int i = 0; i < n; ++i)
		{
			for (int j = 0; j < n; ++j)
			{
				System.out.print(data[i][j]);
				if (j != n - 1)
				{
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}

	public int[][] getData()
	{
		return data;
	}
	
	private final int n;
	private final int[][] data;
}