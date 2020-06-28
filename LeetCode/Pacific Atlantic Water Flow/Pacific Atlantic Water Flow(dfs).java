import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution
{
    private int[][] dir = {{1, 0}, {-1, 0}, {0, -1}, {0, 1}};
    private int row, col;

    public List<List<Integer>> pacificAtlantic(int[][] matrix)
    {
        List<List<Integer>> ans = new ArrayList<>();
        if (matrix == null || matrix.length == 0) {
            return ans;
        }

        row = matrix.length;
        col = matrix[0].length;

        boolean[][] pacificOcean = new boolean[row][col];
        boolean[][] atlanticOcean = new boolean[row][col];

        for (int i = 0; i < row; ++i) {
            dfs(i, 0, matrix, pacificOcean);
            dfs(i, col - 1, matrix, atlanticOcean);
        }

        for (int i = 0; i < col; ++i) {
            dfs(0, i, matrix, pacificOcean);
            dfs(row - 1, i, matrix, atlanticOcean);
        }

        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                if (pacificOcean[i][j] && atlanticOcean[i][j]) {
                    ans.add(Arrays.asList(i, j));
                }
            }
        }

        return ans;
    }

    private boolean isArea(int x, int y)
    {
        return x >= 0 && x < row && y >= 0 && y < col;
    }

    private void dfs(int x, int y, int[][] matrix, boolean[][] grid)
    {
        grid[x][y] = true;

        for (int[] d : dir) {
            int newx = x + d[0];
            int newy = y + d[1];

            if (!isArea(newx, newy) || matrix[x][y] > matrix[newx][newy] || grid[newx][newy]) {
                continue;
            }

            dfs(newx, newy, matrix, grid);
        }
    }
}