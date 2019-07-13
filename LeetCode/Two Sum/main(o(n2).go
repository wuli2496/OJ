package main

import (
	"fmt"
)

func twoSum(nums []int, target int) []int {
	for i := 0; i < len(nums); i++ {
		for j := i + 1; j < len(nums); j++ {
			if nums[i] == target - nums[j] {
				return []int{i, j}
			}
		}
	}

	return nil
}

func main() {
	var nums []int = []int{3, 2, 4}
	target := 6
	fmt.Println("result:", twoSum(nums, target))
}