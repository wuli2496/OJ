import java.util.Scanner;

public class Main 
{
    public static void main(String args[])
    {
        Scanner cin = new Scanner(System.in);
        int n, k, m;
        n = cin.nextInt();
        k = cin.nextInt();
        m = cin.nextInt();
        
        int[][] data = new int[n][n];
        for (int i = 0; i < n; ++i)
        {
        	for (int j = 0; j < n; ++j)
        	{
        		data[i][j] = cin.nextInt() % m;
        	}
        }
        
        Solution solution = new Solution(m, data);
        Matrix ans = solution.solve(k);
        ans.print();
        cin.close();
    }
}

class Solution 
{
	public Solution(int m, int[][] data)
	{
		this.m = m;
		origin = new Matrix(data);
	}
	
	public Matrix solve(int k)
	{
		if (k == 1)
		{
			return origin;
		}
		
		Matrix midMatrix = solve(k / 2);
		Matrix curMatrix, ans;
		if ((k & 1) == 1) 
		{
			curMatrix = origin.pow(k / 2 + 1, m);
			ans = curMatrix.mul(midMatrix, m);
			ans = ans.add(curMatrix, m);
			ans = ans.add(midMatrix, m);
		}
		else 
		{
			curMatrix = origin.pow(k / 2, m);
			ans = curMatrix.mul(midMatrix, m);
			ans = ans.add(midMatrix, m);
		}
		
		return ans;
	}
	
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
	
	private final int n;
	private final int[][] data;
}