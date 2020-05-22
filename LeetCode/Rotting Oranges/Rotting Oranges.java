class Solution {
    public int orangesRotting(int[][] grid) {
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> depth = new HashMap<>();

        int row = grid.length;
        int col = grid[0].length;
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                if (grid[i][j] == 2) {
                    int code = i * col + j;
                    queue.add(code);
                    depth.put(code, 0);
                }
            }
        }

        int ans = 0;
        while (!queue.isEmpty()) {
            int curCode = queue.poll();
            int r = curCode / col;
            int c = curCode % col;

            for (int i = 0; i < dx.length; ++i) {
                int nr = r + dx[i];
                int nc = c + dy[i];

                if (nr < 0 || nr >= row || nc < 0 || nc >= col || grid[nr][nc] != 1) {
                    continue;
                }

                grid[nr][nc] = 2;
                int newCode = nr * col + nc;
                queue.add(newCode);
                depth.put(newCode, depth.get(curCode) + 1);
                ans = depth.get(newCode);
            }
        }

        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                if (grid[i][j] == 1) {
                    return -1;
                }
            }
        }

        return ans;
    }

    private int[] dx = {0, -1, 0, 1};
    private int[] dy = {-1, 0, 1, 0};
}
