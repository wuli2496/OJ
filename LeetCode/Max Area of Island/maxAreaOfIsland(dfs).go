package main

func maxAreaOfIsland(grid [][]int) int {
	algo := &DfsAlgo{grid}
	return algo.Execute()
}

type AlgoStrategy interface {
	Execute() int
}

type DfsAlgo struct {
	grid [][]int
}

func (algo *DfsAlgo) Execute() int {
	row := len(algo.grid)
	col := len(algo.grid[0])

	vis := make([][]bool, row)
	for i := 0; i < row; i++ {
		vis[i] = make([]bool, col)
	}

	ans := 0
	for i := 0; i < row; i++ {
		for j := 0; j < col; j++ {
			if !vis[i][j] && algo.grid[i][j] == 1{
				tmp := algo.dfs(i, j, vis, row, col)
				if tmp > ans {
					ans = tmp
				}
			}
		}
	}

	return ans
}

func (algo *DfsAlgo) dfs(x, y int, vis [][]bool, row, col int) int {
	if x < 0 || x >= row {
		return 0
	}

	if y < 0 || y >= col {
		return 0
	}

	if algo.grid[x][y] == 0 {
		return 0
	}

	if vis[x][y] {
		return 0
	}

	vis[x][y] = true

	return 1 + algo.dfs(x + 1, y, vis, row, col) + algo.dfs(x - 1, y, vis, row, col) +
		algo.dfs(x, y - 1, vis, row, col) + algo.dfs(x, y + 1, vis, row, col)
}

