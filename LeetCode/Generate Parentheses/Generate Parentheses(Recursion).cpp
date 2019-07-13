#include <iostream>
#include <fstream>
#include <iterator>
#include <vector>

using namespace std;

class Solution {
public:
	vector<string> generateParenthesis(int n) 
	{
		vector<string> ans;
        if (0 == n)
        {
            ans.push_back("");
            return ans;
        }
        
        for (int i = 0; i < n; ++i)
        {
            for (auto s1 : generateParenthesis(i))
            {
                for (auto s2 : generateParenthesis(n - 1 - i))
                {
                    ans.push_back("(" + s1 + ")" + s2);
                }
            }
        }
        
		return ans;
	}
};

int main(int argc, char** argv)
{
    #ifndef ONLINE_JUDGE
        ifstream fin("f:\\OJ\\uva_in.txt");
        streambuf* inback = cin.rdbuf(fin.rdbuf());
    #endif
    
    Solution s;
    
    int n = 3;
    vector<string> ans = s.generateParenthesis(n);
    copy(ans.begin(), ans.end(), ostream_iterator<string>(cout, " "));
    cout << endl;
    
    #ifndef ONLINE_JUDGE
        cin.rdbuf(inback);
    #endif
    
    return 0;
}