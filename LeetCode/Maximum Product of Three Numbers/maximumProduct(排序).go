package main

import (
	"math"
	"sort"
)

func maximumProduct(nums []int) int {
	arLen := len(nums)

	sort.Ints(nums)
	return int(math.Max(float64(nums[0] * nums[1] * nums[arLen - 1]),
		float64(nums[arLen - 3] * nums[arLen - 2] * nums[arLen - 1])))
}