class Solution {
public:
    long long minimumPerimeter(long long neededApples) {
        long long n = 1;
        for (; 2 * n * (n + 1) * (2 * n + 1) < neededApples; ++n);
        
        return n * 8;
    }
};

/**
 *添加二分法
 */

class Solution {
public:
    long long minimumPerimeter(long long neededApples) {
        long long left = 1, right = 100000, ans;
        while (left <= right) {
            long long mid = (left + right) / 2;
            if (2 * mid * (mid + 1) * (2 * mid + 1) >= neededApples) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return ans * 8;
    }
};
