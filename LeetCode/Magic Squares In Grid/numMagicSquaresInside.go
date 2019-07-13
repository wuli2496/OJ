package main

const (
	ARR_LEN = 16
	MIN = 1
	MAX = 9
)

func numMagicSquaresInside(grid [][]int) int {
	r := len(grid)
	c := len(grid[0])

	ans := 0
	for row := 0; row < r - 2; row++ {
		for col := 0; col < c - 2; col++ {
			if 5 != grid[row + 1][col +1] {
				continue
			}
			if magic(grid[row][col], grid[row][col + 1], grid[row][col + 2],
				grid[row + 1][col], grid[row + 1][col + 1], grid[row + 1][col + 2],
				grid[row + 2][col], grid[row + 2][col + 1], grid[row + 2][col + 2]) {
					ans++
			}
		}
	}

	return ans
}

func magic(args ...int) bool {
	var a [ARR_LEN]int
	for _, v := range args {
		a[v]++
	}

	for i := MIN; i <= MAX; i++ {
		if 1 != a[i] {
			return false
		}
	}

	return 15 == args[0] + args[1] + args[2] &&
		15 == args[3] + args[4] + args[5] &&
		15 == args[6] + args[7] + args[8] &&
		15 == args[0] + args[3] + args[6] &&
		15 == args[1] + args[4] + args[7] &&
		15 == args[2] + args[5] + args[8] &&
		15 == args[0] + args[4] + args[8] &&
		15 == args[2] + args[4] + args[6]
}
