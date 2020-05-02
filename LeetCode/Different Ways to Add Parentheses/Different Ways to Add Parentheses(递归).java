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
		if (input.length() == 0)
		{
			return new ArrayList<>();
		}
		
		List<Integer> result = new ArrayList<Integer>();
		int num = 0;
		int index = 0;
		for (index = 0; index < input.length() && !isOperation(input.charAt(index)); ++index)
		{
			num = num * 10 + input.charAt(index) - '0';
		}
		
		if (index == input.length())
		{
			result.add(num);
			return result;
		}
		
		for (int i = 0; i < input.length(); ++i)
		{
			if (isOperation(input.charAt(i)))
			{
				List<Integer> left = diffWaysToCompute(input.substring(0, i));
				List<Integer> right = diffWaysToCompute(input.substring(i + 1));
				
				for (int leftNum : left)
				{
					for (int rightNum : right)
					{
						int ans = calculate(leftNum, rightNum, input.charAt(i));
						result.add(ans);
					}
				}
			}
		}
		
		return result;
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

