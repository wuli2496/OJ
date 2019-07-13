package main

func isToeplitzMatrix(matrix [][]int) bool {
	hash := make(map[int]int)
	row := len(matrix)
	col := len(matrix[0])

	for i := 0; i < row; i++ {
		for j := 0; j < col; j++ {
			v, ok := hash[i - j]
			if !ok {
				hash[i - j] = matrix[i][j]
			} else {
				if v != matrix[i][j] {
					return false
				}
			}
		}
	}

	return true
}
