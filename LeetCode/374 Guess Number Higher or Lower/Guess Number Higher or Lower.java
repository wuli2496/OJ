public class Solution extends GuessGame {
    public int guessNumber(int n) {
        int l = 0, r = n;
        while (r - l > 1) {
            int mid = l + (r - l) / 2;
            if (guess(mid) <= 0) {
                r = mid;
            } else {
                l = mid;
            }
        }

        return r;
    }
}