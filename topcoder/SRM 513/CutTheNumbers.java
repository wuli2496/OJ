class CutTheNumbers
{
    public int maximumSum(String[] board)
    {
        int row = board.length;
        int col = board[0].length();
        int t = row * col;
        int res = 0;

        for (int mask = 0; mask < (1 << t); ++mask) {
            int s = 0;
            for (int i = 0; i < row; ++i) {
                int x = 0;
                for (int j = 0; j < col; ++j) {
                    if ((mask & (1 << (i * col + j))) == 0) {
                        s += x;
                        x = 0;
                    } else {
                        x = x * 10 + (board[i].charAt(j) - '0');
                    }
                }
                s += x;
            }

            for (int i = 0; i < col; ++i) {
                int x = 0;
                for (int j = 0; j < row; ++j) {
                    if ((mask & (1 << (i * col + j))) != 0) {
                        s += x;
                        x = 0;
                    } else {
                        x = x * 10 + (board[i].charAt(j) - '0');
                    }
                }
                s += x;
            }

            res = Math.max(res, s);
        }

        return res;
    }
}
