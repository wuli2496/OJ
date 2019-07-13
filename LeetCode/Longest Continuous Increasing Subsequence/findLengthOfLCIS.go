package main

import "math"

func findLengthOfLCIS(nums []int) int {
	ans := 0
	arLen := len(nums)

	for i := 0; i < arLen; i++ {
		j := i
		for (i + 1 < arLen) && (nums[i] < nums[i + 1]){
			i++
		}

		ans = int(math.Max(float64(ans), float64(i - j + 1)))
	}

	return ans
}