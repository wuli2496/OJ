package main

import (
	"fmt"
)

func twoSum(nums []int, target int) []int {
	hash := make(map[int]int)
	for i := 0; i < len(nums); i++ {
		hash[nums[i]] = i
	}

	for i := 0; i < len(nums); i++ {
		supplement := target - nums[i]
		j, ok := hash[supplement]
		if ok && i != j {
			return []int{i, j}
		}
	}

	return nil
}

func main() {
	var nums []int = []int{3, 2, 4}
	target := 6
	fmt.Println("result:", twoSum(nums, target))
}