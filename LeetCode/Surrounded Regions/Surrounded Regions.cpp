class Solution
{
public:
    void solve(vector<vector<char>>& board)
    {
        size_t row = board.size();
        size_t col = 0;
        if (row > 0)
        {
            col = board[0].size();
        }

        for (size_t i = 0; i < row; ++i)
        {
            floodFill(board, i, 0);
            if (col > 1)
            {
                floodFill(board, i, col - 1);
            }
        }

        for (size_t i = 0; i < col; ++i)
        {
            floodFill(board, 0, i);
            if (row > 1)
            {
                floodFill(board, row - 1, i);
            }
        }

        for (auto& rboard : board)
        {
            for (auto& c : rboard)
            {
                if (c == 'O')
                {
                    c = 'X';
                }
            }
        }

        for (auto& rboard : board)
        {
            for (auto& c : rboard)
            {
                if (c == '1')
                {
                    c = 'O';
                }
            }
        }
    }

private:
    void floodFill(vector<vector<char>>& board, size_t x, size_t y)
    {
        if (board[x][y] == '1')
        {
            return;
        }

        if (board[x][y] == 'X')
        {
            return;
        }

        if (board[x][y] == 'O')
        {
            board[x][y] = '1';
        }

        if (x > 0)
        {
            floodFill(board, x - 1, y);
        }

        if (x + 1 < board.size())
        {
            floodFill(board, x + 1, y);
        }

        if (y > 0)
        {
            floodFill(board, x, y - 1);
        }

        if (y + 1 < board[0].size())
        {
            floodFill(board, x, y + 1);
        }

    }
};