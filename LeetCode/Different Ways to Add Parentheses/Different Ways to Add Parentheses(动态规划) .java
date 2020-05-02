/*
 * Given a string of numbers and operators, return all possible results from computing all the different possible ways to group numbers and operators. The valid operators are +, - and *.

Example 1:

Input: "2-1-1"
Output: [0, 2]
Explanation: 
((2-1)-1) = 0 
(2-(1-1)) = 2

Example 2:

Input: "2*3-4*5"
Output: [-34, -14, -10, -10, 10]
Explanation: 
(2*(3-(4*5))) = -34 
((2*3)-(4*5)) = -14 
((2*(3-4))*5) = -10 
(2*((3-4)*5)) = -10 
(((2*3)-4)*5) = 10
 */
import java.util.List;
import java.util.ArrayList;

class Solution 
{	
	public List<Integer> diffWaysToCompute(String input) 
	{
		List<Integer> numList = new ArrayList<>();
		List<Character> opList = new ArrayList<>();
		
		int num = 0;
		for (int i = 0; i < input.length(); ++i)
		{
			if (isOperation(input.charAt(i)))
			{
				opList.add(input.charAt(i));
				numList.add(num);
				num = 0;
				continue;
			}
			
			num = num * 10 + input.charAt(i) - '0';
		}
		
		numList.add(num);
		
		int n = numList.size();
		List<Integer>[][] dp = (ArrayList<Integer>[][])new ArrayList[n][n];
		for (int i = 0; i < n; ++i)
		{
			List<Integer> result = new ArrayList<Integer>();
			result.add(numList.get(i));
			dp[i][i] = result; 
		}
		
		for (int len = 2; len <= n; ++len)
		{
			for (int i = 0; i < n; ++i)
			{
				int j = i + len - 1;
				if (j >= n)
				{
					continue;
				}
				List<Integer> result = new ArrayList<Integer>();
				for (int k = i; k < j; ++k)
				{
					List<Integer> left = dp[i][k]; 
					List<Integer> right = dp[k + 1][j];
					for (int leftNum : left)
					{
						for (int rightNum : right)
						{
							char ch = opList.get(k);
							int ans = calculate(leftNum, rightNum, ch);
							result.add(ans);
						}
					}
				}
				dp[i][j]= result; 
			}
		}
		
		return dp[0][n - 1];
    }
	
	private int calculate(int a, int b, char op)
	{
		switch (op) {
		case '+':
			return a + b;
		case '-':
			return a - b;
		case '*':
			return a * b;
		default:
			break;
		}
		
		return -1;
	}
	
	private boolean isOperation(char ch)
	{
		if (ch == '+' || ch == '-' || ch == '*')
		{
			return true;
		}
		
		return false;
	}
}

