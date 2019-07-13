package main

import (
	"math"
)

func findShortestSubArray(nums []int) int {
	hash := make(map[int][]int)

	for i, v := range nums {
		hash[v] = append(hash[v], i)
	}

	ans := math.MaxInt32
	fre := 0
	for _, v := range hash {
		vLen := len(v)
		if vLen >= fre {
			if vLen == fre {
				ans = int(math.Min(float64(ans), float64(v[vLen - 1] - v[0] + 1)))
			} else {
				ans = v[vLen - 1] - v[0] + 1
			}
			fre = vLen
		}
	}

	return ans
}