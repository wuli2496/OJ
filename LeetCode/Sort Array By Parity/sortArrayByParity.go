package main

func sortArrayByParity(A []int) []int {
	result := make([]int, len(A))

	index := 0
	for _, v := range A {
		if 0 == v % 2 {
			result[index] = v
			index++
		}
	}

	for _, v := range A {
		if 1 == v % 2 {
			result[index] = v
			index++
		}
	}

	return result
}