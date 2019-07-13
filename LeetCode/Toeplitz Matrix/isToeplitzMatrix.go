package main

func isToeplitzMatrix(matrix [][]int) bool {
	row := len(matrix)
	col := len(matrix[0])

	for i := 0; i < row; i++ {
		for j := 0; j < col; j++ {
			if i > 0 && j > 0 && matrix[i - 1][j - 1] != matrix[i][j] {
				return false
			}
		}
	}

	return true
}
