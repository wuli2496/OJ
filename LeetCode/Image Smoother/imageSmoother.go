package main

import "math"

func imageSmoother(M [][]int) [][]int {
	var ans [][]int

	row := len(M)
	col := len(M[0])

	for i := 0; i < row; i++ {
		var tmp []int
		for j := 0; j < col; j++ {
			sum := 0

			cnt := 0

			for nr := i - 1; nr <= i + 1; nr++ {
				for nc := j - 1; nc <= j + 1; nc++ {
					if nr >= 0 && nr < row && nc >= 0 && nc < col {
						sum += M[nr][nc]
						cnt++
					}
				}
			}

			aver := int(math.Floor(float64(sum / cnt)))
			tmp = append(tmp, aver)
		}
		ans = append(ans, tmp)
	}

	return ans
}