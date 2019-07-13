package main

import "fmt"

func sortArrayByParityII(A []int) []int {
	ans := make([]int, len(A))

	i := 0
	for _, v := range A {
		if v % 2 == 0 {
			ans[i] = v
			i += 2
		}
	}

	i = 1
	for _, v := range A {
		if v % 2 == 1 {
			ans[i] = v
			i += 2
		}
	}

	return ans
}


func main() {
	nums := []int{4, 2, 5, 7}
	fmt.Println("nums: ", sortArrayByParityII(nums))
}