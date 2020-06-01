#include <bits/stdc++.h>
#include <limits>

using namespace std;

class Solution 
{
public:
    Solution(string& str)
        : s(str)
    {

    }

    int solve()
    {
        vector<int> suffix(s.length() + 1);
        for (int i = s.length() - 1; i >= 0; --i) {
            suffix[i] = suffix[i + 1] + (s[i] == '1' ? 1 : 0);
        }    
        int zeroNum = 0;
        int ans = numeric_limits<int>::max();
        for (size_t i = 0; i < s.length(); ++i) {
            ans = min(ans, zeroNum + suffix[i]);
            zeroNum += (s[i] == '0' ? 1 : 0);
        }

        for (size_t i = 0; i < s.length(); ++i) {
            if (s[i] == '1') {
                s[i] = '0';
            } else {
                s[i] = '1';
            }
        }

        for (int i = s.length() - 1; i >= 0; --i) {
            suffix[i] = suffix[i + 1] + (s[i] == '1' ? 1 : 0);
        }

        zeroNum = 0;
        for (size_t i = 0; i < s.length(); ++i) {
            ans = min(ans, zeroNum + suffix[i]);
            zeroNum += (s[i] == '0' ? 1 : 0);
        }

        return ans;
    }
private:
    string& s;
};

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

#ifndef ONLINE_JUDGE
    ifstream fin("/home/wl/OJ/OJ/codeforces/codeforces_in");
    streambuf* cinback = cin.rdbuf(fin.rdbuf());
#endif
    int t;
    cin >> t;
    while (t--) {
        string s;
        cin >> s;
        Solution solution(s);
        int ans = solution.solve();
        cout << ans << endl;
    }
#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif
}
