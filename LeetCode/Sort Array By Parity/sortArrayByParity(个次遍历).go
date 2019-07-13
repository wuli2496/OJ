package main

func sortArrayByParity(A []int) []int {
	i, j := 0, len(A) - 1
	for i < j {
		if A[i] % 2 > A[j] % 2 {
			A[i], A[j] = A[j], A[i]
		}

		if 0 == A[i] % 2 {
			i++
		}

		if 1 == A[j] % 2 {
			j--
		}
	}

	return A
}