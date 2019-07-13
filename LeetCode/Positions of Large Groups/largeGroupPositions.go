package main

func largeGroupPositions(S string) [][]int {
	strLen := len(S)
	i := 0
	var ans [][]int

	for j := 0; j < strLen; j++ {
		if j + 1 == strLen || S[j] != S[j + 1] {
			if j - i + 1 >= 3 {
				ans = append(ans, []int{i, j})
			}
			i = j + 1
		}
	}

	return ans
}
