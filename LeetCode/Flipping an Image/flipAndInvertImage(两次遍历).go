package main

func flipAndInvertImage(A [][]int) [][]int {
	row := len(A)
	col := len(A[0])

	for i := 0; i < row; i++ {
		for j := 0; j < col / 2; j++ {
			A[i][j], A[i][col - 1 - j] = A[i][col - 1 - j], A[i][j]
		}
	}

	for i := 0; i < row; i++ {
		for j := 0; j < col; j++ {
			A[i][j] = 1 - A[i][j]
		}
	}
	return A
}
