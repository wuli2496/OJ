#include<iostream>
#include <fstream>
#include <vector>
#include <string>
#include <algorithm>
#include <iterator>

using namespace std;

class Solution
{
public:
	vector<string> generateParenthesis(int n) 
	{
		string s(2 * n, ' ');
		vector<string> result;
		generateAll(s, 0, 0, 0, n, result);
		
		return result;
	}

private:
	void generateAll(string& s, int pos, int open, int close, int max, vector<string>& result)
	{
		if (pos >= (int)s.length())
		{
			result.push_back(s);
			return;
		}

		if (open < max)
		{
			s[pos] = '(';
			generateAll(s, pos + 1, open + 1, close, max, result);
		}

		if (close < open)
		{
			s[pos] = ')';
			generateAll(s, pos + 1, open, close + 1, max, result);
		}
	}
};

int main()
{
	Solution solver;

#ifndef ONLINE_JUDGE
	ifstream fin("f:\\OJ\\uva_in.txt");
	streambuf* old = cin.rdbuf(fin.rdbuf());
#endif
	
	int n = 3;
	vector<string> result = solver.generateParenthesis(n);
	cout << "size:" << result.size() << endl;
	copy(result.begin(), result.end(), ostream_iterator<string>(cout, " "));
	cout << endl;
#ifndef ONLINE_JUDGE
	cin.rdbuf(old);
#endif

	return 0;
}


