package main

import "sort"

func triangleNumber(nums []int) int {
	arLen := len(nums)

	sort.Ints(nums)
	count := 0
	for i := 0; i < arLen; i++ {
		k := i + 2
		for j := i + 1; j < arLen && nums[i] != 0; j++ {
			k = binarySearch(nums, k, arLen - 1, nums[i] + nums[j])
			count += k - j - 1
		}
	}
	return count
}

func binarySearch(nums []int, left, right int, x int) int {
	arLen := len(nums)
	for left <= right && left < arLen {
		mid := (left + right) >> 1
		if nums[mid] >= x {
			right = mid - 1
		} else {
			left = mid + 1
		}
	}

	return left
}