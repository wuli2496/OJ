package main

import "sort"

func triangleNumber(nums []int) int {
	arLen := len(nums)

	sort.Ints(nums)
	count := 0
	for i := 0; i < arLen; i++ {
		k := i + 2
		for j := i + 1; j < arLen && nums[i] != 0; j++ {
			for k < arLen && nums[i] + nums[j] > nums[k] {
				k++
			}
			count += k - j - 1
		}
	}
	return count
}

