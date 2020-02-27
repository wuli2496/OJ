class Solution
{
public:
    int numTrees(int n)
    {
        if (n == 1)
        {
            return 1;
        }

        vector<int> nums(static_cast<size_t>(n + 1), 0);
        nums[0] = 1;
        nums[1] = 1;

        for (size_t i = 2; i <= static_cast<size_t>(n); ++i)
        {
            for (size_t j = 1; j <= i; ++j)
            {
                nums[i] += nums[j - 1] * nums[i - j];
            }
        }

        return nums[static_cast<size_t>(n)];
    }
};