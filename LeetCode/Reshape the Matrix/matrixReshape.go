package main

func matrixReshape(nums [][]int, r int, c int) [][]int {
	row := len(nums)
	col := len(nums[0])

	if row * col != r * c {
		return nums
	}

	ans := make([][]int, r)
	for i := 0; i < r; i++ {
		ans[i] = make([]int, c)
	}

	for i := 0; i < row; i++ {
		for j := 0; j < col; j++ {
			newrow := (i * col + j) / c
			newcol := (i * col + j) % c
			ans[newrow][newcol] = nums[i][j]
		}
	}

	return ans
 }