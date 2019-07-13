package main

func isMonotonic(A []int) bool {
	return increasing(A) || decreasing(A)
}

func increasing(A[] int) bool {
	for i, aLen := 0, len(A); i + 1 < aLen; i++ {
		if A[i] > A[i + 1] {
			return false
		}
	}

	return true
}

func decreasing(A[] int) bool {
	for i, aLen := 0, len(A); i + 1 < aLen; i++ {
		if A[i] < A[i + 1] {
			return false
		}
	}

	return true
}