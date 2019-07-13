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
		generateAll(s, 0, result);
		
		return result;
	}

private:
	void generateAll(string& s, int pos, vector<string>& result)
	{
		if (pos >= (int)s.length())
		{
			if (isValid(s))
			{
				result.push_back(s);
			}

			return;
		}

		s[pos] = '(';
		generateAll(s, pos + 1, result);
		s[pos] = ')';
		generateAll(s, pos + 1, result);
	}

	bool isValid(const string& s)
	{
		int len = s.length();
		int c = 0;
		for (int i = 0; i < len; ++i)
		{
			if ('(' == s[i]) ++c;
			else if (')' == s[i]) --c;

			if (0 > c) return false;
		}

		return 0 == c;
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


