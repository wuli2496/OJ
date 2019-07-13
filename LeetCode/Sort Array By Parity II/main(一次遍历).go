package main

import "fmt"

func sortArrayByParityII(A []int) []int {
	j := 1
	for i := 0; i < len(A); i += 2 {
		if 1 == A[i] % 2 {
			for 1 == A[j] % 2  {
				j += 2
			}

			A[i], A[j] = A[j], A[i]
		}
	}

	return A
}


func main() {
	nums := []int{4, 2, 5, 7}
	fmt.Println("nums: ", sortArrayByParityII(nums))
}