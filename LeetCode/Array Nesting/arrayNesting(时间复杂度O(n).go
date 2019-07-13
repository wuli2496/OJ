package main

import "math"

func arrayNesting(nums []int) int {
	arLen := len(nums)

	ans := 0
	for i := 0; i < arLen; i++ {
		if nums[i] != math.MaxInt32 {
			start := i
			cnt := 0
			for nums[start] != math.MaxInt32 {
				tmp := start
				start = nums[start]
				cnt++
				nums[tmp] = math.MaxInt32
			}
			if cnt > ans {
				ans = cnt
			}
		}
	}

	return ans
}