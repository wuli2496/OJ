package main

func flipAndInvertImage(A [][]int) [][]int {
	row := len(A)
	col := len(A[0])

	for i := 0; i < row; i++ {
		for j := 0; j < (col + 1)/ 2; j++ {
			A[i][j], A[i][col - 1 - j] = 1 - A[i][col - 1 - j], 1 - A[i][j]
		}
	}

	return A
}
