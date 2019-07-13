package main

import "fmt"

func validMountainArray(A []int) bool {
	aLen := len(A)
	i := 0
	for i + 1 < aLen && A[i] < A[i + 1]{
		i++
	}

	if i == 0 || i == aLen - 1 {
		return false
	}

	for i + 1 < aLen && A[i] > A[i + 1] {
		i++
	}

	return i == aLen - 1
}


func main() {
	nums := []int{3,5,5}
	fmt.Println("ans: ", validMountainArray(nums))
}