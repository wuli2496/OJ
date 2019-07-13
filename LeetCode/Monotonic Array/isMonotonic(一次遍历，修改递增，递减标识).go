package main

func isMonotonic(A []int) bool {
	increasing, decreasing := true, true

	for i, aLen := 0, len(A); i + 1 < aLen; i++ {
		if A[i] > A[i + 1] {
			increasing = false
		} else if A[i] < A[i + 1] {
			decreasing = false
		}
	}

	return increasing || decreasing
}


