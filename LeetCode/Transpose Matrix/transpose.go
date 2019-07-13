package main

func transpose(A [][]int) [][]int {
	var result [][]int
	for col := 0; col < len(A[0]); col++ {
		var tmp []int
		for row := 0; row < len(A); row++{
			tmp = append(tmp, A[row][col])
		}
		result = append(result, tmp)
	}

	return result
}

