class Solution {
public:
    vector<vector<int>> largeGroupPositions(string s)
    {
        vector<vector<int>> result;
        int n = s.size();
        int num = 1;
        for (int i = 0; i < n; ++i) {
            if (i == n - 1 || s[i] != s[i + 1]) {
                if (num >= 3) {
                    result.push_back({i - num + 1, i});
                }
                num = 1;
            } else {
                ++num;
            }
        }

        return result;
    }
};
