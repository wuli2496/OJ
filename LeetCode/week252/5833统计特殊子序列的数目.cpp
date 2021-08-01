class Solution {
public:
    int countSpecialSubsequences(vector<int>& nums) {
        int f0 = 0, f1 = 0, f2 = 0;
        for (int num : nums) {
            if (num == 0) {
                f0 = (2 * f0 % mod + 1) % mod;
            } else if (num == 1) {
                f1 = (2 * f1 % mod + f0) % mod;
            } else if (num == 2) {
                f2 = (2 * f2 % mod + f1) % mod;
            }
        }
        
        return f2;
    }
    
private:
    static constexpr int mod = 1000000007;
};
