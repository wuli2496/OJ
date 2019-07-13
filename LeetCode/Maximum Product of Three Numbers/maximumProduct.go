package main

import "math"


//超时
func maximumProduct(nums []int) int {
	arLen := len(nums)

	ans := math.MinInt32
	for i := 0; i < arLen; i++ {
		for j := i + 1; j < arLen; j++ {
			for k := j + 1; k < arLen; k++ {
				tmp := nums[i] * nums[j] * nums[k]
				if tmp > ans {
					ans = tmp
				}
			}
		}
	}

	return ans
}