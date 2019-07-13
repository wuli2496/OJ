package main

func isMonotonic(A []int) bool {
	store := 0

	for i, aLen := 0, len(A); i + 1 < aLen; i++ {
		c := compare(A[i], A[i + 1])
		if 0 != c {
			if 0 != store && c != store {
				return false
			}

			store = c
		}
	}

	return true
}

func compare(a, b int) int {
	if a == b {
		return 0
	} else if a < b {
		return -1
	} else {
		return 1
	}
}